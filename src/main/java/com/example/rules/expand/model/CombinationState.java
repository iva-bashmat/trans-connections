package com.example.rules.expand.model;

public enum CombinationState {
    OK("OK"), DISABLED("DI"), UNKNOWN("UN");
    private final String code;

    CombinationState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
