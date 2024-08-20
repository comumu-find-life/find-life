package com.core.api_core.home.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BathroomType {
    SHARED("Shared Bathroom"),
    OWN("Own Bathroom"),
    ENSUITE("Ensuite Bathroom");

    private final String value;

    BathroomType (String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
