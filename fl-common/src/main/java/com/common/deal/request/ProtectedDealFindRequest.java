package com.common.deal.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProtectedDealFindRequest {
    private Long getterId;
    private Long providerId;
    private Long homeId;
    private Long dmId;
}
