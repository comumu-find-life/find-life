package com.common.chat.response;

import com.common.deal.response.ProtectedDealResponse;
import com.common.home.response.HomeInformationResponse;
import com.common.user.response.UserProfileResponse;
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
