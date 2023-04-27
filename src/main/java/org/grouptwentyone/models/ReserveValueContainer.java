package org.grouptwentyone.models;

import java.util.*;

public class ReserveValueContainer {

    private HashMap<WildlifeToken.WildlifeTokenType, Double> wildlifeReserveValueHash = new HashMap<>();

    public ReserveValueContainer() {
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.BEAR, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.ELK, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.FOX, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.HAWK, 0.0);
        wildlifeReserveValueHash.put(WildlifeToken.WildlifeTokenType.SALMON, 0.0);
    }

    public Map.Entry<WildlifeToken.WildlifeTokenType, Double> getLargestWildlifeReserveValue() {

        Map.Entry<WildlifeToken.WildlifeTokenType, Double> largestReserveValue =
                new AbstractMap.SimpleEntry<>(WildlifeToken.WildlifeTokenType.BEAR, wildlifeReserveValueHash.get(WildlifeToken.WildlifeTokenType.BEAR));

        for (Map.Entry<WildlifeToken.WildlifeTokenType, Double> reserveValue: wildlifeReserveValueHash.entrySet()) {
            if (reserveValue.getValue() > largestReserveValue.getValue()) {
                largestReserveValue = reserveValue;
            }
        }

        return largestReserveValue;
    }

    public void setWildlifeReserveWeight(WildlifeToken.WildlifeTokenType wildlifeTokenType, double n) {
        wildlifeReserveValueHash.put(wildlifeTokenType, n);
    }

    public void incrementWildlifeReserveWeight(WildlifeToken.WildlifeTokenType wildlifeTokenType, double iterationAmount) {
        wildlifeReserveValueHash.put(wildlifeTokenType, wildlifeReserveValueHash.get(wildlifeTokenType) + iterationAmount);
    }

}
