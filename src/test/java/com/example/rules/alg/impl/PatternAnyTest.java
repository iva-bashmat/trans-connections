package com.example.rules.alg.impl;

import com.example.rules.model.Combination;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternAnyTest {
    private final PatternAny alg = new PatternAny();

    @Test
    public void testPopulateNextStop() {
        var combinations = alg.populateNextStop(Combination.of("A1-A1")).collect(Collectors.toSet());
        assertEquals(1, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2")).collect(Collectors.toSet());
        assertEquals(2, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2-A1")).collect(Collectors.toSet());
        assertEquals(2, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2-A2")).collect(Collectors.toSet());
        assertEquals(2, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2-A3")).collect(Collectors.toSet());
        assertEquals(3, combinations.size());
    }
}
