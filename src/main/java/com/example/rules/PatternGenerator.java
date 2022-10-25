package com.example.rules;

import com.example.rules.alg.impl.PatternLeftRight;
import com.example.rules.expand.CombinationBuilder;
import com.example.rules.expand.ExpandResult;
import com.example.rules.expand.model.CombinationExpanded;
import com.example.rules.expand.model.PatternExpanded;
import com.example.rules.model.AbstractCombination;
import com.example.rules.model.Area;
import com.example.rules.model.Combination;
import com.example.rules.model.Pattern;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.rules.expand.model.CombinationState.OK;
import static com.example.rules.expand.model.CombinationState.UNKNOWN;

public class PatternGenerator {
    public ExpandResult<Pattern> generate(Set<Combination> combinationsExpectedAll) {
        var combinationsExpected = new TreeSet<>(combinationsExpectedAll);

        List<Pattern> patternsAll = new ArrayList<>();
        Set<Combination> disabledCombinations = new TreeSet<>();

        while (!combinationsExpected.isEmpty()) {
            var combination = combinationsExpected.first();
            var pattern = toPattern(combination);

            var expandResult = new CombinationBuilder<>(new PatternLeftRight(), List.of(pattern), disabledCombinations).expand(combinationsExpected);

            var unknownRoutesPerSize = getUnknownCombinationsPerSize(expandResult.getPatterns());
            var okRouteMaxSize = getOkCombinationMaxSize(expandResult.getPatterns());

            for (var size : unknownRoutesPerSize.keySet()) {
                if (size <= okRouteMaxSize) {
                    disabledCombinations.addAll(unknownRoutesPerSize.get(size));
                }
            }

            pattern.setBlockedStops(AbstractCombination.NUMBER_OF_INTERMEDIATE_STOPS.intValue() - okRouteMaxSize);

            patternsAll.add(pattern);
            combinationsExpected = expandResult.getRemainingCombinations();
        }


        return new CombinationBuilder<>(new PatternLeftRight(), patternsAll, disabledCombinations).expand(combinationsExpectedAll);
    }

    private Integer getOkCombinationMaxSize(List<PatternExpanded<Pattern>> patternsExpanded) {
        return patternsExpanded.stream().map(PatternExpanded::getCombinations).flatMap(Set::stream)
                .filter(combinationExpanded -> combinationExpanded.getState().equals(OK))
                .map(CombinationExpanded::getCombination)
                .mapToInt(combination -> combination.getStops().size())
                .max().orElse(0);
    }

    private Map<Integer, List<Combination>> getUnknownCombinationsPerSize(List<PatternExpanded<Pattern>> patternsExpanded) {
        return patternsExpanded.stream().map(PatternExpanded::getCombinations).flatMap(Set::stream)
                .filter(combinationExpanded -> combinationExpanded.getState().equals(UNKNOWN))
                .map(CombinationExpanded::getCombination)
                .collect(Collectors.groupingBy(combination -> combination.getStops().size()));
    }

    protected Pattern toPattern(Combination combination) {
        var combinationAreas = combination.toListAreas();

        var areas = new ArrayList<Area>();
        if (combinationAreas.size() != 6) {
            throw new IllegalArgumentException("Full representation of combination is required");
        }

        var right = combinationAreas.get(5);
        areas.add(right);

        var lastNotNullIndex = 4;
        for (; lastNotNullIndex >= 0; lastNotNullIndex--) {
            if (combinationAreas.get(lastNotNullIndex) != null) {
                break;
            }
        }
        if (lastNotNullIndex < 0) {
            throw new IllegalArgumentException("Not valid combination, doesn't have from");
        }

        var left = combinationAreas.get(lastNotNullIndex);
        if (!right.equals(left)) {
            areas.add(left);
        }

        var remainingAreasIndex = lastNotNullIndex - 1;
        for (; remainingAreasIndex >= 0; remainingAreasIndex--) {
            var current = combinationAreas.get(remainingAreasIndex);
            if (!current.equals(left)) {
                break;
            }
        }

        for (; remainingAreasIndex >= 0; remainingAreasIndex--) {
            areas.add(combinationAreas.get(remainingAreasIndex));
        }

        if (areas.size() == 1) {
            areas.add(areas.get(0));
        }
        Collections.reverse(areas);
        return new Pattern(areas);
    }
}
