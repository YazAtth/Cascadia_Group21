package org.grouptwentyone.models.ReserveValueMaps;

import java.util.HashMap;

public class SalmonReserveValueMap extends AbstractReserveValueMap {

    HashMap<Integer, Double> botScoringMap = new HashMap<>() {{
        botScoringMap.put(-2, -2.0); // Placing a salmon that will ruin two or more runs
        botScoringMap.put(-1, -1.0); // Placing a salmon will run one run
        botScoringMap.put(1, 1.3);
        botScoringMap.put(2, 2.35);
        botScoringMap.put(3, 3.45);
        botScoringMap.put(4, 4.65);
        botScoringMap.put(5, 5.7);
        botScoringMap.put(6, 6.9);
        botScoringMap.put(7, 9.0);
//        salmonScoreMap.put(9, 1.3);
    }};


    @Override
    double getReserveValue(int numOfSalmonInRun) {

        // If there are more than 7 salmon in a run
        //TODO: Not sure of this calculation
        if (numOfSalmonInRun > 7) {
            int extraSalmon = numOfSalmonInRun - 7;
            return 9+extraSalmon;
        }

        if (!botScoringMap.containsKey(numOfSalmonInRun)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfSalmonInRun));
        }

        return botScoringMap.get(numOfSalmonInRun);
    }
}
