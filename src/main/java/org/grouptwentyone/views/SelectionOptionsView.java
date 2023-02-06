package org.grouptwentyone.views;

import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;

import static org.grouptwentyone.controllers.HabitatTilesController.habitatTilesBag;
import static org.grouptwentyone.controllers.WildlifeTokensController.wildlifeTokenBag;

public class SelectionOptionsView {
    public static ArrayList<HabitatTile> getFourHabitatTiles() {
        ArrayList<HabitatTile> selectedHabitatTiles = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            //gets a tile from habitatTileBag and then removes it from that bag
            selectedHabitatTiles.add(habitatTilesBag.get(0));
            habitatTilesBag.remove(0);
        }

        return habitatTilesBag;
    }

    public static ArrayList<WildlifeToken> getFourWildlifeTokens() {
        ArrayList<WildlifeToken> selectedWildlifeTokens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            //gets a token from wildlifeTokenBag and then removes it from that bag
            selectedWildlifeTokens.add(wildlifeTokenBag.get(0));
            wildlifeTokenBag.remove(0);
        }
        return selectedWildlifeTokens;
    }
}
