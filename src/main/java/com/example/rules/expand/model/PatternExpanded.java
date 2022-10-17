package com.example.rules.expand.model;

import com.example.rules.model.Combination;
import com.example.rules.model.Pattern;
import lombok.Data;

import java.util.TreeSet;

@Data
public class PatternExpanded {
    private Pattern pattern;
    private TreeSet<CombinationExpanded> combinations;

    public PatternExpanded(Pattern pattern) {
        this.pattern = pattern;
        this.combinations = new TreeSet<>();
    }

    public void addCombination(Combination combination, CombinationState state) {
        combinations.add(CombinationExpanded.builder()
                .combination(combination)
                .state(state)
                .build());
    }
}
