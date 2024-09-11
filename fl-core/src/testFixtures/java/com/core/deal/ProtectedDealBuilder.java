package com.core.deal;

import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.ProtectedDealDateTime;
import com.core.api_core.deal.model.ProviderAccount;

import java.time.LocalDateTime;

public class ProtectedDealBuilder {

    public static ProtectedDeal createProtectedDeal(Long homeId){
        return ProtectedDeal.builder()
                .homeId(homeId)
                .dmId(1L)
                .randomDepositorName("randomString")
                .getterId(2L)
                .providerId(1L)
                .deposit(2000)
                .protectedDealDateTime(createProtectedDealDateTime())
                .providerAccount(createProviderAccount())
                .dealState(DealState.BEFORE_DEPOSIT)
                .build();
    }

    public static ProtectedDealDateTime createProtectedDealDateTime(){
        return ProtectedDealDateTime.builder()
                .dealStartDateTime(LocalDateTime.of(2024,10,31,14,15))
                .build();
    }

    public static ProviderAccount createProviderAccount() {
        return ProviderAccount.builder()
                .accountHolder("minseok")
                .bankName("bankName")
                .account("account")
                .build();
    }
}
