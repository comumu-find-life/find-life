package com.common.deal.response;

import com.core.api_core.deal.model.DealState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Provider 가 조회할 안전 거래 정보
 */
@Getter
@Builder
public class ProtectedDealByProviderResponse  {
    private String account;
    private String accountHolder;
    private String bankName;
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
}
