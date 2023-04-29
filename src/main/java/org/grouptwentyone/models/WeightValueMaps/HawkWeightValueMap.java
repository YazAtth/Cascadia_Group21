package org.grouptwentyone.models.WeightValueMaps;

import java.util.HashMap;

public class HawkWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> hawkWeightTable = new HashMap<>();

    public HawkWeightValueMap() {
        super();

        hawkWeightTable.put(0, 0.0);
        hawkWeightTable.put(1, 1.0);
        hawkWeightTable.put(2, 2.0);
        hawkWeightTable.put(3, 3.0);
        hawkWeightTable.put(4, 4.0);
        hawkWeightTable.put(5, 5.3);
        hawkWeightTable.put(6, 6.3);
        hawkWeightTable.put(7, 7.3);
        hawkWeightTable.put(8, 8.3);
    }

    @Override
    public double getWeightValue(int validHawksOnPlayerBoard) {

        //error handling
        if (!hawkWeightTable.containsKey(validHawksOnPlayerBoard)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", validHawksOnPlayerBoard));
        }

        return hawkWeightTable.get(validHawksOnPlayerBoard);
    }
}
