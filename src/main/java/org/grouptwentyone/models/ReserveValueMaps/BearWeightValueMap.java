package org.grouptwentyone.models.ReserveValueMaps;

import java.util.HashMap;

public class BearWeightValueMap extends AbstractWeightValueMap {

    //hashtable to associate weight with the number of pair of bear in the playerBoard
    HashMap<Integer, Double> bearWeightMap = new HashMap<>() {{
        bearWeightMap.put(0, 0.35); //if placing a bear will result in 0 pairs
        bearWeightMap.put(1, 1.0); //if placing a bear will result in 1 valid pair
        bearWeightMap.put(2, 4.1); //if placing a bear will result in 2 valid pair
        bearWeightMap.put(3, 6.8); //if placing a bear will result in 3 valid pair
        bearWeightMap.put(4, 8.9); //if placing a bear will result in 4 valid pair
    }};
    @Override
    double getWeightValue(int numOfPairs) {

        if (ruinsPair()) {
            return -2.0;
        }

        //having more than 4 pairs yields no extra points, so we return a weight of 0
        if (numOfPairs > 4) {
            return 0;
        }

        //error handling
        if (!bearWeightMap.containsKey(numOfPairs)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfPairs));
        }

        //get weight based on the number of pairs
        double bearWeight = bearWeightMap.get(numOfPairs);

        if (areAllBearsInPairs()) {
            bearWeight -= 0.5;
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
}
