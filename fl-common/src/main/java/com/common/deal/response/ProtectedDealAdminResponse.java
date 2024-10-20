package com.common.deal.response;

import com.common.home.response.HomeInformationResponse;
import com.common.user.response.UserInformationByAdminResponse;
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
