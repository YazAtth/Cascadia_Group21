package org.grouptwentyone.models.ReserveValueMaps;

import java.util.HashMap;

public class HawkWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> botScoringMap = new HashMap<>() {{
        botScoringMap.put(-1, -1.0); // Placing a hawk beside two other hawks.
        botScoringMap.put(0, 0.0); // Placing a hawk adjacently to another hawk OR if there are 8 hawk (that all yield hawks) all hawk reserves become 0.
        botScoringMap.put(1, 1.0);
        botScoringMap.put(2, 2.0);
        botScoringMap.put(3, 3.0);
        botScoringMap.put(4, 4.0);
        botScoringMap.put(5, 5.3);
        botScoringMap.put(6, 6.3);
        botScoringMap.put(7, 7.3);
        botScoringMap.put(8, 8.3);
    }};

    @Override
    public double getWeightValue(int validHawksOnPlayerBoard) {
        if (!botScoringMap.containsKey(validHawksOnPlayerBoard)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", validHawksOnPlayerBoard));
        }

        return botScoringMap.get(validHawksOnPlayerBoard);
    }


}
