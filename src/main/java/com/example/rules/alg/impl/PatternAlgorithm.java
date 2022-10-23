package com.example.rules.alg.impl;

import com.example.rules.alg.ExpansionAlgorithm;
import com.example.rules.model.Combination;
import com.example.rules.model.Pattern;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class PatternAlgorithm extends ExpansionAlgorithm<Pattern> {

    @Override
    public TreeSet<Combination> buildCombinations(Pattern pattern) {
        var minStops = pattern.getStops().size();
        var maxStops = Pattern.NUMBER_OF_INTERMEDIATE_STOPS - pattern.getBlockedStopsCount();

        var currentStopsNumber = minStops;
        Set<Combination> combinationCurrent = new TreeSet<>();
        combinationCurrent.add(new Combination(pattern.getFrom(), pattern.getTo(), new ArrayList<>(pattern.getStops())));

        var combinations = new TreeSet<>(combinationCurrent);
        while (currentStopsNumber < maxStops) {
            currentStopsNumber++;
            combinationCurrent = generateNextCombinations(combinationCurrent);
            combinations.addAll(combinationCurrent);
        }

        return combinations;
    }

    private Set<Combination> generateNextCombinations(Set<Combination> combinations) {
        return combinations.stream().flatMap(this::populateNextStop).collect(Collectors.toSet());
    }

    protected abstract Stream<Combination> populateNextStop(Combination combination);

}
