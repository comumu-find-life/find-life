package com.service.deal.dto;

import com.core.deal.model.DealState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProtectedDealResponse {

    //보증금
    private double bond;

    //수수료
    private double charge;

    //최종 결제 금액
    private double totalPrice;

}
