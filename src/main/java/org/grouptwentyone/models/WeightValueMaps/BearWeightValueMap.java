package org.grouptwentyone.models.WeightValueMaps;

import org.grouptwentyone.controllers.WeightController;

import java.util.HashMap;

public class BearWeightValueMap extends AbstractWeightValueMap {

    //hashtable to associate weight with the number of pair of bear in the playerBoard
    HashMap<Integer, Double> bearWeightTable = new HashMap<>();

    public BearWeightValueMap() {
        super();

        bearWeightTable.put(0, 0.35 * WeightController.WeightConstants.bearMultiplier); //if placing a bear will result in 0 pairs
        bearWeightTable.put(1, 1.0 * WeightController.WeightConstants.bearMultiplier); //if placing a bear will result in 1 valid pair
        bearWeightTable.put(2, 4.1 * WeightController.WeightConstants.bearMultiplier); //if placing a bear will result in 2 valid pair
        bearWeightTable.put(3, 6.8 * WeightController.WeightConstants.bearMultiplier); //if placing a bear will result in 3 valid pair
        bearWeightTable.put(4, 8.9 * WeightController.WeightConstants.bearMultiplier); //if placing a bear will result in 4 valid pair
    }

    @Override
    public double getWeightValue(int numOfPairs) {

        //having more than 4 pairs yields no extra points, so we return a weight of 0
        if (numOfPairs > 4) {
            return 0;
        }

        //error handling
        if (!bearWeightTable.containsKey(numOfPairs)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfPairs));
        }

        //get weight based on the number of pairs
        double bearWeight = bearWeightTable.get(numOfPairs);


        return bearWeight;
    }

    public double ruinsPairWeight() {
        return WeightController.WeightConstants.ruinsBearPairWeight;
    }


}
