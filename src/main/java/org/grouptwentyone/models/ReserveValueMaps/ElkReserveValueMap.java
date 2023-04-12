package org.grouptwentyone.models.ReserveValueMaps;

import java.util.HashMap;

public class ElkReserveValueMap extends AbstractReserveValueMap {

    HashMap<Integer, Double> botScoringMap = new HashMap<>() {{
        botScoringMap.put(1, 0.9);
        botScoringMap.put(2, 0.9);
        botScoringMap.put(3, 0.9);
        botScoringMap.put(4, 0.9);

    }};


    @Override
    double getReserveValue(int numOfElkInLine) {

        //TODO: Case for when "Placing an elk that intersects another line of elk"

        if (!botScoringMap.containsKey(numOfElkInLine)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfElkInLine));
        }

        return botScoringMap.get(numOfElkInLine);
    }
}
