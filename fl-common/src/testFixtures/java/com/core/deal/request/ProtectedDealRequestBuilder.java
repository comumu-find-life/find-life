package com.core.deal.request;

import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;

public class ProtectedDealRequestBuilder {

    public static ProtectedDealGeneratorRequest createProtectedDealGeneratorRequest() {
        return ProtectedDealGeneratorRequest.builder()
                .getterId(1L)
                .providerId(2L)
                .homeId(1L)
                .dmId(1L)
                .deposit(10000)
                .account("123-123-123")
                .accountHolder("minseok")
                .bankName("bankName")
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
