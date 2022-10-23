package com.example.rules.alg.impl;

import com.example.rules.model.Combination;
import com.example.rules.model.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SequenceAlgorithmTest {
    @Test
    public void testBuildCombinations() {
        var sequence = Sequence.of("A1-X-A1");
        var combinations = new SequenceAlgorithm().buildCombinations(sequence);
        assertEquals(4, combinations.size());

        sequence = Sequence.of("A1-X-X-A2");
        combinations = new SequenceAlgorithm().buildCombinations(sequence);
        assertEquals(6, combinations.size());

        assertEquals(Combination.of("A1-A2"), combinations.first());
        assertEquals(Combination.of("A1-A1-A2"), combinations.higher(combinations.first()));

        sequence = Sequence.of("A1-A2-A3");
        combinations = new SequenceAlgorithm().buildCombinations(sequence);
        assertEquals(20, combinations.size());

        assertEquals(Combination.of("A1-A1-A1-A1-A2-A3"), combinations.first());
        assertEquals(Combination.of("A1-A1-A1-A2-A3"), combinations.higher(combinations.first()));
    }
}
