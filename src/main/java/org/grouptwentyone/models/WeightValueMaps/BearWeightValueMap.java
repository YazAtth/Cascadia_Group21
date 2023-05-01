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

//        if (ruinsPair()) {
//            return -2.0;
//        }

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

        if (areAllBearsInPairs()) {
            bearWeight -= WeightController.WeightConstants.allBearsInPairsReduction;
        }

        return bearWeight;
    }

    //helper function to check if placing a bear on a tile will ruin an existing pair of bears
    boolean ruinsPair() {
        //TODO: return true if placing a bear on that tile will ruin an existing pair of bears
        return true; //temporary
    }

    boolean areAllBearsInPairs() {
        //TODO
        /* if all existing bears on the board are already in pairs, placing a bear in a new position
         * will not yield any extra points. So if this function returns true, we subtract 0.5 to the
         * bearWeight we got from the hashtable
         */
        return true; //temporary
    }

    public double ruinsPairWeight() {
        return WeightController.WeightConstants.ruinsBearPairWeight;
    }


}
