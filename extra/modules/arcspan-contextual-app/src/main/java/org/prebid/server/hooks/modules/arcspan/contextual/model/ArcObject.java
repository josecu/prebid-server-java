package org.prebid.server.hooks.modules.arcspan.contextual.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ArcObject {
    
    @JsonProperty("raw")
    ArcCodes raw;

    @JsonProperty("codes")
    ArcCodes codes;

    @JsonProperty("newcodes")
    ArcCodes newcodes;
    
}
