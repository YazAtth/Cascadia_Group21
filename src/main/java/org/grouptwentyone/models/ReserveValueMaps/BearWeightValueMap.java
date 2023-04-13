package org.grouptwentyone.models.ReserveValueMaps;

import java.util.HashMap;

public class BearWeightValueMap extends AbstractWeightValueMap {

    //hashtable to associate weig
    HashMap<Integer, Double> botScoringMap = new HashMap<>() {{
        botScoringMap.put(-2, -2.0); // Placing bear will ruin pair.
        botScoringMap.put(1, 1.0);
        botScoringMap.put(2, 1.0);
        botScoringMap.put(3, 1.0);
        botScoringMap.put(4, 1.0);

    }};
    @Override
    double getWeightValue(int numOfPairs) {
        // TODO: Case for "If placing a bear will not result in a pair yet."

        if (!botScoringMap.containsKey(numOfPairs)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfPairs));
        }

        return botScoringMap.get(numOfPairs);
    }
}
