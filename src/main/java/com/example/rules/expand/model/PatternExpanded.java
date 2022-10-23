package com.example.rules.expand.model;

import com.example.rules.model.Combination;
import lombok.Data;

import java.util.TreeSet;

@Data
public class PatternExpanded<T> {
    private T pattern;
    private TreeSet<CombinationExpanded> combinations;

    public PatternExpanded(T pattern) {
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
