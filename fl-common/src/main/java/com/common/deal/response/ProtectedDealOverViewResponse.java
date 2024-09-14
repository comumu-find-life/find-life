package com.common.deal.response;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProtectedDealOverViewResponse {

    private Long id;

    private LocalDate createDate;
    // 거래금액
    private double deposit;

}
