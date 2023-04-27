package org.grouptwentyone.models;

import java.util.*;

public class ReserveValueContainer {

    private HashMap<WildlifeToken.WildlifeTokenType, Double> wildlifeReserveValueHash = new HashMap<>();
    private ArrayList<WildlifeToken.WildlifeTokenType> activeWildlifeTokenTypes = new ArrayList<>();

    public ReserveValueContainer() {
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.BEAR, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.ELK, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.FOX, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.HAWK, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.SALMON, 0.0);
    }

    public Map.Entry<WildlifeToken.WildlifeTokenType, Double> getLargestWildlifeReserveValue() {

        WildlifeToken.WildlifeTokenType firstActiveWildlifeTokenType = getActiveWildlifeTokenTypes().get(0);

        // Defaults to first type inside the active wildlife token type
        Map.Entry<WildlifeToken.WildlifeTokenType, Double> largestReserveValue =
                new AbstractMap.SimpleEntry<>(firstActiveWildlifeTokenType, wildlifeReserveValueHash.get(firstActiveWildlifeTokenType));


        for (Map.Entry<WildlifeToken.WildlifeTokenType, Double> reserveValue: wildlifeReserveValueHash.entrySet()) {

            // Ignore reserve values that are not active
            boolean wildlifeTokenTypeNotActive = !(activeWildlifeTokenTypes.contains(reserveValue.getKey()));
            if (wildlifeTokenTypeNotActive)
                continue;


            if (reserveValue.getValue() > largestReserveValue.getValue()) {
                largestReserveValue = reserveValue;
            }
        }

        return largestReserveValue;
    }

    public void setWildlifeReserveWeight(WildlifeToken.WildlifeTokenType wildlifeTokenType, double n) {
        // Marks a certain wildlife token type as active when a weight is set for it.
        // So if the weights are all 0s, a non-active tile is not returned.
        activeWildlifeTokenTypes.add(wildlifeTokenType);
        wildlifeReserveValueHash.put(wildlifeTokenType, n);
    }

    public void incrementWildlifeReserveWeight(WildlifeToken.WildlifeTokenType wildlifeTokenType, double iterationAmount) {
        wildlifeReserveValueHash.put(wildlifeTokenType, wildlifeReserveValueHash.get(wildlifeTokenType) + iterationAmount);
    }


    private ArrayList<WildlifeToken.WildlifeTokenType> getActiveWildlifeTokenTypes() {
        return activeWildlifeTokenTypes;
    }
}
