package org.prebid.server.hooks.modules.arcspan.contextual.hooks;

import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.bidder.BidderInvocationContext;
import org.prebid.server.hooks.v1.bidder.BidderResponsePayload;
import org.prebid.server.hooks.v1.bidder.RawBidderResponseHook;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

public class ArcSpanRawBidderResponseHook implements RawBidderResponseHook {
    private static final String CODE = "arcspan-raw-bidder-response-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanRawBidderResponseHook.class);

    @Override
    public Future<InvocationResult<BidderResponsePayload>> call(BidderResponsePayload payload,
            BidderInvocationContext invocationContext) {
            logger.info("{0} called", CODE);
            return Future.succeededFuture();
    }

    @Override
    public String code() {
        return CODE;
    }

}
