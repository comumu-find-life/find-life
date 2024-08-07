package com.core.api_core.home.model;

public enum HomeStatus {
    SOLD_OUT("판매 완료"),
    SELL_STOP("판매 중단"),
    FOR_SALE("판매 중");

    private final String description;

    HomeStatus(String description) {
        this.description = description;
    }

    public static HomeStatus fromString(String status) {
        for (HomeStatus homeStatus : HomeStatus.values()) {
            if (homeStatus.name().equalsIgnoreCase(status)) {
                return homeStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant for status: " + status);
    }


}