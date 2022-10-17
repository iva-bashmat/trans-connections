package com.example.rules.expand;

import com.example.rules.expand.model.PatternExpanded;
import com.example.rules.model.Combination;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.TreeSet;

@Data
@Builder
public class ExpandResult {
    private List<PatternExpanded> patterns;
    private TreeSet<Combination> remainingCombinations;
}
