package com.core.api_core.user.dto;

public enum PlatformType {
    ANDROID,
    IOS;

    public boolean isAndroid(){
        return this == ANDROID;
    }
}
