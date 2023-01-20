package org.prebid.server.hooks.modules.arcspan.contextual.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ArcCodes {
    
    @JsonProperty("images")
    List<String> images;

    @JsonProperty("text")
    List<String> text;

}
