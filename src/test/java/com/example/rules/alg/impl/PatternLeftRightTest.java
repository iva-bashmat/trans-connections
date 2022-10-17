package com.example.rules.alg.impl;

import com.example.rules.model.Combination;
import com.example.rules.model.Pattern;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternLeftRightTest {
    private final PatternLeftRight alg = new PatternLeftRight();

    @Test
    public void testBuildCombinations() {
        assertEquals(6, alg.buildCombinations(Pattern.of("R14-A1-A2---R14")).size());
        assertEquals(3, alg.buildCombinations(Pattern.of("R14-A1-A2--X-R14")).size());
        assertEquals(1, alg.buildCombinations(Pattern.of("R14-A1-A2-X--R14")).size());
        assertEquals(5, alg.buildCombinations(Pattern.of("R14-----R14")).size());
    }

    @Test
    public void testPopulateNextStop() {
        var combinations = alg.populateNextStop(Combination.of("A1-A1")).collect(Collectors.toSet());
        assertEquals(1, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2")).collect(Collectors.toSet());
        assertEquals(2, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2-A1")).collect(Collectors.toSet());
        assertEquals(2, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2-A2")).collect(Collectors.toSet());
        assertEquals(1, combinations.size());

        combinations = alg.populateNextStop(Combination.of("A1-A2-A3")).collect(Collectors.toSet());
        assertEquals(2, combinations.size());
    }

}
