package com.common.deal.response;


import com.core.api_core.deal.model.DealState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Getter 가 조회할 안전거래 정보
 */
@Getter
@Builder
public class ProtectedDealByGetterResponse {
    private Long id;
    private DealState dealState;
    private double deposit;
    private double fee;
    private double totalPrice;
    private LocalDateTime startDate;
    private LocalDateTime depositDate;
    private LocalDateTime finishDate;
    private LocalDateTime cancelDate;
    private String address;
    private String homeImage;
    private double rent;
    private double bill;
    private double bond;
    private String randomDepositorName;
}
