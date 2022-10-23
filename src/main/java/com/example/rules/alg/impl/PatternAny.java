package com.example.rules.alg.impl;

import com.example.rules.model.Area;
import com.example.rules.model.Combination;

import java.util.HashSet;
import java.util.stream.Stream;

public class PatternAny extends PatternAlgorithm {

    @Override
    protected Stream<Combination> populateNextStop(Combination combination) {
        var distinctAreas = new HashSet<Area>();
        distinctAreas.add(combination.getFrom());
        distinctAreas.add(combination.getTo());
        distinctAreas.addAll(combination.getStops());
        return distinctAreas.stream().map(area -> {
            var newCombination = combination.clone();
            newCombination.getStops().add(area);
            return newCombination;
        });
    }
}
