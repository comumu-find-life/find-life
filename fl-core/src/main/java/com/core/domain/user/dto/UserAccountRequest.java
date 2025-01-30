package com.core.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAccountRequest {

    private String depositorName;

    private String paypalInformation;
}
