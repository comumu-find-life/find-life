package com.common.login.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse{
    String accessToken;
    String refreshToken;

}