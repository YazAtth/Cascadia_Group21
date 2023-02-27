package org.grouptwentyone.views;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import static org.grouptwentyone.controllers.HabitatTilesController.habitatTilesBag;
import static org.grouptwentyone.controllers.WildlifeTokensController.wildlifeTokenBag;

public class SelectionOptionsView {
    public static ArrayList<HabitatTile> getFourHabitatTiles() {
        ArrayList<HabitatTile> selectedHabitatTiles = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            //gets a tile from habitatTileBag and removes it from that bag simultaneously
            selectedHabitatTiles.add(habitatTilesBag.remove(0));
        }

        return selectedHabitatTiles;
    }

    public static ArrayList<WildlifeToken> getFourWildlifeTokens() {
        ArrayList<WildlifeToken> selectedWildlifeTokens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            //gets a token from wildlifeTokenBag and removes it from that bag simultaneously
            selectedWildlifeTokens.add(wildlifeTokenBag.remove(0));
        }
        return selectedWildlifeTokens;
    }

    public static String displaySelectedHabitatTiles(ArrayList<HabitatTile> selectedHabitatTiles) {
        StringBuilder pattern = new StringBuilder();

        //link HabitatTileTypes to a colour
        Hashtable<HabitatTile.HabitatTileType, String> tileToColourTable = new Hashtable<>();
        tileToColourTable.put(HabitatTile.HabitatTileType.FORESTS, "\033[1;92m");
        tileToColourTable.put(HabitatTile.HabitatTileType.MOUNTAINS, "\033[1;97m");
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

        String tokenString;
        String endString = "\u001B[0m";
        String colourOne, colourTwo, colourThree, colourFour, colourFive, colourSix;

        //iterate through habitat tiles
        //iterate over each row of each tile (tiles size is 6)
        for (int i = 0; i < 6; i++) {

            //iterate over each Tile
            for (int col = 0; col < selectedHabitatTiles.size(); col++) {

                HabitatTile currTile = selectedHabitatTiles.get(col);


                //current row of tiles
                ArrayList<HabitatTile.HabitatTileType> currHabitatTileTypeList = currTile.getHabitatTileTypeList();

                //tokenString should always length 5, hence the different if statements
                if (currHabitatTileTypeList.size() == 1) {
                    colourOne = colourTwo = colourThree = colourFour = colourFive = colourSix = tileToColourTable.get(currHabitatTileTypeList.get(0));
                    tokenString = "  " + tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + "  ";
                } else {
                    colourOne = colourTwo = colourThree = tileToColourTable.get(currHabitatTileTypeList.get(0));
                    colourFour = colourFive = colourSix = tileToColourTable.get(currHabitatTileTypeList.get(1));
                    tokenString = " " + tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + " " +
                            tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(1)) + " ";
                }
                if (i == 0) {
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

    public static String displaySelectedWildlifeTokens(ArrayList<WildlifeToken> selectedWildlifeTokens) {
        StringBuilder pattern = new StringBuilder();

        //link WildLifeTokenType to a colour
        Hashtable<WildlifeToken.WildlifeTokenType, String> tokenToColourTable = new Hashtable<>();
        tokenToColourTable.put(WildlifeToken.WildlifeTokenType.BEAR, "\033[1;38;5;94m");
        tokenToColourTable.put(WildlifeToken.WildlifeTokenType.ELK, "\033[1;90m");
        tokenToColourTable.put(WildlifeToken.WildlifeTokenType.SALMON, "\033[1;91m");
        tokenToColourTable.put(WildlifeToken.WildlifeTokenType.FOX, "\033[1;33m");
        tokenToColourTable.put(WildlifeToken.WildlifeTokenType.HAWK, "\033[1;36m");

        //link WildLifeTokenType to a letter
        Hashtable<WildlifeToken.WildlifeTokenType, String> tokenToLetterTable = new Hashtable<>();
        tokenToLetterTable.put(WildlifeToken.WildlifeTokenType.BEAR, "B");
        tokenToLetterTable.put(WildlifeToken.WildlifeTokenType.ELK, "E");
        tokenToLetterTable.put(WildlifeToken.WildlifeTokenType.SALMON, "S");
        tokenToLetterTable.put(WildlifeToken.WildlifeTokenType.FOX, "F");
        tokenToLetterTable.put(WildlifeToken.WildlifeTokenType.HAWK, "H");

        String tokenString;
        String endString = "\u001B[0m";
        String colour;

        //iterate through habitat tiles
        //iterate over each row of each tile (tiles size is 6)
        for (int i = 0; i < 3; i++) {

            //iterate over each Tile
            for (int j = 0; j < selectedWildlifeTokens.size(); j++) {

                WildlifeToken currToken = selectedWildlifeTokens.get(j);


                //current row of tiles
                WildlifeToken.WildlifeTokenType currWildlifeTokenType = currToken.getWildlifeTokenType();

                colour = tokenToColourTable.get(currWildlifeTokenType);
                tokenString = " " + tokenToLetterTable.get(currWildlifeTokenType) + " ";

                if (i == 0) {
                    pattern.append(colour +   "     *****     " + endString);
                } else if (i == 1) {
                    pattern.append(colour + "     *" + tokenString + "*     " + endString);
                } else {
                    pattern.append(colour +   "     *****     " + endString);
                }
            }
            pattern.append("\n");
        }
        //pattern.append("\n");
        return pattern.toString();
    }

    public static void selectTileAndToken(Player activePlayer) {
        System.out.print("Please select one of the above pairs by entering the associated number: \n>");
        int userNum = -1;

        while (userNum < 1 || userNum > 4) {
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            try {
                userNum = Integer.parseInt(userInput);
            } catch (NumberFormatException ex) {
                System.out.printf("Invalid argument, please enter a number between " +
                        "1 and %s to select an above pair: \n>", StartGame.selectedTiles.size());
            }
            if (userNum < 1 || userNum > org.grouptwentyone.StartGame.selectedTiles.size())
                System.out.printf("Invalid argument, please enter a number " +
                    "between 1 and %s to select an above pair: \n>", StartGame.selectedTiles.size());
        }
        userNum--;

//        activePlayer.setSelectedTile(org.grouptwentyone.StartGame.selectedTiles.remove(userNum));
//        activePlayer.setSelectedToken(org.grouptwentyone.StartGame.selectedTokens.remove(userNum));
        activePlayer.getPlayerBoardObject().setSelectedToken(org.grouptwentyone.StartGame.selectedTokens.remove(userNum));
        activePlayer.getPlayerBoardObject().setSelectedTile(org.grouptwentyone.StartGame.selectedTiles.remove(userNum));

        //detects that no tiles remain so ends player turns
        if (!replaceTileAndToken()) {
            StartGame.tilesRemain = false;
        }
    }

    private static boolean replaceTileAndToken() {
        if (habitatTilesBag.size() > 0 && wildlifeTokenBag.size() > 0) {
            org.grouptwentyone.StartGame.selectedTiles.add(habitatTilesBag.remove(0));
            org.grouptwentyone.StartGame.selectedTokens.add(wildlifeTokenBag.remove(0));
            return true;
        } else {
            return false;
        }
    }
}
