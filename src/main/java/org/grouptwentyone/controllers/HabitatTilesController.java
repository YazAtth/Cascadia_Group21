package org.grouptwentyone.controllers;

import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;
import java.util.Collections;

public class HabitatTilesController {
    public static ArrayList<HabitatTile> habitatTilesBag = createBagOfHabitatTiles();

    public static ArrayList<HabitatTile> createBagOfHabitatTiles() {
        ArrayList<HabitatTile> habitatTilesBag = new ArrayList<>();

        //create keystone tiles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList = wildlifeTokenTypeSelector(i);
                ArrayList<HabitatTile.HabitatTileType> habitatTileTypeList = habitatTileTypeSelector(j);
                habitatTilesBag.add(new HabitatTile(habitatTileTypeList, wildlifeTokenTypeList));
            }
        }

        //create other tiles
        for (int i = 2; i <=3; i++) {
            for (int j = 1; j <= 30; j++) {
                habitatTilesBag.add(new HabitatTile(i));
            }
        }

        Collections.shuffle(habitatTilesBag);

        return habitatTilesBag;
    }

    private static ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeSelector(int wildlifeType) {
        ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList = new ArrayList<>();

        switch (wildlifeType) {
            case 0 -> wildlifeTokenTypeList.add(WildlifeToken.WildlifeTokenType.BEAR);
            case 1 -> wildlifeTokenTypeList.add(WildlifeToken.WildlifeTokenType.ELK);
            case 2 -> wildlifeTokenTypeList.add(WildlifeToken.WildlifeTokenType.SALMON);
            case 3 -> wildlifeTokenTypeList.add(WildlifeToken.WildlifeTokenType.HAWK);
            case 4 -> wildlifeTokenTypeList.add(WildlifeToken.WildlifeTokenType.FOX);
            default -> throw new IllegalArgumentException("num generator failed");
        }

        return wildlifeTokenTypeList;
    }

    private static ArrayList<HabitatTile.HabitatTileType> habitatTileTypeSelector(int tileType) {
        ArrayList<HabitatTile.HabitatTileType> HabitatTileTypeList = new ArrayList<>();

        switch (tileType) {
            case 0 -> HabitatTileTypeList.add(HabitatTile.HabitatTileType.MOUNTAINS);
            case 1 -> HabitatTileTypeList.add(HabitatTile.HabitatTileType.FORESTS);
            case 2 -> HabitatTileTypeList.add(HabitatTile.HabitatTileType.PRAIRIES);
            case 3 -> HabitatTileTypeList.add(HabitatTile.HabitatTileType.WETLANDS);
            case 4 -> HabitatTileTypeList.add(HabitatTile.HabitatTileType.RIVERS);
            default -> throw new IllegalArgumentException("num generator failed");
        }

        return HabitatTileTypeList;
    }
}
