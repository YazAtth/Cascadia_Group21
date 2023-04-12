package org.grouptwentyone.models.ReserveValueMaps;

import java.util.HashMap;

public class FoxReserveValueMap extends AbstractReserveValueMap {

    HashMap<Integer, Double> botScoringMap = new HashMap<>() {{
            botScoringMap.put(1, 0.4);
            botScoringMap.put(2, 1.0);
            botScoringMap.put(3, 2.2);
            botScoringMap.put(4, 3.5);
            botScoringMap.put(5, 9.4);
    }};

    @Override
    protected double getReserveValue(int numOfUniqueAdjacentWildlifeTokens) {

        if (!botScoringMap.containsKey(numOfUniqueAdjacentWildlifeTokens)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfUniqueAdjacentWildlifeTokens));
        }

        return botScoringMap.get(numOfUniqueAdjacentWildlifeTokens);

    }

}
