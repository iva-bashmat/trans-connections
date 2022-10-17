package com.example.rules.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    public static Combination of(String line) {
        var parts = line.split("-");
        if (parts.length < 2) {
            throw new IllegalArgumentException(String.format("Invalid combination representation: %s", line));
        }
        var combination = new Combination();
        combination.setFrom(AbstractCombination.parseArea(parts[0]));
        combination.setTo(AbstractCombination.parseArea(parts[parts.length - 1]));
        combination.setStops(new ArrayList<>());

        if (parts.length > 2) {
            var partsStops = Arrays.copyOfRange(parts, 1, parts.length - 1);
            combination.getStops().addAll(Arrays.stream(partsStops).map(AbstractCombination::parseArea).collect(toList()));
        }
        return combination;
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
            if (getStops().get(i) == null) {
                System.out.println("ooops");
            }
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
