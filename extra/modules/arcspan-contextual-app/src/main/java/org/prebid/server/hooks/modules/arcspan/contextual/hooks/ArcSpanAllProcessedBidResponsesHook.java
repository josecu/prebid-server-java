package org.prebid.server.hooks.modules.arcspan.contextual.hooks;

import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.auction.AuctionInvocationContext;
import org.prebid.server.hooks.v1.bidder.AllProcessedBidResponsesHook;
import org.prebid.server.hooks.v1.bidder.AllProcessedBidResponsesPayload;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

public class ArcSpanAllProcessedBidResponsesHook implements AllProcessedBidResponsesHook {

    private static final String CODE = "arcspan-all-processed-bid-responses-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanAllProcessedBidResponsesHook.class);

    @Override
    public Future<InvocationResult<AllProcessedBidResponsesPayload>> call(AllProcessedBidResponsesPayload payload,
            AuctionInvocationContext invocationContext) {
            logger.info("{0} called", CODE);
            return Future.succeededFuture();
    }

    @Override
    public String code() {
        return CODE;
    }
    
}
