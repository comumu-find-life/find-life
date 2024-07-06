package com.service.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateProtectedDealDto {
    private Long getterId;

    private Long providerId;

    // 계약금/보증금
    private double bond;
}
