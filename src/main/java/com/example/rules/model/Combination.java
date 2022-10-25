package com.example.rules.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Data
public class Combination extends AbstractCombination implements Cloneable, Comparable<Combination> {

    private Combination() {

    }

    public Combination(Area from, Area to, List<Area> stops) {
        super(from, to, stops);
    }

    public Combination(List<Area> areas) {
        super(areas);
    }

    public static Combination of(String line) {
        var parts = line.split("-");
        if (parts.length < 2 || parts.length > 6) {
            throw new IllegalArgumentException(String.format("Invalid combination representation: %s", line));
        }
        var areas = Arrays.stream(parts)
                .map(part -> Area.toDefinedArea(part)
                        .orElseThrow(() -> new IllegalArgumentException("Empty or X area is not allowed in combination: %s")))
                .collect(toList());

        var combination = new Combination();
        combination.setFrom(areas.get(0));
        combination.setTo(areas.get(areas.size() - 1));
        combination.setStops(new ArrayList<>());

        if (areas.size() > 2) {
            combination.getStops().addAll(areas.subList(1, areas.size() - 1));
        }
        return combination;
    }

    public List<Area> toListAreas() {
        var areas = new ArrayList<Area>();
        areas.add(getFrom());
        areas.addAll(getStops());
        areas.addAll(Collections.nCopies(AbstractCombination.NUMBER_OF_INTERMEDIATE_STOPS.intValue() - getStops().size(), null));
        areas.add(getTo());
        return areas;
    }

    @Override
    public Combination clone() {
        return new Combination(getFrom(), getTo(), new ArrayList<>(getStops()));
    }

    @Override
    public int hashCode() {
        var indexes = new HashMap<Integer, Integer>();
        indexes.put(0, 343);
        indexes.put(1, 49);
        indexes.put(2, 7);
        indexes.put(3, 1);

        var result = getFrom().getMultiplier() * 10000000 + getTo().getMultiplier() * 1000000;
        for (int i = 0; i < getStops().size(); i++) {
            result += getStops().get(i).getMultiplier() * indexes.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Combination)) {
            return false;
        }

        var other = (Combination) o;

        return this.getFrom().equals(other.getFrom()) &&
                this.getTo().equals(other.getTo()) &&
                this.getStops().equals(other.getStops());
    }

    @Override
    public int compareTo(Combination o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }

    @Override
    public String toString() {
        return Stream.of(getFrom().name(), formatStops(), getTo().name()).filter(StringUtils::hasLength).collect(joining("-"));
    }


    private String formatStops() {
        return getStops().stream().map(Area::name).collect(joining("-"));
    }
}
