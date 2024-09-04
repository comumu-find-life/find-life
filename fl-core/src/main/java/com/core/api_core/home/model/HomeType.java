package com.core.api_core.home.model;

import com.fasterxml.jackson.annotation.JsonValue;

// 홈 타입 뭐가 있는지 추가
public enum HomeType {
    WHOLE_PROPERTY_RENT(),
    SHARED_ROOM(),
    PRIVATE_ROOM(),
    HOME_STAY(),
    STUDIO(), //(원룸)
    STUDENT_ACCOMMODATION(),
    ONE_BED_FLAT();

//    private final String value;
//
//    HomeType(String value) {
//        this.value = value;
//    }
//
//    @JsonValue
//    public String getValue() {
//        return value;
//    }
}
