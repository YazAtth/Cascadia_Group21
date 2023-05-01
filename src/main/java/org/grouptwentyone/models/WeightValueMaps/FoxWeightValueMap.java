/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models.WeightValueMaps;

import org.grouptwentyone.controllers.WeightController;

import java.util.HashMap;

public class FoxWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> foxWeightTable = new HashMap<>();



    public FoxWeightValueMap() {
        super();

        foxWeightTable.put(0, 0.0 * WeightController.WeightConstants.foxMultiplier);
        foxWeightTable.put(1, 0.4 * WeightController.WeightConstants.foxMultiplier);
        foxWeightTable.put(2, 1.0 * WeightController.WeightConstants.foxMultiplier);
        foxWeightTable.put(3, 2.2 * WeightController.WeightConstants.foxMultiplier);
        foxWeightTable.put(4, 3.5 * WeightController.WeightConstants.foxMultiplier);
        foxWeightTable.put(5, 9.4 * WeightController.WeightConstants.foxMultiplier);
    }

    @Override
    public double getWeightValue(int numOfUniqueAdjacentWildlifeTokens) {

        if (!foxWeightTable.containsKey(numOfUniqueAdjacentWildlifeTokens)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", numOfUniqueAdjacentWildlifeTokens));
        }

        return foxWeightTable.get(numOfUniqueAdjacentWildlifeTokens);
    }

}
