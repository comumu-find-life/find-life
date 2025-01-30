package com.core.domain.user.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse{
    String accessToken;
    String refreshToken;

}