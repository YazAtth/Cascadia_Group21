package org.grouptwentyone.models.WeightValueMaps;

import java.util.HashMap;

public class FoxWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> foxWeightTable = new HashMap<>();



    public FoxWeightValueMap() {
        super();

        foxWeightTable.put(0, 0.0);
        foxWeightTable.put(1, 0.4);
        foxWeightTable.put(2, 1.0);
        foxWeightTable.put(3, 2.2);
        foxWeightTable.put(4, 3.5);
        foxWeightTable.put(5, 9.4);
    }

    @Override
    public double getWeightValue(int numOfUniqueAdjacentWildlifeTokens) {

        if (!foxWeightTable.containsKey(numOfUniqueAdjacentWildlifeTokens)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfUniqueAdjacentWildlifeTokens));
        }

        return foxWeightTable.get(numOfUniqueAdjacentWildlifeTokens);
    }

}
