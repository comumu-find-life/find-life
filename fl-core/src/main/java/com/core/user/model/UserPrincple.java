package com.core.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPrincple {
    private String email;
    private Role role;
}
