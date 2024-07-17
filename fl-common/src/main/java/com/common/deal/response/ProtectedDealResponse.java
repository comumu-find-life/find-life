package com.common.deal.response;

import com.core.api_core.deal.model.DealState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProtectedDealResponse {

    private Long id;

    private Long getterId;

    private Long providerId;

    private Long homeId;

    //보증금
    private double deposit;

    private String account;

    private String accountHolder;

    private String bankName;

    private DealState dealState;

}
