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

public class SalmonWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> salmonWeightTable = new HashMap<>();

    public SalmonWeightValueMap() {
        super();

        salmonWeightTable.put(0, -2.0 * WeightController.WeightConstants.salmonMultiplier);
        salmonWeightTable.put(1, 1.3 * WeightController.WeightConstants.salmonMultiplier);
        salmonWeightTable.put(2, 2.35 * WeightController.WeightConstants.salmonMultiplier);
        salmonWeightTable.put(3, 3.45 * WeightController.WeightConstants.salmonMultiplier);
        salmonWeightTable.put(4, 4.65 * WeightController.WeightConstants.salmonMultiplier);
        salmonWeightTable.put(5, 5.7 * WeightController.WeightConstants.salmonMultiplier);
        salmonWeightTable.put(6, 6.9 * WeightController.WeightConstants.salmonMultiplier);
        salmonWeightTable.put(7, 9.0 * WeightController.WeightConstants.salmonMultiplier);
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
