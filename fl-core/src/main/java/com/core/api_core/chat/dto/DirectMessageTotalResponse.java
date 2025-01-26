package com.core.api_core.chat.dto;

import com.core.api_core.deal.dto.ProtectedDealResponse;
import com.core.api_core.home.dto.HomeInformationResponse;
import com.core.api_core.user.dto.UserProfileResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DirectMessageTotalResponse {
    private UserProfileResponse sender;
    private UserProfileResponse receiver;
    private List<ProtectedDealResponse> protectedDealResponse;
    private HomeInformationResponse homeInformationResponse;
    private boolean isExistAccount;
}
