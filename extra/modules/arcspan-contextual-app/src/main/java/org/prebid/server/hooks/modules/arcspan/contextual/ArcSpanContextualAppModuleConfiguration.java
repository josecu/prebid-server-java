package org.prebid.server.hooks.modules.arcspan.contextual;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "hooks." + ArcSpanContextualAppModule.CODE, name = "enabled", havingValue = "true")
@Configuration
public class ArcSpanContextualAppModuleConfiguration {

    @Bean
    ArcSpanContextualAppModule arcSpanContextualAppModule() {
        System.out.println("ArcSpanContextualAppModuleConfiguration INSTANTIATED");
        return new ArcSpanContextualAppModule();
    }

}
