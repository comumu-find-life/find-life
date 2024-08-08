package com.common.deal.response;

import com.core.api_core.deal.model.DealState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyProtectedDealResponse {
    private Long id;

    private DealState dealState;

    private double deposit;

    // 수수료
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
}
