package com.service.deal.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProtectedDealRequest {
    private Long id;

    private Long getterId;

    private Long providerId;

    private double bond;

    private double charge;

    private double totalPrice;
}
