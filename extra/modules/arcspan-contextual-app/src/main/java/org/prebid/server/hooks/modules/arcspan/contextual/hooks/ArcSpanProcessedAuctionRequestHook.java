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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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
    
}
