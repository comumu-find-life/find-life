package com.core.api_core.home.model;

import com.fasterxml.jackson.annotation.JsonValue;

// 홈 타입 뭐가 있는지 추가
public enum HomeType {
    SHARED_ROOM(),
    PRIVATE_ROOM(),
    HOME_STAY(),
    STUDIO(), //(원룸)
    ONE_BED_FLAT();
}
