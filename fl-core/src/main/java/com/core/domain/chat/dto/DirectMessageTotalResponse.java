package com.core.domain.chat.dto;

import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.user.dto.UserProfileResponse;
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
