package com.core.domain.deal.dto;

import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.user.dto.UserInformationByAdminResponse;
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
