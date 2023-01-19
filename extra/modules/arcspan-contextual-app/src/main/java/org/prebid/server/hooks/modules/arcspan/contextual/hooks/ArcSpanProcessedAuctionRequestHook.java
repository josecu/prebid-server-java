package org.prebid.server.hooks.modules.arcspan.contextual.hooks;
import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.auction.AuctionInvocationContext;
import org.prebid.server.hooks.v1.auction.AuctionRequestPayload;
import org.prebid.server.hooks.v1.auction.ProcessedAuctionRequestHook;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

public class ArcSpanProcessedAuctionRequestHook implements ProcessedAuctionRequestHook {

    private static final String CODE = "arcspan-processed-auction-request-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanProcessedAuctionRequestHook.class);

    @Override
    public Future<InvocationResult<AuctionRequestPayload>> call(AuctionRequestPayload payload,
            AuctionInvocationContext invocationContext) {
            logger.info("{0} called", CODE);

            logger.info("Processed Auction Request with ID {0}", payload.bidRequest().getId());
            return Future.succeededFuture();
    }

    @Override
    public String code() {
        return CODE;
    }
    
}
