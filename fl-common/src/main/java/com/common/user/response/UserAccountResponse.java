package com.common.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountResponse {

    private String bsb;

    private String accountNumber;

    private double point;

    private List<PointChargeHistoryResponse> chargeHistories;
}
