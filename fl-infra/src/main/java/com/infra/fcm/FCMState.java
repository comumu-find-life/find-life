package com.infra.fcm;

public enum FCMState {
    SAVE("save"),
    NOT_SAVE("notSave");

    private final String value;

    FCMState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
