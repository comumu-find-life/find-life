package com.core.api_core.deal.dto;

import com.core.api_core.home.dto.HomeInformationResponse;
import com.core.api_core.user.dto.UserInformationByAdminResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProtectedDealAdminResponse {

    private Long id;

    private HomeInformationResponse homeInformationResponse;

    private UserInformationByAdminResponse getter;

    private UserInformationByAdminResponse provider;

    private String randomDepositorName;

    private Long dmId;

    private double deposit;

}
