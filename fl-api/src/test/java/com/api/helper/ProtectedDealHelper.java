package com.api.helper;

import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;

public class ProtectedDealHelper {

    public static ProtectedDealGeneratorRequest generateProtectedDealGeneratorRequest(){
        return ProtectedDealGeneratorRequest.builder()
                .getterId(1L)
                .providerId(2L)
                .dmId(1L)
                .deposit(10000)
                .account("123-123-123")
                .accountHolder("minseok")
                .bankName("bankName")
                .build();
    }

    public static ProtectedDealFindRequest generateProtectedDealFindRequest() {
        return ProtectedDealFindRequest.builder()
                .getterId(1L)
                .providerId(2L)
                .homeId(1L)
                .dmId(1L)
                .build();
    }
}
