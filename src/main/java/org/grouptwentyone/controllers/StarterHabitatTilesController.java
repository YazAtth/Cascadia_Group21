/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.controllers;

import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;
import java.util.Collections;

import static org.grouptwentyone.controllers.HabitatTilesController.habitatTileTypeSelector;
import static org.grouptwentyone.controllers.HabitatTilesController.wildlifeTokenTypeSelector;
import static org.grouptwentyone.models.HabitatTile.habitatTileTypeListGenerator;

public class StarterHabitatTilesController {
    public static ArrayList<ArrayList<HabitatTile>> starterHabitatTilesBag = createBagOfStarterHabitatTiles();

    public static ArrayList<ArrayList<HabitatTile>> createBagOfStarterHabitatTiles() {
        ArrayList<ArrayList<HabitatTile>> starterTilesBag = new ArrayList<>();
        ArrayList<HabitatTile> newStarterTile = new ArrayList<>();

        //create wildlife token type list of unique types
        ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList = new ArrayList<>();
        for (int k = 0; k < 5; k++) {
            wildlifeTokenTypeList.add(wildlifeTokenTypeSelector(k).get(0));
        }

        for (int i = 0; i < 5; i++) {
            //creates a keystone tile
            newStarterTile.add(new HabitatTile(habitatTileTypeSelector(i), wildlifeTokenTypeSelector(i)));



            //creates 2 tiles, on with 2 wildlife types, another with 3, all with unique animals
            Collections.shuffle(wildlifeTokenTypeList);
            ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList1 = new ArrayList<>(),
                    wildlifeTokenTypeList2 = new ArrayList<>();
            wildlifeTokenTypeList1.add(wildlifeTokenTypeList.get(0));
            wildlifeTokenTypeList1.add(wildlifeTokenTypeList.get(1));
            wildlifeTokenTypeList2.add(wildlifeTokenTypeList.get(2));
            wildlifeTokenTypeList2.add(wildlifeTokenTypeList.get(3));
            wildlifeTokenTypeList2.add(wildlifeTokenTypeList.get(4));

            newStarterTile.add(new HabitatTile(habitatTileTypeListGenerator(2), wildlifeTokenTypeList1));
            newStarterTile.add(new HabitatTile(habitatTileTypeListGenerator(2), wildlifeTokenTypeList2));

            //adds all 3 tiles created to the startTiles bag as a starter tile
            starterTilesBag.add(newStarterTile);
            newStarterTile = new ArrayList<>();
        }

        //randomise order of tiles
        Collections.shuffle((starterTilesBag));
        return starterTilesBag;
    }

    public static ArrayList<HabitatTile> getStarterTile() {

        return starterHabitatTilesBag.remove(0);
    }
}
