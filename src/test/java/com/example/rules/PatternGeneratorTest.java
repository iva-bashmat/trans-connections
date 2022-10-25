package com.example.rules;

import com.example.rules.expand.model.CombinationState;
import com.example.rules.expand.model.PatternExpanded;
import com.example.rules.model.Combination;
import com.example.rules.model.Pattern;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.example.rules.expand.model.CombinationState.*;
import static com.example.rules.model.Area.A1;
import static com.example.rules.model.Area.A3;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PatternGeneratorTest {

    @Test
    public void testCreatePattern() {
        var pattern = new PatternGenerator().toPattern(Combination.of("A1-A1-A1"));
        assertEquals(A1, pattern.getFrom());
        assertEquals(A1, pattern.getTo());
        assertEquals(0, pattern.getStops().size());

        pattern = new PatternGenerator().toPattern(Combination.of("A1-A1-A1-A3"));
        assertEquals(A1, pattern.getFrom());
        assertEquals(A3, pattern.getTo());
        assertEquals(0, pattern.getStops().size());

        pattern = new PatternGenerator().toPattern(Combination.of("A1-A1-A1-A2-A1"));
        assertEquals(A1, pattern.getFrom());
        assertEquals(A1, pattern.getTo());
        assertEquals(3, pattern.getStops().size());

    }

    @Test
    public void testGenerate() {
        var combinationsExpected = Set.of(Combination.of("A1-A1-A3-A1-A1"));
        var expandResult = new PatternGenerator().generate(combinationsExpected);

        assertEquals(1, expandResult.getPatterns().size());
        assertEquals(Pattern.of("A1-A1-A3--X-A1"), expandResult.getPatterns().get(0).getPattern());

        var pattern = expandResult.getPatterns().get(0);
        assertEquals(3, pattern.getCombinations().size());
        assertEquals(1L, getCombinationsByState(pattern, OK));
        assertEquals(2L, getCombinationsByState(pattern, DISABLED));
        assertEquals(0L, getCombinationsByState(pattern, UNKNOWN));
    }

    private Long getCombinationsByState(PatternExpanded<Pattern> pattern, CombinationState state) {
        return pattern.getCombinations().stream().filter(combinationExpanded -> state.equals(combinationExpanded.getState())).count();
    }
}
