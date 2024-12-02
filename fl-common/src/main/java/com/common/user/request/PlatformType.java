package com.common.user.request;

public enum PlatformType {
    ANDROID,
    IOS;

    public boolean isAndroid(){
        return this == ANDROID;
    }
}
