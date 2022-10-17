package com.example.rules.model;

public enum Area {
    A1(1), A2(2), A3(3), A4(4), A5(5), R14(6), X(0);
    private final Integer multiplier;

    Area(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public Integer getMultiplier() {
        return multiplier;
    }
}
