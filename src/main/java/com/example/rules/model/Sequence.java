package com.example.rules.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rules.model.Area.X;
import static java.util.stream.Collectors.toList;

@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Sequence {
    private List<Area> allStops;
    private List<Area> stops;
    private long blockedStopsCount;

    private Sequence() {

    }

    public static Sequence of(String line) {
        var parts = line.split("-");
        if (parts.length == 0 || parts.length > 6) {
            throw new IllegalArgumentException(String.format("Invalid sequence representation: %s", line));
        }

        var areas = Arrays.stream(parts)
                .map(part -> Area.toArea(part)
                        .orElseThrow(() -> new IllegalArgumentException("Empty area is not allowed in sequence: %s")))
                .collect(toList());

        if (X.equals(areas.get(0))) {
            throw new IllegalArgumentException(String.format("From has to be set, sequence: %s", line));
        }

        if (X.equals(areas.get(areas.size() - 1))) {
            throw new IllegalArgumentException(String.format("From has to be set, sequence: %s", line));
        }

        if (areas.size() > 2) {
            var intraAreas = areas.subList(1, areas.size() - 1);
            for (int i = 1; i < intraAreas.size(); i++) {
                var prev = intraAreas.get(i - 1);
                var current = intraAreas.get(i);

                if (X.equals(prev) && !X.equals(current)) {
                    throw new IllegalArgumentException(String.format("Stop cannot be set if previous is blocked, sequence: %s", line));
                }
            }
        }
        var sequence = new Sequence();
        sequence.setAllStops(areas);
        sequence.setBlockedStopsCount(areas.stream().filter(X::equals).count());
        sequence.setStops(areas.stream().filter(area -> !X.equals(area)).collect(toList()));
        return sequence;
    }

    @Override
    public String toString() {
        return allStops.stream().map(Area::name).collect(Collectors.joining("-"));
    }

}

