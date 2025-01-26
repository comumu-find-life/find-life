package com.core.api_core.home.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HomeType {
    SHARED_ROOM(),
    PRIVATE_ROOM(),
    HOME_STAY(),
    STUDIO(),
    ONE_BED_FLAT();
}
