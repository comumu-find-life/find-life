package com.common.login.response;

import com.common.login.JwtTokenDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse{
    String accessToken;
    String refreshToken;

}