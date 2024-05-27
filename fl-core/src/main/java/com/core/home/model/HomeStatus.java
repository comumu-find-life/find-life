package com.core.home.model;

public enum HomeStatus {
    SOLD_OUT("판매 완료"),
    FOR_SALE("판매 중");

    private final String description;

    HomeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}