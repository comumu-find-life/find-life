package com.common.user.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class LoginResponse{
    String accessToken;
    String refreshToken;

}