package org.prebid.server.hooks.modules.arcspan.contextual.hooks;

import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.auction.AuctionInvocationContext;
import org.prebid.server.hooks.v1.auction.AuctionResponseHook;
import org.prebid.server.hooks.v1.auction.AuctionResponsePayload;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

public class ArcSpanAuctionResponseHook implements AuctionResponseHook {
    
    private static final String CODE = "arcspan-auction-response-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanAuctionResponseHook.class);

    @Override
    public Future<InvocationResult<AuctionResponsePayload>> call(AuctionResponsePayload payload,
            AuctionInvocationContext invocationContext) {
                logger.info("{0} called", CODE);
                return Future.succeededFuture();        
    }

    @Override
    public String code() {
        return CODE;
    }

}