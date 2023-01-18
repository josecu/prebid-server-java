package org.prebid.server.hooks.modules.arcspan.contextual;

import org.prebid.server.hooks.v1.Hook;
import org.prebid.server.hooks.v1.InvocationContext;
import org.prebid.server.hooks.v1.Module;

import java.util.Collection;
import java.util.List;

public class ArcSpanContextualAppModule implements Module {

    public static final String CODE = "arcspan-contextual-app";

    private final List<? extends Hook<?, ? extends InvocationContext>> hooks;

    public ArcSpanContextualAppModule() {
        System.out.println("ArcSpanContextualAppModule CREATED");
        hooks = List.of(
            new ArcSpanRequestHook()
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
