package com.example.rules.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.rules.model.Area.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatternTest {

    @Test
    public void testConstructor() {
        var pattern = new Pattern(List.of(A1, A1, A1, A2, A3));
        assertEquals(A1, pattern.getFrom());
        assertEquals(A3, pattern.getTo());
        assertEquals(3, pattern.getStops().size());
    }

    @Test
    public void testOf() {
        assertThrows(IllegalArgumentException.class, () -> Pattern.of("A1"), "Invalid pattern representation: A1");
        assertThrows(IllegalArgumentException.class, () -> Pattern.of("-----A1"), "From has to be set, pattern: -----A1");
        assertThrows(IllegalArgumentException.class, () -> Pattern.of("X-----A1"), "From has to be set, pattern: X-----A1");
        assertThrows(IllegalArgumentException.class, () -> Pattern.of("A1-----"), "To has to be set, pattern: A1-----");
        assertThrows(IllegalArgumentException.class, () -> Pattern.of("A1-----X"), "To has to be set, pattern: A1-----X");
        assertThrows(IllegalArgumentException.class, () -> Pattern.of("A1--A1---A1"), "Stop cannot be set if previous is not set, pattern: A1--A1---A1");
        assertThrows(IllegalArgumentException.class, () -> Pattern.of("A1-X-A1---A1"), "Stop cannot be set if previous is blocked, pattern: A1-X-A1---A1");

        var pattern = Pattern.of("A1-A2--X--A3");
        assertEquals(A1, pattern.getFrom());
        assertEquals(A3, pattern.getTo());

        assertEquals(1, pattern.getStops().size());
        assertEquals(A2, pattern.getStops().get(0));
        assertEquals(2L, pattern.getBlockedStopsCount());
    }
}
