package org.prebid.server.hooks.modules.arcspan.contextual.hooks;

import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.bidder.BidderInvocationContext;
import org.prebid.server.hooks.v1.bidder.BidderRequestHook;
import org.prebid.server.hooks.v1.bidder.BidderRequestPayload;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

public class ArcSpanBidderRequestHook implements BidderRequestHook {

    private static final String CODE = "arcspan-bidder-request-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanBidderRequestHook.class);

    @Override
    public Future<InvocationResult<BidderRequestPayload>> call(BidderRequestPayload payload,
            BidderInvocationContext invocationContext) {
            logger.info("{0} called", CODE);

            logger.info("Bidder Request with Site {0}", payload.bidRequest().getSite());

            return Future.succeededFuture();
    }

    @Override
    public String code() {
        return CODE;
    }
    
}
