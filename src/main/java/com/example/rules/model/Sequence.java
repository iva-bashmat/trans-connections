package com.example.rules.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.rules.model.Area.X;
import static java.util.stream.Collectors.toList;

@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Sequence extends AbstractCombination {
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

        var sequence = new Sequence();
        sequence.setFrom(areas.get(0));
        if (X.equals(sequence.getFrom())) {
            throw new IllegalArgumentException(String.format("From has to be set, sequence: %s", line));
        }

        if (parts.length == 1) {
            sequence.setTo(sequence.getFrom());
        } else {
            var to = areas.get(areas.size() - 1);
            if (X.equals(to)) {
                throw new IllegalArgumentException(String.format("To has to be set, sequence: %s", line));
            }
            sequence.setTo(to);
        }

        for (int i = 1; i < areas.size(); i++) {
            var prev = areas.get(i - 1);
            var current = areas.get(i);

            if (current.equals(prev) && !X.equals(current)) {
                throw new IllegalArgumentException(String.format("Next stop cannot be same as previous, sequence: %s", line));
            }
        }

        sequence.setStops(new ArrayList<>());
        sequence.setBlockedStopsCount(0);
        if (areas.size() > 2) {
            var intraAreas = areas.subList(1, areas.size() - 1);
            for (int i = 1; i < intraAreas.size(); i++) {
                var prev = intraAreas.get(i - 1);
                var current = intraAreas.get(1);

                if (X.equals(prev) && !X.equals(current)) {
                    throw new IllegalArgumentException(String.format("Stop cannot be set if previous is blocked, sequence: %s", line));
                }
            }

            sequence.setBlockedStopsCount(intraAreas.stream().filter(X::equals).count());
            sequence.getStops().addAll(intraAreas.stream().filter(area -> !X.equals(area)).collect(toList()));
        }

        return sequence;
    }

}

