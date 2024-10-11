package com.common.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointChargeHistoryResponse {

    private Integer chargeAmount;

    private LocalDateTime chargeDate;
}
