package org.prebid.server.hooks.modules.arcspan.contextual.hooks;

import org.prebid.server.hooks.v1.InvocationContext;
import org.prebid.server.hooks.v1.InvocationResult;
import org.prebid.server.hooks.v1.entrypoint.EntrypointHook;
import org.prebid.server.hooks.v1.entrypoint.EntrypointPayload;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.core.Future;

public class ArcSpanEntrypointHook implements EntrypointHook {
    
    private static final String CODE = "arcspan-entrypoint-hook";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanEntrypointHook.class);

    @Override
    public String code() {
        return CODE;
    }

    @Override
    public Future<InvocationResult<EntrypointPayload>> call(EntrypointPayload payload,
            InvocationContext invocationContext) {
                logger.info("{0} called", CODE);

                logger.info("QUERY PARAMS VALUE: {0}", payload.queryParams());
                logger.info("HEADERS VALUE: {0}", payload.headers());
                logger.info("BODY VALUE: {0}", payload.body());

                return Future.succeededFuture();
    }
}