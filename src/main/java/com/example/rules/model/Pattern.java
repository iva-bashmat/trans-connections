package com.example.rules.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.example.rules.model.Area.X;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Getter
@Setter(value = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Pattern extends AbstractCombination {
    private long blockedStopsCount;
    private List<Area> allStops;

    private Pattern() {

    }

    public Pattern(List<Area> areas) {
        super(areas);
        this.allStops = new ArrayList<>(Collections.nCopies(4, null));
        for (int i = 1; i <= areas.size() - 2; i++) {
            this.allStops.set(i - 1, areas.get(i));
        }
    }

    public static Pattern of(String line) {
        var parts = line.split("-");
        if (parts.length != 6) {
            throw new IllegalArgumentException(String.format("Invalid pattern representation: %s", line));
        }

        var from = Area.toDefinedArea(parts[0])
                .orElseThrow(() -> new IllegalArgumentException(String.format("From has to be set, pattern: %s", line)));
        var to = Area.toDefinedArea(parts[5])
                .orElseThrow(() -> new IllegalArgumentException(String.format("To has to be set, pattern: %s", line)));
        var pattern = new Pattern();
        pattern.setFrom(from);
        pattern.setTo(to);

        var allStops = new ArrayList<Area>();
        for (int i = 1; i <= 4; i++) {
            allStops.add(Area.toArea(parts[i]).orElse(null));
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

    public void setBlockedStops(long blockedStopsCount) {
        this.blockedStopsCount = blockedStopsCount;
        for (int i = allStops.size() - 1; i > allStops.size() - 1 - blockedStopsCount; i--) {
            allStops.set(i, X);
            setStops(allStops.stream().filter(stop -> !X.equals(stop)).filter(Objects::nonNull).collect(toList()));
        }
    }
}
