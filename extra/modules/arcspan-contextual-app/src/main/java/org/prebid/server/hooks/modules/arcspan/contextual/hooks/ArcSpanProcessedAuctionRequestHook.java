package org.prebid.server.hooks.modules.arcspan.contextual.hooks;

import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.InvocationStatus;
import org.prebid.server.hooks.v1.InvocationAction;
import org.prebid.server.hooks.v1.auction.AuctionInvocationContext;
import org.prebid.server.hooks.v1.auction.AuctionRequestPayload;
import org.prebid.server.hooks.execution.v1.auction.AuctionRequestPayloadImpl;
import org.prebid.server.hooks.modules.arcspan.contextual.InvocationResultImpl;
import org.prebid.server.hooks.v1.auction.ProcessedAuctionRequestHook;

import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.request.Site;
import com.iab.openrtb.request.Content;
import com.iab.openrtb.request.Data;
import com.iab.openrtb.request.Segment;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.Future;

import org.prebid.server.vertx.http.BasicHttpClient;
import io.vertx.core.Vertx;
import org.prebid.server.vertx.http.model.HttpClientResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.prebid.server.exception.PreBidException;

import org.prebid.server.hooks.modules.arcspan.contextual.model.ArcObject;

public class ArcSpanProcessedAuctionRequestHook implements ProcessedAuctionRequestHook {

    private static final String CODE = "arcspan-processed-auction-request-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanProcessedAuctionRequestHook.class);

    @Override
    public Future<InvocationResult<AuctionRequestPayload>> call(AuctionRequestPayload payload,
            AuctionInvocationContext invocationContext) {
            logger.info("{0} called", CODE);

            logger.info("Processed Auction Request with Site name {0}", payload.bidRequest().getSite().getName());
            logger.info("Processed Auction Request with Site cat {0}", payload.bidRequest().getSite().getCat());

            return Future.succeededFuture(
                InvocationResultImpl.<AuctionRequestPayload>builder()
                    .status(InvocationStatus.success)
                    .action(InvocationAction.update)
                    .payloadUpdate(p ->
                      AuctionRequestPayloadImpl.of(update(p)))
                .build()
            );
    }

    @Override
    public String code() {
        return CODE;
    }

    private BidRequest update(AuctionRequestPayload auctionRequestPayload) {
        boolean hasSite = auctionRequestPayload.bidRequest().getSite() != null;
        if (!hasSite) {
            logger.info("No site object included in request. Unable to add contextual data.");
            return auctionRequestPayload.bidRequest();
        }
        boolean hasPage = auctionRequestPayload.bidRequest().getSite().getPage() != null;
        if (!hasPage) {
            logger.info("Site object does not contain a page url. Unable to add contextual data.");
            return auctionRequestPayload.bidRequest();
        }

        final Vertx vertx = Vertx.vertx();
        final BasicHttpClient client = new BasicHttpClient(vertx, vertx.createHttpClient());

        // TODO: Use existing Vert.X context

        String pageUrl = "https://dwy889uqoaft4.cloudfront.net/3333444jj?uri=" + auctionRequestPayload.bidRequest().getSite().getPage();
        logger.info("Fetching classifications from url: {0}", pageUrl);

        client.get(pageUrl, 1000)
                .map(this::processResponse);

        // TODO: Fetch contextual data for page url

        boolean hasContent = hasSite && auctionRequestPayload.bidRequest().getSite().getContent() != null;
        boolean hasData = hasContent && auctionRequestPayload.bidRequest().getSite().getContent().getData() != null;

        ObjectMapper mapper = new ObjectMapper(); // TODO: Make this a singleton
        ObjectNode ext = mapper.createObjectNode();
        ext.put("segtax", 6);

        List<Data> data = hasData ?
            auctionRequestPayload.bidRequest().getSite().getContent().getData() : new ArrayList<Data>();
        Data arcspanData = Data.builder()
                            .name("arcspan")
                            .segment(new ArrayList<Segment>() {{
                                add(Segment.builder().id("1").build());
                                add(Segment.builder().id("210").build());
                                add(Segment.builder().id("483").build());
                            }})
                            .ext(ext)
                            .build();
        data.add(arcspanData);

        Content.ContentBuilder contentBuilder = hasContent ?
            auctionRequestPayload.bidRequest().getSite().getContent().toBuilder() : Content.builder();
        Content content =  contentBuilder.data(data).build();

        Site site = auctionRequestPayload.bidRequest().getSite().toBuilder()
                        .name("arcspan")
                        .cat(Arrays.asList("IAB8", "IAB2", "IAB17", "IAB2"))
                        .sectioncat(Arrays.asList("IAB8", "IAB2", "IAB17", "IAB2"))
                        .pagecat(Arrays.asList("IAB8", "IAB2", "IAB17", "IAB2"))
                        .keywords("Sports,Food & Drink,Automotive,Automotive")
                        .content(content)
                        .build();

        return auctionRequestPayload.bidRequest().toBuilder().site(site).build();
    }

    private Void processResponse(HttpClientResponse response) {
        final int statusCode = response.getStatusCode();
        if (statusCode != 200) {
            throw new PreBidException("HTTP status code " + statusCode);
        }

        final String body = response.getBody();


        logger.info("Received classification data: {0}", body.substring(13, body.length() - 1));

        try {
            ObjectMapper objectMapper = new ObjectMapper(); // TODO: Make this a singleton
            ArcObject obj = objectMapper.readValue(body.substring(13, body.length() - 1), ArcObject.class);

            logger.info("Received arc object {0}", obj);

            if (obj.getCodes() != null) {
                if (obj.getCodes().getImages() != null) {
                    List<String> newImages = new ArrayList<String>();
                    for (String code : obj.getCodes().getImages()) {
                        newImages.add(code.replaceAll("-", "_"));
                    }
                    obj.getCodes().setImages(newImages);
                }

                if (obj.getCodes().getText() != null) {
                    List<String> newText = new ArrayList<String>();
                    for (String code : obj.getCodes().getText()) {
                        newText.add(code.replaceAll("-", "_"));
                    }
                    obj.getCodes().setText(newText);
                }
            }

            logger.info("Received arc object {0}", obj);
        } catch (JsonProcessingException e) {
            logger.info("JSON processing exception {0}", e);
        }

        return null;
    }
    
}
