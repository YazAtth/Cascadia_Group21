package org.grouptwentyone.views;

import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.PlayerBoard;
import org.grouptwentyone.models.Tile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.Hashtable;

import java.util.ArrayList;

public class BoardView {


    //gave these hashtables a private scope for use in helper methods
    private static final Hashtable<HabitatTile.HabitatTileType, String> tileToColourTable = new Hashtable<>();
    private static final Hashtable<WildlifeToken.WildlifeTokenType, String> tokenToStringTable = new Hashtable<>();

    //static method to display tiles
    public static String displayTiles(PlayerBoard playerBoardObject) {
        ArrayList<ArrayList<Tile>> playerBoard = playerBoardObject.getPlayerBoardAs2dArray();

        StringBuilder pattern = new StringBuilder();

        //link HabitatTileTypes to a colour
        //Hashtable<HabitatTile.HabitatTileType, String> tileToColourTable = new Hashtable<HabitatTile.HabitatTileType, String>();
        tileToColourTable.put(HabitatTile.HabitatTileType.FORESTS, "\033[1;92m");
        tileToColourTable.put(HabitatTile.HabitatTileType.MOUNTAINS, "\033[1;97m");
        tileToColourTable.put(HabitatTile.HabitatTileType.PRAIRIES, "\033[1;93m");
        tileToColourTable.put(HabitatTile.HabitatTileType.RIVERS, "\033[1;34m");
        tileToColourTable.put(HabitatTile.HabitatTileType.WETLANDS, "\033[1;96m");

        //link WildLifeTokenType to a coloured letter
//        Hashtable<WildlifeToken.WildlifeTokenType, String> tokenToStringTable = new Hashtable<WildlifeToken.WildlifeTokenType, String>();
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.BEAR, "\033[1;38;5;94m" + "B" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.ELK, "\033[1;90m" + "E" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.SALMON, "\033[1;91m" + "S" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.FOX, "\033[1;33m" + "F" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.HAWK, "\033[1;36m" + "H" + "\u001B[0m");

        String endString = "\u001B[0m";
        String greyCode = "\033[1;90m";
        String colourOne, colourTwo;
        colourOne = colourTwo = "";

        System.out.println("\033[1;92m" +  "Forest = GREEN " + endString +
                "\033[0;97m" + "  Mountains = WHITE" + endString + "\033[1;93m" + "  Prairies = YELLOW" + endString +
                "\033[1;34m" + "  Rivers = BLUE" + endString + "\033[1;96m" + "  Wetlands = CYAN" + endString);

        GameUiView.printLinePageBorder();

        System.out.println("\033[1;36m" + "  H = Hawk " + endString +
                "\033[1;38;5;94m" + "  B = Bear" + endString + "\033[1;90m" + "  E = Elk" + endString +
                "\033[1;91m" + "  S = Salmon" + endString + "\033[1;33m" + "  F = Fox" + endString);

        GameUiView.printLinePageBorder();

        //iterate over each ArrayList in the ArrayList (rows)
        for (int row = 0; row < playerBoard.size(); row++) {
            //iterate over each row of each tile (tiles size is 6)
            for (int i = 0; i < 6; i++) {

                if (row % 2 == 0) { //slight offset for even columns
                    pattern.append("       ");
                }

                //iterate over each Tile
                for (int col = 0; col < playerBoard.get(row).size(); col++) {


                    Tile currTile = playerBoard.get(row).get(col);

                    // Not ideal
                    boolean isCurrentTileAdjacentToActiveTile = false;
                    for (Tile focusedTile: playerBoardObject.getActiveTiles()) {
                        if (currTile.isEmptyTileAdjacentToTile(focusedTile)) {
                            isCurrentTileAdjacentToActiveTile = true;

                        }
                    }

                    if (!currTile.getHabitatTile().isNull()) {

                        //current row of tiles
                        ArrayList<HabitatTile.HabitatTileType> currHabitatTileTypeList = currTile.getHabitatTile().getHabitatTileTypeList();

                        if (currHabitatTileTypeList.size() == 1) {
                            colourOne = colourTwo = tileToColourTable.get(currHabitatTileTypeList.get(0));
                        } else {
                            colourOne = tileToColourTable.get(currHabitatTileTypeList.get(0));
                            colourTwo = tileToColourTable.get(currHabitatTileTypeList.get(1));
                        }
                    }

                    int currOrientation = currTile.getTileOrientation();
                    String tempString;


                    if (currOrientation == 3 || currOrientation == 4 || currOrientation == 5) {
                            tempString = colourOne;
                            colourOne = colourTwo;
                            colourTwo = tempString;
                    }

                    if (currTile.getHabitatTile().isNull() & isCurrentTileAdjacentToActiveTile) {
                        if (i == 0 || i == 5) {
                            pattern.append(greyCode + "    *  *  *    " + endString);
                        } else if (i == 1 || i == 4) {
                            pattern.append(greyCode + "  *         *  " + endString);
                        } else if (i == 3){
                            pattern.append(greyCode +  " *   " + currTile.getHexCoordinate().toString() + "   * " + endString);
                        } else  {
                            pattern.append(greyCode + " *           * " + endString);
                        }
                    } else if (currTile.getHabitatTile().isNull()) {
                        if (i == 0 || i == 5) {
                            pattern.append(greyCode + "               " + endString);
                        } else if (i == 1 || i == 4) {
                            pattern.append(greyCode + "               " + endString);
                        } else if (i == 3){
                            pattern.append(greyCode +  "          " + "     " + endString);
                        } else  {
                            pattern.append(greyCode + "               " + endString);
                        }
                    } else if (currTile.getTileOrientation() == 0 || //print horizontal tile
                    currTile.getTileOrientation() == 3) {
                        if (i == 0) {
                            pattern.append(colourOne + "    *******    " + endString);
                        } else if (i == 1) {
                            pattern.append(colourOne + "  ***********  " + endString);
                        } else if (i == 2) {
                            pattern.append(colourOne + " ****" + placeTokenInfo(0, currTile) + colourOne + "**** " + endString);
                        } else if (i == 3) {
                            pattern.append(colourTwo + " ****" + placeTokenInfo(1, currTile) + colourTwo + "**** " + endString);
                        } else if (i == 4) {
                            pattern.append(colourTwo + "  ***********  " + endString);
                        } else {
                            pattern.append(colourTwo + "    *******    " + endString);
                        }
                    }
                    else if (currOrientation == 1 || currOrientation == 4) { //print diagonal tile
                        if (i == 0) {
                            pattern.append(colourTwo + "    *" + colourOne + "******    " + endString);
                        } else if (i == 1) {
                            pattern.append(colourTwo + "  ****" + colourOne + "*******  " + endString);
                        } else if (i == 2) {
                            pattern.append(colourTwo + " ****" + placeTokenInfo(0, currTile) + colourOne + "**** " + endString);
                        } else if (i == 3) {
                            pattern.append(colourTwo + " ****" + placeTokenInfo(1, currTile) + colourOne + "**** " + endString);
                        } else if (i == 4) {
                            pattern.append(colourTwo + "  *******" + colourOne + "****  " + endString);
                        } else {
                            pattern.append(colourTwo + "    ******" + colourOne + "*    " + endString);
                        }
                    } else {
                        if (i == 0) {
                            pattern.append(colourTwo + "    ******" + colourOne + "*    " + endString);
                        } else if (i == 1) {
                            pattern.append(colourTwo + "  *******" + colourOne + "****  " + endString);
                        } else if (i == 2) {
                            pattern.append(colourTwo + " ****"  + placeTokenInfo(0, currTile) + colourOne + "**** " + endString);
                        } else if (i == 3) {
                            pattern.append(colourTwo + " ****" + placeTokenInfo(1, currTile) + colourOne + "**** " + endString);
                        } else if (i == 4) {
                            pattern.append(colourTwo + "  ****" + colourOne + "*******  " + endString);
                        } else {
                            pattern.append(colourTwo + "    *" + colourOne + "******    " + endString);
                        }
                    }
                }
                pattern.append("\n");
            }
        }
        return pattern.toString();
    }

    private static String placeTokenInfo(int line, Tile currTile) {
        String tokenString;
        String endString = "\u001B[0m";
        boolean isTokenPlaced = currTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY;

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

        if (!isTokenPlaced) {
            if (line == 0) {
                if (currTile.getHabitatTile().getWildlifeTokenTypeList().size() == 1) {
                    tokenString = "  " + tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(0)) + "  ";
                } else if (currTile.getHabitatTile().getWildlifeTokenTypeList().size() == 2) {
                    tokenString = " " + tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(0)) + " " +
                            tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(1)) + " ";
                } else {
                    tokenString = tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(0)) + " " +
                            tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(1)) + " " +
                            tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(2));
                }
                return tokenString + endString;
            } else if (line == 1) {
                //return string of coordinates
                return endString + currTile.getHexCoordinate().toString();
            } else {
                throw new IllegalArgumentException("invalid line num passed to func");
            }
        } else {
            String colour = tokenToColourTable.get(currTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());

            if (line == 0) {
                tokenString = colour +   "*****" + endString;
            } else if (line == 1) {
                tokenString = colour + "* " + tokenToLetterTable.get(currTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType()) + " *" + endString;
            } else {
                throw new IllegalArgumentException("invalid line num passed to func");
            }
            return tokenString;
        }
    }
}
