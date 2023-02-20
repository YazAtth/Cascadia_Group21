package org.grouptwentyone.models;

import java.util.ArrayList;
import java.util.HashSet;

public class HabitatTile {
    public enum HabitatTileType {MOUNTAINS, FORESTS, PRAIRIES, WETLANDS, RIVERS}
    private ArrayList<HabitatTileType> habitatTileTypeList;
    private ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList;
    private WildlifeToken wildlifeToken;
    private boolean keystone;
    private boolean isNull;

    //keystone tile constructor
    public HabitatTile(ArrayList<HabitatTileType> habitatTileTypeList, ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList) {
        this.habitatTileTypeList = habitatTileTypeList;
        this.wildlifeTokenTypeList = wildlifeTokenTypeList;
        this.wildlifeToken = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);
        this.keystone = (habitatTileTypeList.size() == 1);
        this.isNull = false;
    }

    //constructor for other tile types
    public HabitatTile(int numTypes) {
        if (numTypes < 2 || numTypes > 3)
            throw new IllegalArgumentException("invalid number of types inputted");
        this.habitatTileTypeList = habitatTileTypeListGenerator(2);
        this.wildlifeTokenTypeList = wildlifeTokenTypeListGenerator(numTypes);
        this.wildlifeToken = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);
        this.keystone = (habitatTileTypeList.size() == 1);
        this.isNull = false;
    }

    public HabitatTile() {
        this.habitatTileTypeList = new ArrayList<>();
        this.wildlifeTokenTypeList = new ArrayList<>();
        this.wildlifeToken = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);
        this.keystone = false;
        this.isNull = true;
    }

    private static ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeListGenerator(int numWildlifeTypes) {
        HashSet<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeSet = new HashSet<>();

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
            if (i+1 != wildlifeTokenTypeSet.size()) i--;
        }
        return new ArrayList<>(wildlifeTokenTypeSet);
    }

    public static ArrayList<HabitatTileType> habitatTileTypeListGenerator(int numHabitatTypes) {
        HashSet<HabitatTileType> habitatTileTypeSet = new HashSet<>();

        for (int i = 0; i < numHabitatTypes; i++) {
            int test = RandomNumberGenerator.getRandomNumberInRange(1, 5);
            switch (test) {
                case 1 -> habitatTileTypeSet.add(HabitatTileType.MOUNTAINS);
                case 2 -> habitatTileTypeSet.add(HabitatTileType.FORESTS);
                case 3 -> habitatTileTypeSet.add(HabitatTileType.PRAIRIES);
                case 4 -> habitatTileTypeSet.add(HabitatTileType.WETLANDS);
                case 5 -> habitatTileTypeSet.add(HabitatTileType.RIVERS);
                default -> throw new IllegalArgumentException("num generator failed");
            }
            //reduces i by 1 if a duplicate HabitatTileType was about to be added
            if (i+1 != habitatTileTypeSet.size()) i--;
        }
        return new ArrayList<>(habitatTileTypeSet);
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

    public void setWildlifeToken(WildlifeToken wildlifeToken) {
        this.wildlifeToken = wildlifeToken;
    }

    public boolean isNull() {
        return isNull;
    }


    @Override
    public String toString() {
        return "HabitatTile\n" +
                "Habitat Type(s): " + habitatTileTypeList.toString() +
                ", Wildlife Token Types: " + wildlifeTokenTypeList.toString() +
                ", wildlife Token: " + wildlifeToken.toString();
    }
}
