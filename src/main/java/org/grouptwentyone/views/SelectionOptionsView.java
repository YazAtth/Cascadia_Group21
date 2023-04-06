/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.views;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.grouptwentyone.controllers.HabitatTilesController.habitatTilesBag;
import static org.grouptwentyone.controllers.WildlifeTokensController.wildlifeTokenBag;

/**
 * This class manages the display of Habitat Tiles and Wildlife Tokens to the user and the subsequent selection of them.
 */
public class SelectionOptionsView {
    /**
     * Used to initialise the ArrayList that displays the Habitat Tiles options.
     * @return An ArrayList of four Habitat Tiles.
     */
    public static ArrayList<HabitatTile> getFourHabitatTiles() {
        ArrayList<HabitatTile> selectedHabitatTiles = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            //gets a tile from habitatTileBag and removes it from that bag simultaneously
            selectedHabitatTiles.add(habitatTilesBag.remove(0));
        }

        return selectedHabitatTiles;
    }

    /**
     * Used to initialise the ArrayList that displays the Wildlife Token options.
     * @return An ArrayList of four Wildlife Tokens
     */
    public static ArrayList<WildlifeToken> getFourWildlifeTokens() {
        ArrayList<WildlifeToken> selectedWildlifeTokens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            //gets a token from wildlifeTokenBag and removes it from that bag simultaneously
            selectedWildlifeTokens.add(wildlifeTokenBag.remove(0));
        }
        return selectedWildlifeTokens;
    }

    /**
     * Generates a String representation of the four Habitat Tiles that the player can later choose from.
     * @param selectedHabitatTiles - The ArrayList of Habitat Tiles that the player can choose from.
     * @return The String representation of Tiles that can then be printed to console.
     */
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

        //iterate over each row of each tile (tiles size is 6)
        for (int i = 0; i < 6; i++) {

            //iterate over each Tile
            for (HabitatTile currTile : selectedHabitatTiles) {

                ArrayList<HabitatTile.HabitatTileType> currHabitatTileTypeList = currTile.getHabitatTileTypeList();

                //creates a string of the token types that can be placed on the tile
                if (currHabitatTileTypeList.size() == 1) {
                    colourOne = colourTwo = colourThree = colourFour = colourFive = colourSix = tileToColourTable.get(currHabitatTileTypeList.get(0));
                    tokenString = "  " + tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + "  ";
                } else {
                    colourOne = colourTwo = colourThree = tileToColourTable.get(currHabitatTileTypeList.get(0));
                    colourFour = colourFive = colourSix = tileToColourTable.get(currHabitatTileTypeList.get(1));
                    if (currTile.getWildlifeTokenTypeList().size() == 2) {
                        tokenString = " " + tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + " " +
                                tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(1)) + " ";
                    } else {
                        tokenString = tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(0)) + " " +
                                tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(1)) + " " +
                                tokenToStringTable.get(currTile.getWildlifeTokenTypeList().get(2));
                    }
                }
                if (i == 0) {
                    pattern.append(colourOne + "    *******    " + endString);
                } else if (i == 1) {
                    pattern.append(colourTwo + "  ***********  " + endString);
                } else if (i == 2) {
                    pattern.append(colourThree + " ****" + tokenString + endString + colourThree + "**** " + endString);
                } else if (i == 3) {
                    pattern.append(colourFour + " ****     **** " + endString);
                } else if (i == 4) {
                    pattern.append(colourFive + "  ***********  " + endString);
                } else {
                    pattern.append(colourSix + "    *******    " + endString);
                }
            }
            pattern.append("\n");
        }
        pattern.append("\n");
        return pattern.toString();
    }

    /**
     * Generates a String representation of the four Wildlife Tokens that the player can later choose from.
     * @param selectedWildlifeTokens - The ArrayList of Wildlife Tokens that the player can choose from.
     * @return The String representation of Tokens that can then be printed to console.
     */
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

        //iterate through wildlife tokens
        for (int i = 0; i < 3; i++) {

            //iterate over each Token
            for (WildlifeToken currToken : selectedWildlifeTokens) {

                WildlifeToken.WildlifeTokenType currWildlifeTokenType = currToken.getWildlifeTokenType();

                colour = tokenToColourTable.get(currWildlifeTokenType);
                tokenString = " " + tokenToLetterTable.get(currWildlifeTokenType) + " ";

                if (i == 0) {
                    pattern.append(colour + "     *****     " + endString);
                } else if (i == 1) {
                    pattern.append(colour + "     *" + tokenString + "*     " + endString);
                } else {
                    pattern.append(colour + "     *****     " + endString);
                }
            }
            pattern.append("\n");
        }
        return pattern.toString();
    }

    /**
     * Selects a Habitat Tile and Wildlife Token pair from Tiles and Tokens that have already bee displayed to the user.
     * @param activePlayer - the Player who is selecting the Tile and Token pair and will have the pair assigned to
     *                     their selectedTile and selectedToken variables.
     */
    public static void selectTileAndToken(Player activePlayer) {
        System.out.print("Please select one of the above pairs by entering the associated number: \n> ");
        int userNum = -1;

        while (userNum < 1 || userNum > 4) {
            String userInput = UserInputView.askUserForInput();
            try {
                userNum = Integer.parseInt(userInput);
            } catch (NumberFormatException ex) {
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                        "between 1 and 4 to select an above pair: %s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
            }
            if (userNum < 1 || userNum > 4)
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                        "between 1 and 4 to select an above pair: %s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
        }
        userNum--;

        activePlayer.getPlayerBoardObject().setSelectedToken(StartGame.selectedTokens.remove(userNum));
        activePlayer.getPlayerBoardObject().setSelectedTile(StartGame.selectedTiles.remove(userNum));

        //detects that no tiles remain so ends player turns
        if (!replaceTileAndToken()) {
            StartGame.tilesRemain = false;
        }
    }

    /**
     * Helper method that replaces a selected Tile and Token with a new one into their respective ArrayLists, using
     * the ArrayLists that store the generated Tiles and Tokens for the game.
     * @return Returns true if Tile and Token are successfully replaced.
     */
    public static boolean replaceTileAndToken() {
        if (habitatTilesBag.size() > 0 && wildlifeTokenBag.size() > 0) {
            StartGame.selectedTiles.add(habitatTilesBag.remove(0));
            StartGame.selectedTokens.add(wildlifeTokenBag.remove(0));
            return true;
        } else {
            return false;
        }
    }
}
