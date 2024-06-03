package com.batch.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class LoginFormDto {
    private String email;
    private String password;
}
