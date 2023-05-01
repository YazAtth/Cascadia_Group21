package org.grouptwentyone.models.WeightValueMaps;

import org.grouptwentyone.controllers.WeightController;

import java.util.HashMap;

public class HawkWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> hawkWeightTable = new HashMap<>();

    public HawkWeightValueMap() {
        super();

        hawkWeightTable.put(0, 0.0 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(1, 1.0 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(2, 2.0 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(3, 3.0 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(4, 4.0 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(5, 5.3 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(6, 6.3 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(7, 7.3 * WeightController.WeightConstants.hawkMultiplier);
        hawkWeightTable.put(8, 8.3 * WeightController.WeightConstants.hawkMultiplier);
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
