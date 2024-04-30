package com.service.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAccountRequest {
    private Long userId;

    private Integer account;

    private String bank;

}
