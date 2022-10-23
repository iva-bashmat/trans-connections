package com.example.rules.alg;

import com.example.rules.model.Combination;

import java.util.TreeSet;

public abstract class ExpansionAlgorithm<T> {
    public abstract TreeSet<Combination> buildCombinations(T t);
}
