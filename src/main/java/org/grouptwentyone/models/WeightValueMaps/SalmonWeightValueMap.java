package org.grouptwentyone.models.WeightValueMaps;

import java.util.HashMap;

public class SalmonWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> salmonWeightTable = new HashMap<>();

    public SalmonWeightValueMap() {
        super();

        salmonWeightTable.put(0, -2.0);
        salmonWeightTable.put(1, 1.3);
        salmonWeightTable.put(2, 2.35);
        salmonWeightTable.put(3, 3.45);
        salmonWeightTable.put(4, 4.65);
        salmonWeightTable.put(5, 5.7);
        salmonWeightTable.put(6, 6.9);
        salmonWeightTable.put(7, 9.0);
    }


    @Override
    public  double getWeightValue(int numOfSalmonInRun) {

        // If there are more than 7 salmon in a run
        if (numOfSalmonInRun > 7) {
            int extraSalmon = numOfSalmonInRun - 7;

            return 9.0 + salmonWeightTable.get(extraSalmon);
        }


        if (!salmonWeightTable.containsKey(numOfSalmonInRun)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfSalmonInRun));
        }

        return salmonWeightTable.get(numOfSalmonInRun);
    }
}
