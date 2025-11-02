package com.testweaver.strategy;

import java.util.List;
import java.util.Map;

public interface PairwiseStrategy {
    String name();
    List<Map<String, String>> generate(Map<String, List<String>> params, int strength);
}
