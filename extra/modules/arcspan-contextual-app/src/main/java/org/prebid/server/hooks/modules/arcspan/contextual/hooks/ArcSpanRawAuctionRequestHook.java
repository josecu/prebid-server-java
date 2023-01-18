package org.prebid.server.hooks.modules.arcspan.contextual.hooks;

import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.auction.AuctionInvocationContext;
import org.prebid.server.hooks.v1.auction.AuctionRequestPayload;
import org.prebid.server.hooks.v1.auction.RawAuctionRequestHook;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

public class ArcSpanRawAuctionRequestHook implements RawAuctionRequestHook {

    private static final String CODE = "arcspan-raw-auction-request-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanRawAuctionRequestHook.class);

    @Override
    public Future<InvocationResult<AuctionRequestPayload>> call(AuctionRequestPayload payload,
            AuctionInvocationContext invocationContext) {
            logger.info("{0} called", CODE);
            return Future.succeededFuture();
    }

    @Override
    public String code() {
        return CODE;
    }
    
}
