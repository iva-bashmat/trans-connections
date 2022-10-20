package com.example.rules.model;

import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractCombination {
    public static final Long NUMBER_OF_STOPS = 4L;
    private Area from;
    private Area to;
    private List<Area> stops;
}
