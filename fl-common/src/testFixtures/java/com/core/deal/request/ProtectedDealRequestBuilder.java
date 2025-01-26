package com.core.deal.request;

import com.core.api_core.deal.dto.ProtectedDealFindRequest;
import com.core.api_core.deal.dto.ProtectedDealGeneratorRequest;

public class ProtectedDealRequestBuilder {

    public static ProtectedDealGeneratorRequest createProtectedDealGeneratorRequest() {
        return ProtectedDealGeneratorRequest.builder()
                .getterId(2L)
                .providerId(1L)
                .homeId(1L)
                .dmId(1L)
                .deposit(10000)
                .build();
    }

    public static ProtectedDealFindRequest createProtectedDealFindRequest(Long homeId) {
        return ProtectedDealFindRequest.builder()
                .homeId(homeId)
                .dmId(1L)
                .getterId(2L)
                .providerId(1L)
                .build();
    }


}
