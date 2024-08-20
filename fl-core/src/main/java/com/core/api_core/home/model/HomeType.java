package com.core.api_core.home.model;

import com.fasterxml.jackson.annotation.JsonValue;

// 홈 타입 뭐가 있는지 추가
public enum HomeType {
    WHOLE_PROPERTY_RENT("Whole property rent"),
    SHARED_ROOM("Sheared room"),
    PRIVATE_ROOM("Private room"),
    HOME_STAY("Home stay"),
    STUDIO("Studio"), //(원룸)
    STUDENT_ACCOMMODATION("Student accommodation"),
    ONE_BED_FLAT("One bed flat");

    private final String value;

    HomeType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
