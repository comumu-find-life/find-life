package com.batch.login;

import lombok.Getter;

@Getter
public class JwtTokenDto {
    private String accessToken;
    private String refreshToken;
}
