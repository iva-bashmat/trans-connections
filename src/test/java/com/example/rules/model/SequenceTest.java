package com.example.rules.model;

import org.junit.jupiter.api.Test;

import static com.example.rules.model.Area.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SequenceTest {
    @Test
    public void testOf() {
        assertThrows(IllegalArgumentException.class, () -> Sequence.of(""), "Invalid sequence representation: ");
        assertThrows(IllegalArgumentException.class, () -> Sequence.of("A1-A1-A1-A1-A1-A1-A1"), "Invalid sequence representation: A1-A1-A1-A1-A1-A1-A1");
        assertThrows(IllegalArgumentException.class, () -> Sequence.of("A1--A1"), "Empty area is not allowed in sequence: A1--A1");
        assertThrows(IllegalArgumentException.class, () -> Sequence.of("X"), "From has to be set, sequence: X");
        assertThrows(IllegalArgumentException.class, () -> Sequence.of("A1-X"), "To has to be set, sequence: A1-X");
        assertThrows(IllegalArgumentException.class, () -> Sequence.of("A1-A1"), "Next stop cannot be same as previous, sequence: A1-A1");
        assertThrows(IllegalArgumentException.class, () -> Sequence.of("A1-X-A2-A1"), "Stop cannot be set if previous is blocked, sequence: A1-X-A2-A1");
        assertThrows(IllegalArgumentException.class, () -> Sequence.of("A1-A2-A2-A1"), "Next stop cannot be same as previous, sequence: A1-A2-A2-A1");

        var sequence = Sequence.of("A1");
        assertEquals(1, sequence.getStops().size());
        assertEquals(A1, sequence.getStops().get(0));
        assertEquals(0, sequence.getBlockedStopsCount());

        sequence = Sequence.of("A1-A2");
        assertEquals(2, sequence.getStops().size());
        assertEquals(A1, sequence.getStops().get(0));
        assertEquals(A2, sequence.getStops().get(1));
        assertEquals(0, sequence.getBlockedStopsCount());

        sequence = Sequence.of("A1-X-X-A2");
        assertEquals(2, sequence.getStops().size());
        assertEquals(A1, sequence.getStops().get(0));
        assertEquals(A2, sequence.getStops().get(1));
        assertEquals(2, sequence.getBlockedStopsCount());

        sequence = Sequence.of("A1-A3-X-A2");
        assertEquals(3, sequence.getStops().size());
        assertEquals(A1, sequence.getStops().get(0));
        assertEquals(A3, sequence.getStops().get(1));
        assertEquals(A2, sequence.getStops().get(2));
        assertEquals(1, sequence.getBlockedStopsCount());
    }
}
