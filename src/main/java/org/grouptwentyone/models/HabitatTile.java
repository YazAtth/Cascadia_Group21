/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

import java.util.ArrayList;
import java.util.HashSet;

public class HabitatTile {
    public enum HabitatTileType {MOUNTAINS, FORESTS, PRAIRIES, WETLANDS, RIVERS}
    private final ArrayList<HabitatTileType> habitatTileTypeList;
    private final ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList;
    private WildlifeToken wildlifeToken;
    private final boolean keystone;
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
        this.keystone = (this.habitatTileTypeList.size() == 1);
        this.isNull = false;
    }

    //empty habitat tile type
    public HabitatTile() {
        this.habitatTileTypeList = new ArrayList<>();
        this.wildlifeTokenTypeList = new ArrayList<>();
        this.wildlifeToken = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);
        this.keystone = false;
        this.isNull = true;
    }

    //debug constructor used in testing
    public HabitatTile(boolean debug) {
        this.habitatTileTypeList = habitatTileTypeListGenerator(2);
        this.wildlifeTokenTypeList = wildlifeTokenTypeListGenerator(5);
        this.wildlifeToken = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);
        this.keystone = (this.habitatTileTypeList.size() == 1);
        this.isNull = false;
    }

    // Creates a new habitat tile by value based on arguments .
    public HabitatTile(ArrayList<HabitatTileType> newHabitatTileType, ArrayList<WildlifeToken.WildlifeTokenType> newWildlifeTokenTypeList,
                       WildlifeToken.WildlifeTokenType newWildlifeTokenType, boolean isKeystone, boolean isNull) {
        this.habitatTileTypeList = new ArrayList<>(newHabitatTileType);
        this.wildlifeTokenTypeList = new ArrayList<>(newWildlifeTokenTypeList);
        this.wildlifeToken = new WildlifeToken(newWildlifeTokenType);
        this.keystone = isKeystone;
        this.isNull = isNull;
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
        return this.habitatTileTypeList;
    }

    public ArrayList<WildlifeToken.WildlifeTokenType> getWildlifeTokenTypeList() {
        return this.wildlifeTokenTypeList;
    }

    public WildlifeToken getWildlifeToken() {
        return this.wildlifeToken;
    }

    public boolean isKeystone() {
        return this.keystone;
    }

    public void setWildlifeToken(WildlifeToken wildlifeToken) {
        this.wildlifeToken = wildlifeToken;
    }

    public boolean isNull() {
        return this.isNull;
    }


    @Override
    public String toString() {
        return String.format("HabitatTile %s", this.getHabitatTileTypeList());
    }

    public String toString(boolean verbose) {

        if (verbose) {
            return "HabitatTile\n" +
                    "\tHabitat Type(s): " + this.habitatTileTypeList.toString() +
                    ",\n\t\tWildlife Token Types: " + this.wildlifeTokenTypeList.toString() +
                    ",\n\t\tWildlife Token: " + this.wildlifeToken.toString() +
                    ",\n\t\tisKeyStone: " + this.isKeystone() +
                    ",\n\t\tisNull: " + this.isNull();
        } else {
            return this.toString();
        }
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof HabitatTile other)) return false;
//        System.out.printf("Attribute val: %s (this=%s, other=%s)\n", this.getWildlifeToken().equals(other.getWildlifeToken()), this.getWildlifeToken(), other.getWildlifeToken());

        return (this.getHabitatTileTypeList().equals(other.getHabitatTileTypeList())) &&
                (this.getWildlifeTokenTypeList().equals(other.getWildlifeTokenTypeList())) &&
                (this.getWildlifeToken().equals(other.getWildlifeToken())) &&
                (this.isKeystone() == other.isKeystone()) &&
                (this.isNull() == other.isNull());
    }

}
