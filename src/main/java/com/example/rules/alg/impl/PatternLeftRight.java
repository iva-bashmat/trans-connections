package com.example.rules.alg.impl;

import com.example.rules.model.Combination;

import java.util.stream.Stream;

public class PatternLeftRight extends PatternAlgorithm {

    @Override
    protected Stream<Combination> populateNextStop(Combination combination) {
        var left = combination.getStops().stream().reduce((first, second) -> second).orElse(combination.getFrom());
        var right = combination.getTo();

        var combinationLeft = combination.clone();
        combinationLeft.getStops().add(left);

        var combinationRight = combination.clone();
        combinationRight.getStops().add(right);

        return Stream.of(combinationLeft, combinationRight);
    }

}
