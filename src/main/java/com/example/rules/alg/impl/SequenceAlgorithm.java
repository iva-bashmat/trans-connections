package com.example.rules.alg.impl;

import com.example.rules.alg.ExpansionAlgorithm;
import com.example.rules.model.AbstractCombination;
import com.example.rules.model.Area;
import com.example.rules.model.Combination;
import com.example.rules.model.Sequence;

import java.util.*;

public class SequenceAlgorithm extends ExpansionAlgorithm<Sequence> {
    private final Map<Integer, List<List<Integer>>> combinationTemplates;

    public SequenceAlgorithm() {
        combinationTemplates = new HashMap<>();
        populateCombinationTemplates();
    }

    @Override
    public TreeSet<Combination> buildCombinations(Sequence sequence) {
        var minStops = sequence.getStops().size();
        var maxStops = AbstractCombination.NUMBER_OF_ALL_STOPS - sequence.getBlockedStopsCount();
        var templates = combinationTemplates.get(minStops);
        var combinations = new TreeSet<Combination>();
        for (var template : templates) {
            if (template.stream().mapToInt(Integer::intValue).sum() <= maxStops) {
                combinations.add(createCombination(sequence, template));
            }
        }
        return combinations;
    }

    private Combination createCombination(Sequence sequence, List<Integer> template) {
        var combinationStops = new ArrayList<Area>();
        for (int i = 0; i < sequence.getStops().size(); i++) {
            combinationStops.addAll(Collections.nCopies(template.get(i), sequence.getStops().get(i)));
        }
        return new Combination(combinationStops);
    }

    public void populateCombinationTemplates() {
        combinationTemplates.put(2, sequence2Templates());
        combinationTemplates.put(3, sequence3Templates());
        combinationTemplates.put(4, sequence4Templates());
        combinationTemplates.put(5, sequence5Templates());
        combinationTemplates.put(6, sequence6Templates());
    }

    private List<List<Integer>> sequence2Templates() {
        var templates = new ArrayList<List<Integer>>();
        templates.add(List.of(1, 1));
        templates.add(List.of(2, 1));
        templates.add(List.of(1, 2));
        templates.add(List.of(3, 1));
        templates.add(List.of(2, 2));
        templates.add(List.of(1, 3));
        templates.add(List.of(4, 1));
        templates.add(List.of(3, 2));
        templates.add(List.of(2, 3));
        templates.add(List.of(1, 4));
        templates.add(List.of(5, 1));
        templates.add(List.of(4, 2));
        templates.add(List.of(3, 3));
        templates.add(List.of(2, 4));
        templates.add(List.of(1, 5));
        return templates;
    }

    private List<List<Integer>> sequence3Templates() {
        var templates = new ArrayList<List<Integer>>();
        templates.add(List.of(1, 1, 1));
        templates.add(List.of(2, 1, 1));
        templates.add(List.of(1, 2, 1));
        templates.add(List.of(1, 1, 2));
        templates.add(List.of(3, 1, 1));
        templates.add(List.of(2, 2, 1));
        templates.add(List.of(2, 1, 2));
        templates.add(List.of(1, 3, 1));
        templates.add(List.of(1, 2, 2));
        templates.add(List.of(1, 1, 3));
        templates.add(List.of(4, 1, 1));
        templates.add(List.of(3, 2, 1));
        templates.add(List.of(3, 1, 2));
        templates.add(List.of(2, 3, 1));
        templates.add(List.of(2, 2, 2));
        templates.add(List.of(2, 1, 3));
        templates.add(List.of(1, 4, 1));
        templates.add(List.of(1, 3, 2));
        templates.add(List.of(1, 2, 3));
        templates.add(List.of(1, 1, 4));
        return templates;
    }

    private List<List<Integer>> sequence4Templates() {
        var templates = new ArrayList<List<Integer>>();
        templates.add(List.of(1, 1, 1, 1));
        templates.add(List.of(2, 1, 1, 1));
        templates.add(List.of(1, 2, 1, 1));
        templates.add(List.of(1, 1, 2, 1));
        templates.add(List.of(1, 1, 1, 2));
        templates.add(List.of(3, 1, 1, 1));
        templates.add(List.of(2, 2, 1, 1));
        templates.add(List.of(2, 1, 2, 1));
        templates.add(List.of(2, 1, 1, 2));
        templates.add(List.of(1, 3, 1, 1));
        templates.add(List.of(1, 2, 2, 1));
        templates.add(List.of(1, 2, 1, 2));
        templates.add(List.of(1, 1, 3, 1));
        templates.add(List.of(1, 1, 2, 2));
        templates.add(List.of(1, 1, 1, 3));
        return templates;
    }

    private List<List<Integer>> sequence5Templates() {
        var templates = new ArrayList<List<Integer>>();
        templates.add(List.of(1, 1, 1, 1, 1));
        templates.add(List.of(2, 1, 1, 1, 1));
        templates.add(List.of(1, 2, 1, 1, 1));
        templates.add(List.of(1, 1, 2, 1, 1));
        templates.add(List.of(1, 1, 1, 2, 1));
        templates.add(List.of(1, 1, 1, 1, 2));
        return templates;
    }

    private List<List<Integer>> sequence6Templates() {
        var templates = new ArrayList<List<Integer>>();
        templates.add(List.of(1, 1, 1, 1, 1, 1));
        return templates;
    }
}