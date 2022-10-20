package com.example.rules.model;


import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.rules.model.Area.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CombinationTest {

    @Test
    public void testOf() {
        assertThrows(IllegalArgumentException.class, () -> Combination.of("A1"), "Invalid combination representation: A1");
        assertThrows(IllegalArgumentException.class, () -> Combination.of("A1-A1-A1-A1-A1-A1-A1"), "Invalid combination representation: A1-A1-A1-A1-A1-A1-A1");
        assertThrows(IllegalArgumentException.class, () -> Combination.of("A1-X"), "Empty or X area is not allowed in combination: A1-X");
        assertThrows(IllegalArgumentException.class, () -> Combination.of("A1--A2"), "Empty or X area is not allowed in combination: A1--A2");

        var combination = Combination.of("A1-A2-A3");
        assertEquals(A1, combination.getFrom());
        assertEquals(A3, combination.getTo());
        assertEquals(1, combination.getStops().size());
        assertEquals(A2, combination.getStops().get(0));
    }

    @Test
    public void testCompareTo() {
        var combinationThis = new Combination(A1, A1, List.of());
        var combinationOther = new Combination(A1, A2, List.of());
        assertEquals(-1, combinationThis.compareTo(combinationOther));

        combinationThis = new Combination(A1, A2, List.of(A1));
        combinationOther = new Combination(A1, A2, List.of());
        assertEquals(1, combinationThis.compareTo(combinationOther));

        combinationThis = new Combination(R14, R14, List.of(A5, A5, A5, A5));
        combinationOther = new Combination(R14, R14, List.of(R14));
        assertEquals(-1, combinationThis.compareTo(combinationOther));
    }
}
