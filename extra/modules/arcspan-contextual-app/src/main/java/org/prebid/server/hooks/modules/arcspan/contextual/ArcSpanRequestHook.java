package org.prebid.server.hooks.modules.arcspan.contextual;

import org.prebid.server.hooks.v1.InvocationAction;
import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.auction.AuctionInvocationContext;
import org.prebid.server.hooks.v1.auction.AuctionRequestPayload;
import org.prebid.server.hooks.v1.auction.RawAuctionRequestHook;

import io.vertx.core.Future;

public class ArcSpanRequestHook implements RawAuctionRequestHook {
    
    private static final String CODE = "arcspan-request-hook";

    @Override
    public String code() {
        return CODE;
    }

    @Override
    public Future<InvocationResult<AuctionRequestPayload>> call(AuctionRequestPayload payload,
            AuctionInvocationContext invocationContext) {
                return Future.succeededFuture(
                    InvocationResultImpl.<AuctionRequestPayload>builder().action(InvocationAction.reject).build()
                );
    }
}