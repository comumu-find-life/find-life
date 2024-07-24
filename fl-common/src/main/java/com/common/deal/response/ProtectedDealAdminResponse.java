package com.common.deal.response;

import com.common.home.response.HomeInformationResponse;
import com.common.user.response.UserInformationByAdminResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProtectedDealAdminResponse {

    private Long id;

    private HomeInformationResponse homeInformationResponse;

    private UserInformationByAdminResponse getter;

    private UserInformationByAdminResponse provider;

    private double deposit;

}
