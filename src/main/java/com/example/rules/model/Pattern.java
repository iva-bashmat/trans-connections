package com.example.rules.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.rules.model.Area.X;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Pattern extends AbstractCombination {
    public static final Long NUMBER_OF_STOPS = 4L;
    private long blockedStopsCount;
    private List<Area> allStops;

    private Pattern() {

    }

    public static Pattern of(String line) {
        var parts = line.split("-");
        if (parts.length != 6) {
            throw new IllegalArgumentException(String.format("Invalid pattern representation: %s", line));
        }

        var from = Optional.ofNullable(AbstractCombination.parseArea(parts[0]))
                .filter(area -> !X.equals(area))
                .orElseThrow(() -> new IllegalArgumentException(String.format("From has to be set, pattern: %s", line)));
        var to = Optional.ofNullable(AbstractCombination.parseArea(parts[5]))
                .filter(area -> !X.equals(area))
                .orElseThrow(() -> new IllegalArgumentException(String.format("To has to be set, pattern: %s", line)));
        var pattern = new Pattern();
        pattern.setFrom(from);
        pattern.setTo(to);

        var allStops = new ArrayList<Area>();
        for (int i = 1; i <= 4; i++) {
            allStops.add(AbstractCombination.parseArea(parts[i]));
        }

        for (int i = 1; i < allStops.size(); i++) {
            var prev = allStops.get(i - 1);
            var current = allStops.get(i);

            if (prev == null) {
                if (current != null && !current.equals(X)) {
                    throw new IllegalArgumentException(String.format("Stop cannot be set if previous is not set, pattern: %s", line));
                }
            }

            if (X.equals(prev)) {
                if (current == null || X.equals(current)) {
                    allStops.set(i, X);
                } else {
                    throw new IllegalArgumentException(String.format("Stop cannot be set if previous is blocked, pattern: %s", line));
                }
            }
        }
        pattern.setAllStops(allStops);
        pattern.setBlockedStopsCount(allStops.stream().filter(X::equals).count());
        pattern.setStops(allStops.stream().filter(stop -> !X.equals(stop)).filter(Objects::nonNull).collect(toList()));

        return pattern;
    }


    @Override
    public String toString() {
        return String.format("%s-%s-%s", getFrom().name(), formatStops(), getTo().name());
    }

    private String formatStops() {
        return allStops.stream().map(stop -> Optional.ofNullable(stop).map(Area::name).orElse("[]")).collect(joining("-"));
    }
}
