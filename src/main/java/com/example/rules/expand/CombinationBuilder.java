package com.example.rules.expand;

import com.example.rules.alg.ExpansionAlgorithm;
import com.example.rules.expand.model.PatternExpanded;
import com.example.rules.model.Combination;
import com.example.rules.util.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.rules.expand.model.CombinationState.*;

public class CombinationBuilder<T> {
    private final ExpansionAlgorithm<T> alg;
    private final Set<Combination> combinationsDisabled;
    private final List<T> patterns;

    public CombinationBuilder(ExpansionAlgorithm<T> alg, String patternsFile, Function<String, T> mapper, String combinationsDisabledFile) throws URISyntaxException, IOException {
        this.alg = alg;
        this.combinationsDisabled = FileUtils.readClasspathFile(combinationsDisabledFile, Combination::of, Collectors.toSet());
        this.patterns = FileUtils.readClasspathFile(patternsFile, mapper, Collectors.toList());
    }

    public CombinationBuilder(ExpansionAlgorithm<T> alg, List<T> patterns, Set<Combination> combinationsDisabled) {
        this.alg = alg;
        this.patterns = patterns;
        this.combinationsDisabled = combinationsDisabled;
    }

    public ExpandResult<T> expand(Set<Combination> combinationsExpected) {
        var combinationsActual = new HashSet<Combination>();
        var patternsExpanded = patterns.stream().map(pattern -> {
            var patternExpanded = new PatternExpanded<>(pattern);
            var combinations = alg.buildCombinations(pattern);
            for (var combination : combinations) {
                if (combinationsExpected.contains(combination)) {
                    patternExpanded.addCombination(combination, OK);
                } else if (combinationsDisabled.contains(combination)) {
                    patternExpanded.addCombination(combination, DISABLED);
                } else {
                    patternExpanded.addCombination(combination, UNKNOWN);
                }
            }

            combinationsActual.addAll(combinations);
            return patternExpanded;
        }).collect(Collectors.toList());

        var combinationsRemaining = new TreeSet<>(combinationsExpected);
        combinationsRemaining.removeAll(combinationsActual);

        return new ExpandResult<>(patternsExpanded, combinationsRemaining);
    }
}
