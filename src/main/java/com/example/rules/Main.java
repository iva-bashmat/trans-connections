package com.example.rules;

import com.example.rules.alg.ExpansionAlgorithm;
import com.example.rules.alg.impl.PatternAny;
import com.example.rules.alg.impl.PatternLeftRight;
import com.example.rules.alg.impl.SequenceAlgorithm;
import com.example.rules.expand.CombinationBuilder;
import com.example.rules.expand.ExpandResult;
import com.example.rules.model.Combination;
import com.example.rules.model.Pattern;
import com.example.rules.model.Sequence;
import com.example.rules.util.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class Main {
    private static final String RESULT_FOLDER_BASE = "/Users/ihoroshko/work/patterns/";

    public static void main(String... args) throws URISyntaxException, IOException {
        var combinationsExpected = FileUtils.readClasspathFile("/static/routes.csv", Combination::of, toSet());

        Main.expand(combinationsExpected, new PatternLeftRight(), "left-right", Pattern::of);
        Main.expand(combinationsExpected, new PatternAny(), "any", Pattern::of);
        Main.expand(combinationsExpected, new SequenceAlgorithm(), "sequence", Sequence::of);

        var patternsLeftRightGenerated = new PatternGenerator().generate(combinationsExpected);
        Main.writeResults(patternsLeftRightGenerated, Main.RESULT_FOLDER_BASE + "/left-right-autogenerate");
    }

    private static <T> void expand(Set<Combination> combinationsExpected, ExpansionAlgorithm<T> alg, String folderName, Function<String, T> patternMapper) throws URISyntaxException, IOException {
        var combinationBuilder = new CombinationBuilder<>(alg, "/static/" + folderName + "/patterns.csv", patternMapper, "/static/" + folderName + "/disable.csv");
        var expandResult = combinationBuilder.expand(combinationsExpected);
        Main.writeResults(expandResult, Main.RESULT_FOLDER_BASE + folderName);
    }

    private static <T> void writeResults(ExpandResult<T> expandResult, String folder) throws IOException {

        var patternsContent = new StringBuilder();
        for (var patternResult : expandResult.getPatterns()) {
            patternsContent.append("-------------------").append(System.lineSeparator());
            patternsContent.append(patternResult.getPattern()).append(System.lineSeparator());
            patternResult.getCombinations().forEach(combination -> patternsContent.append(combination).append(System.lineSeparator()));
        }
        FileUtils.writeFile(patternsContent.toString(), folder, "patterns.txt");

        var contentCombinations = expandResult.getRemainingCombinations().stream().map(Combination::toString).collect(Collectors.joining(System.lineSeparator()));
        FileUtils.writeFile(contentCombinations, folder, "combinations-remaining.txt");
    }
}

