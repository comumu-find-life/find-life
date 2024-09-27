package com.common.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAccountRequest {

    private String bsb;

    private String accountNumber;
}
