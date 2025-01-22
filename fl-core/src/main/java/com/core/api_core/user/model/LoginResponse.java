package com.core.api_core.user.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse{
    String accessToken;
    String refreshToken;

}