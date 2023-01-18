package org.prebid.server.hooks.modules.arcspan.contextual;

import org.prebid.server.hooks.v1.Hook;
import org.prebid.server.hooks.v1.InvocationContext;
import org.prebid.server.hooks.v1.Module;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class ArcSpanContextualAppModule implements Module {

    public static final String CODE = "arcspan-contextual-app";
    private static final Logger logger = LoggerFactory.getLogger(ArcSpanContextualAppModule.class);

    private final List<? extends Hook<?, ? extends InvocationContext>> hooks;

    public ArcSpanContextualAppModule() {
        logger.info("ArcSpanContextualAppModule instantiated");
        hooks = List.of(
            new ArcSpanEntrypointHook()
        );
    }

    @Override
    public String code() {
        return CODE;
    }

    @Override
    public Collection<? extends Hook<?, ? extends InvocationContext>> hooks() {
        return hooks;
    }
}
