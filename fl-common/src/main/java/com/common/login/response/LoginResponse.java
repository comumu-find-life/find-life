package com.common.login.response;

import com.common.login.JwtTokenDto;
import lombok.Getter;

@Getter
public class LoginResponse {
    private boolean success;
    private String message;

    private JwtTokenDto data;
}
