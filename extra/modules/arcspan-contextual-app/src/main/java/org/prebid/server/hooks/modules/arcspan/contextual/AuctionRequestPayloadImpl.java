package org.prebid.server.hooks.modules.arcspan.contextual;

import com.iab.openrtb.request.BidRequest;
import lombok.Value;
import lombok.experimental.Accessors;
import org.prebid.server.hooks.v1.auction.AuctionRequestPayload;

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class AuctionRequestPayloadImpl implements AuctionRequestPayload {

    BidRequest bidRequest;

}