package com.common.deal.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProtectedDealGeneratorRequest {

    private Long getterId;

    private Long providerId;

    private Long homeId;

    private Long dmId;

    //계약금
    private double deposit;

    private String account;

    private String accountHolder;

    private String bankName;
}
