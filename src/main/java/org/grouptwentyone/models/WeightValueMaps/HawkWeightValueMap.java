package org.grouptwentyone.models.WeightValueMaps;

import java.util.HashMap;


/* Hawks are rewarded for being in isolation, the more hawks in isolation the more points (but max is 8)
   FOR HABITAT TILES
   We want to place tiles with hawk icons away from existing tiles with hawk icon to reduce having adjacent
   hawks as much as possible.

   FOR TOKENS
   We want to place tokens that will result in the hawk token being isolated from other existing hawk token

   SPECIAL CASES
   we check if we are placing a token next to another hawk token or placing a habitat tile next to an existing habitat
   tile with a hawk icon. This is allowing the possibility of having adjacent hawks, which is punished

 */
public class HawkWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> hawkWeightTable = new HashMap<>();

    public HawkWeightValueMap() {
        super();

        hawkWeightTable.put(0, 0.0);
        hawkWeightTable.put(1, 1.0);
        hawkWeightTable.put(2, 2.0);
        hawkWeightTable.put(3, 3.0);
        hawkWeightTable.put(4, 4.0);
        hawkWeightTable.put(5, 5.3);
        hawkWeightTable.put(6, 6.3);
        hawkWeightTable.put(7, 7.3);
        hawkWeightTable.put(8, 8.3);
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
