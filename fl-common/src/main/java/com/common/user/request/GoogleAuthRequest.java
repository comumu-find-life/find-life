package com.common.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GoogleAuthRequest {
    private String idToken;
    // Android, Ios
    private PlatformType platformType;
}
