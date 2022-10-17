package com.example.rules.expand.model;

import com.example.rules.model.Combination;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CombinationExpanded implements Comparable<CombinationExpanded> {
    private final Combination combination;
    private final CombinationState state;

    @Override
    public int compareTo(CombinationExpanded o) {
        return this.combination.compareTo(o.combination);
    }

    @Override
    public String toString() {
        return state.getCode() + ":" + combination;
    }
}
