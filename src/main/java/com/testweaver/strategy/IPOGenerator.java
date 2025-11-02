package com.testweaver.strategy;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IPOGenerator implements PairwiseStrategy{
    @Override
    public String name() { return "IPO";}

    @Override
    public List<Map<String, String>> generate(Map<String, List<String>> params, int strength) {
        // to do 실제 IPO 알고리즘 구현
        return List.of();
    }
}
