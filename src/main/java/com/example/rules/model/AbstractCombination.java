package com.example.rules.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractCombination {
    public static final Long NUMBER_OF_INTERMEDIATE_STOPS = 4L;
    public static final Long NUMBER_OF_ALL_STOPS = 6L;

    private Area from;
    private Area to;
    private List<Area> stops;

    public AbstractCombination(List<Area> areas) {
        if (areas.size() < 2 || areas.size() > 6) {
            throw new IllegalArgumentException("Invalid representation");
        }
        this.setFrom(areas.get(0));
        this.setTo(areas.get(areas.size() - 1));
        this.setStops(new ArrayList<>());
        if (areas.size() > 2) {
            this.getStops().addAll(areas.subList(1, areas.size() - 1));
        }
    }
}
