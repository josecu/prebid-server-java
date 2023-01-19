package org.prebid.server.hooks.modules.arcspan.contextual.hooks;
import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.InvocationStatus;
import org.prebid.server.hooks.v1.InvocationAction;
import org.prebid.server.hooks.v1.auction.AuctionInvocationContext;
import org.prebid.server.hooks.v1.auction.AuctionRequestPayload;
import org.prebid.server.hooks.execution.v1.auction.AuctionRequestPayloadImpl;
import org.prebid.server.hooks.modules.arcspan.contextual.InvocationResultImpl;
import org.prebid.server.hooks.v1.auction.ProcessedAuctionRequestHook;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

import java.util.Arrays;

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
                      AuctionRequestPayloadImpl.of(p.bidRequest().toBuilder()
                              .site(p.bidRequest().getSite().toBuilder()
                                        .name("arcspan")
                                        .cat(Arrays.asList("IAB8", "IAB2", "IAB17", "IAB2"))
                                        .sectioncat(Arrays.asList("IAB8", "IAB2", "IAB17", "IAB2"))
                                        .pagecat(Arrays.asList("IAB8", "IAB2", "IAB17", "IAB2"))
                                        .keywords("Sports,Food & Drink,Automotive,Automotive")
                                        .build())
                              .build()))
                .build()
            );
    }

    @Override
    public String code() {
        return CODE;
    }
    
}
