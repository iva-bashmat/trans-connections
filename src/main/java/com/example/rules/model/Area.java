package com.example.rules.model;

import org.springframework.util.StringUtils;

import java.util.Optional;

public enum Area {
    A1(1), A2(2), A3(3), A4(4), A5(5), R14(6), X(0);
    private final Integer multiplier;

    Area(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public static Optional<Area> toArea(String area) {
        if (StringUtils.hasLength(area)) {
            return Optional.of(Area.valueOf(Area.class, area));
        } else
            return Optional.empty();
    }

    public static Optional<Area> toDefinedArea(String area) {
        return Area.toArea(area).filter(a -> !Area.X.equals(a));
    }
}
