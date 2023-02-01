package org.grouptwentyone.models;

import java.util.ArrayList;
import java.util.HashSet;

public class HabitatTile {
    enum HabitatTileType {MOUNTAINS, FORESTS, PRAIRIES,WETLANDS, RIVERS}
    ArrayList<HabitatTileType> habitatTileTypeList;
    ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList;
    WildlifeToken wildlifeToken;
    boolean keystone;

    public HabitatTile(ArrayList<HabitatTileType> habitatTileTypeList, int numWildlifeTypes) {
        this.habitatTileTypeList = habitatTileTypeList;
        if (numWildlifeTypes < 1 || numWildlifeTypes > 3)
            throw new IllegalArgumentException("Invalid number of wildlife types inputted");
        this.wildlifeTokenTypeList = wildlifeTokenTypeListGenerator(numWildlifeTypes);
        this.wildlifeToken = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);
        this.keystone = (habitatTileTypeList.size() == 1);
    }

    private static ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeListGenerator(int numWildlifeTypes) {
        HashSet<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeSet = new HashSet<>(numWildlifeTypes);

        for (int i = 0; i < numWildlifeTypes; i++) {
            switch (RandomNumberGenerator.getRandomNumberInRange(1, 5)) {
                case 1 -> wildlifeTokenTypeSet.add(WildlifeToken.WildlifeTokenType.BEAR);
                case 2 -> wildlifeTokenTypeSet.add(WildlifeToken.WildlifeTokenType.ELK);
                case 3 -> wildlifeTokenTypeSet.add(WildlifeToken.WildlifeTokenType.SALMON);
                case 4 -> wildlifeTokenTypeSet.add(WildlifeToken.WildlifeTokenType.HAWK);
                case 5 -> wildlifeTokenTypeSet.add(WildlifeToken.WildlifeTokenType.FOX);
                default -> throw new IllegalArgumentException("num generator failed");
            }
            //reduces i by 1 if a duplicate WildlifeTokenType was about to be added
            if (i != wildlifeTokenTypeSet.size()) i--;
        }
        return new ArrayList<>(wildlifeTokenTypeSet);
    }

    public ArrayList<HabitatTileType> getHabitatTileTypeList() {
        return habitatTileTypeList;
    }

    public ArrayList<WildlifeToken.WildlifeTokenType> getWildlifeTokenTypeList() {
        return wildlifeTokenTypeList;
    }

    public WildlifeToken getWildlifeToken() {
        return wildlifeToken;
    }

    public boolean isKeystone() {
        return keystone;
    }
}
