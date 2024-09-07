package com.api.helper;

import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.ProtectedDealDateTime;
import com.core.api_core.deal.model.ProviderAccount;

import java.time.LocalDateTime;

public class ProtectedDealHelper {

    public static ProtectedDealGeneratorRequest generateProtectedDealGeneratorRequest(){
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

    public static ProtectedDealFindRequest generateProtectedDealFindRequest() {
        return ProtectedDealFindRequest.builder()
                .homeId(1L)
                .dmId(1L)
                .getterId(2L)
                .providerId(1L)
                .build();
    }

    public static ProtectedDeal generateProtectedDeal(){
        return ProtectedDeal.builder()
                .homeId(1L)
                .dmId(1L)
                .randomDepositorName("randomString")
                .getterId(2L)
                .providerId(1L)
                .deposit(2000)
                .protectedDealDateTime(generateProtectedDealDateTime())
                .providerAccount(generateProviderAccount())
                .dealState(DealState.BEFORE_DEPOSIT)
                .build();
    }

    public static ProtectedDealDateTime generateProtectedDealDateTime(){
        return ProtectedDealDateTime.builder()
                .dealStartDateTime(LocalDateTime.of(2024,10,31,14,15))
                .build();
    }

    public static ProviderAccount generateProviderAccount() {
        return ProviderAccount.builder()
                .accountHolder("minseok")
                .bankName("bankName")
                .account("account")
                .build();
    }
}
