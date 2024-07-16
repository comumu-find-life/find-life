package com.batch.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginFormRequest {
    private String email;
    private String password;
}
