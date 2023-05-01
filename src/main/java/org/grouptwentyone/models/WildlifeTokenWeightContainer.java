package org.grouptwentyone.models;

import java.util.*;

public class WildlifeTokenWeightContainer {

    private HashMap<WildlifeToken.WildlifeTokenType, Double> wildlifeWeightValueHash = new HashMap<>();
    private ArrayList<WildlifeToken.WildlifeTokenType> activeWildlifeTokenTypes;

    public WildlifeTokenWeightContainer(ArrayList<WildlifeToken.WildlifeTokenType> activeWildlifeTokenTypes) {
        wildlifeWeightValueHash.put(WildlifeToken.WildlifeTokenType.BEAR, 0.0);
        wildlifeWeightValueHash.put(WildlifeToken.WildlifeTokenType.ELK, 0.0);
        wildlifeWeightValueHash.put(WildlifeToken.WildlifeTokenType.FOX, 0.0);
        wildlifeWeightValueHash.put(WildlifeToken.WildlifeTokenType.HAWK, 0.0);
        wildlifeWeightValueHash.put(WildlifeToken.WildlifeTokenType.SALMON, 0.0);

        // Marks a certain wildlife token type as active when a weight is set for it.
        // So if the weights are all 0s, a non-active tile is not returned.
        this.activeWildlifeTokenTypes = activeWildlifeTokenTypes;
    }

    public Map.Entry<WildlifeToken.WildlifeTokenType, Double> getLargestWildlifeWeightValue() {

        WildlifeToken.WildlifeTokenType firstActiveWildlifeTokenType = getActiveWildlifeTokenTypes().get(0);

        // Defaults to first type inside the active wildlife token type
        Map.Entry<WildlifeToken.WildlifeTokenType, Double> largestWeightValue =
                new AbstractMap.SimpleEntry<>(firstActiveWildlifeTokenType, wildlifeWeightValueHash.get(firstActiveWildlifeTokenType));


        for (Map.Entry<WildlifeToken.WildlifeTokenType, Double> weightValue: wildlifeWeightValueHash.entrySet()) {

            // Ignore reserve values that are not active
            boolean wildlifeTokenTypeNotActive = !(activeWildlifeTokenTypes.contains(weightValue.getKey()));
            if (wildlifeTokenTypeNotActive)
                continue;


            if (weightValue.getValue() > largestWeightValue.getValue()) {
                largestWeightValue = weightValue;
            }
        }

        return largestWeightValue;
    }

    public double getAverageWeightValue(ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypes) {
        double averageWeightValue = 0.0;

        for (WildlifeToken.WildlifeTokenType wildlifeTokenType: wildlifeTokenTypes) {
            averageWeightValue += wildlifeWeightValueHash.get(wildlifeTokenType);
        }

        averageWeightValue /= wildlifeTokenTypes.size();

        return averageWeightValue;
    }

    public void setWildlifeWeight(WildlifeToken.WildlifeTokenType wildlifeTokenType, double n) {
        wildlifeWeightValueHash.put(wildlifeTokenType, n);
    }

    public double getWeightOfSpecificAnimal(WildlifeToken.WildlifeTokenType wildlifeTokenType) {
        return wildlifeWeightValueHash.get(wildlifeTokenType);
    }



    private ArrayList<WildlifeToken.WildlifeTokenType> getActiveWildlifeTokenTypes() {
        return activeWildlifeTokenTypes;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Map.Entry<WildlifeToken.WildlifeTokenType, Double> weightValue: wildlifeWeightValueHash.entrySet()) {
            output.append(weightValue.getKey()).append(": ").append(weightValue.getValue()).append(", ");
        }
        return output.toString();
    }
}
