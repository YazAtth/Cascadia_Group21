package org.grouptwentyone.views;

import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.Tile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;
import java.util.Hashtable;

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

        return selectedHabitatTiles;
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

    public static String displaySelectedHabitatTiles(ArrayList<HabitatTile> selectedHabitatTiles) {
        StringBuilder pattern = new StringBuilder();

        //link HabitatTileTypes to a colour
        Hashtable<HabitatTile.HabitatTileType, String> tileToColourTable = new Hashtable<>();
        tileToColourTable.put(HabitatTile.HabitatTileType.FORESTS, "\033[1;92m");
        tileToColourTable.put(HabitatTile.HabitatTileType.MOUNTAINS, "\033[1;37m");
        tileToColourTable.put(HabitatTile.HabitatTileType.PRAIRIES, "\033[1;93m");
        tileToColourTable.put(HabitatTile.HabitatTileType.RIVERS, "\033[1;34m");
        tileToColourTable.put(HabitatTile.HabitatTileType.WETLANDS, "\033[1;96m");

        //link WildLifeTokenType to a coloured letter
        Hashtable<WildlifeToken.WildlifeTokenType, String> tokenToStringTable = new Hashtable<>();
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.BEAR, "\033[1;38;5;94m" + "B" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.ELK, "\033[1;90m" + "E" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.SALMON, "\033[1;91m" + "S" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.FOX, "\033[1;33m" + "F" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.HAWK, "\033[1;36m" + "H" + "\u001B[0m");

        String tokenString = "";
        String endString = "\u001B[0m";
        String colourOne, colourTwo, colourThree, colourFour, colourFive, colourSix;
        colourOne = colourTwo = colourThree = colourFour = colourFive = colourSix = "";

        //iterate through habitat tiles
        //iterate over each row of each tile (tiles size is 6)
        for (int i = 0; i < 6; i++) {
//
//            if (row % 2 == 0) { //slight offset for even columns
//                pattern.append("       ");
//            }

            //iterate over each Tile
            for (int col = 0; col < selectedHabitatTiles.size(); col++) {

                HabitatTile currTile = selectedHabitatTiles.get(col);


                if (currTile == null) {
                    //if null do nothing
                }

                else {

                    //current row of tiles
                    ArrayList<HabitatTile.HabitatTileType> currHabitatTileTypeList = currTile.getHabitatTileTypeList();

                    //tokenString should always length 5, hence the different if statements
                    if (currHabitatTileTypeList.size() == 1) {
                        colourOne = colourTwo = colourThree = colourFour = colourFive = colourSix = tileToColourTable.get(currHabitatTileTypeList.get(0));
                        tokenString = "  " + tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + "  ";
                    } else if (currHabitatTileTypeList.size() == 2) {
                        colourOne = colourTwo = colourThree = tileToColourTable.get(currHabitatTileTypeList.get(0));
                        colourFour = colourFive = colourSix = tileToColourTable.get(currHabitatTileTypeList.get(1));
                        tokenString = " " + tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + " " +
                                tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(1)) + " ";

                    } else {
                        colourOne = colourTwo = tileToColourTable.get(currHabitatTileTypeList.get(0));
                        colourThree = colourFour = tileToColourTable.get(currHabitatTileTypeList.get(1));
                        colourFive = colourSix = tileToColourTable.get(currHabitatTileTypeList.get(2));
                        tokenString = tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + " " +
                                tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(1)) + " " +
                                tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(2));
                    }


                }

                //if tile is not placed, just print spaces
                /*if (!selectedHabitatTiles.get(row).get(col).isActive()) {
                    pattern.append("               ");
                } else*/ if (i == 0) {
                    pattern.append(colourOne +   "    *******    " + endString);
                } else if (i == 1) {
                    pattern.append(colourTwo +   "  ***********  " + endString);
                } else if (i == 2) {
                    pattern.append(colourThree + " ****" + tokenString +  endString + colourThree + "**** " + endString);
                } else if (i == 3) {
                    pattern.append(colourFour +  " ****     **** " + endString);
                } else if (i == 4) {
                    pattern.append(colourFive +  "  ***********  " + endString);
                } else {
                    pattern.append(colourSix +   "    *******    " + endString);
                }
            }
            pattern.append("\n");
        }
        pattern.append("\n");
        return pattern.toString();
    }

    public static void main(String[] args) {
        System.out.println(displaySelectedHabitatTiles(getFourHabitatTiles()));
    }
}
