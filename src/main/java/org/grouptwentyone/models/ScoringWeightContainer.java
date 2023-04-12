package org.grouptwentyone.models;

import java.util.HashMap;
import java.util.Map;

public class ScoringWeightContainer {

    HashMap<Integer, Double> foxMap = new HashMap<>() {{
        foxMap.put(1, 0.4);
        foxMap.put(2, 1.0);
        foxMap.put(3, 2.2);
        foxMap.put(4, 3.5);
        foxMap.put(5, 9.4);
    }};
}
