package org.grouptwentyone.views;

import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.HexCoordinate;
import org.grouptwentyone.models.Tile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.Hashtable;
import java.util.Objects;

import java.util.ArrayList;

public class TestBoardView {
    public static ArrayList<ArrayList<Tile>> playerBoard = new ArrayList<>();

    public static void addToBoard() {
        //these for loops initialise the board to null habitat tiles
        int sizeX = 3, sizeY = 3;
        for (int i = 0; i < sizeX; i++) {
            playerBoard.add(new ArrayList<>());
        }
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                playerBoard.get(i).add(j, new Tile(new HexCoordinate(i, j)));
            }
        }

        //create dummy tiles
        Tile oneOne = new Tile(HabitatTilesController.habitatTilesBag.get(0), new HexCoordinate(1, 1));
        Tile oneTwo = new Tile(HabitatTilesController.habitatTilesBag.get(1), new HexCoordinate(1, 2));
        Tile twoOne = new Tile(HabitatTilesController.habitatTilesBag.get(2), new HexCoordinate(2, 1));

        playerBoard.get(1).set(1, oneOne);
        //note oneTwo is added at index [2, 1]
        playerBoard.get(2).set(1, oneTwo);
        playerBoard.get(1).set(2, twoOne);
    }

    //static method to display tiles
    public static String displayTiles(ArrayList<ArrayList<Tile>> playerBoard) {

        StringBuilder pattern = new StringBuilder();

        //link HabitatTileTypes to a colour
        Hashtable<HabitatTile.HabitatTileType, String> tileToColourTable = new Hashtable<HabitatTile.HabitatTileType, String>();
        tileToColourTable.put(HabitatTile.HabitatTileType.FORESTS, "\033[1;92m");
        tileToColourTable.put(HabitatTile.HabitatTileType.MOUNTAINS, "\033[1;37m");
        tileToColourTable.put(HabitatTile.HabitatTileType.PRAIRIES, "\033[1;93m");
        tileToColourTable.put(HabitatTile.HabitatTileType.RIVERS, "\033[1;34m");
        tileToColourTable.put(HabitatTile.HabitatTileType.WETLANDS, "\033[1;96m");

        //link WildLifeTokenType to a coloured letter
        Hashtable<WildlifeToken.WildlifeTokenType, String> tokenToStringTable = new Hashtable<WildlifeToken.WildlifeTokenType, String>();
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.BEAR, "\033[1;38;5;94m" + "B" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.ELK, "\033[1;90m" + "E" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.SALMON, "\033[1;91m" + "S" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.FOX, "\033[1;33m" + "F" + "\u001B[0m");
        tokenToStringTable.put(WildlifeToken.WildlifeTokenType.HAWK, "\033[1;36m" + "H" + "\u001B[0m");

        String tokenString = "";
        String endString = "\u001B[0m";
        String colourOne, colourTwo;
        colourOne = colourTwo = "";

        System.out.println("\033[1;92m" +  "Forest = GREEN " + endString +
                "\033[1;37m" + "  Mountains = GREY" + endString + "\033[1;93m" + "  Prairies = YELLOW" + endString +
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


                    if (currTile.getHabitatTile() == null) {
                        //if null do nothing
                    }

                    else {

                        //current row of tiles
                        ArrayList<HabitatTile.HabitatTileType> currHabitatTileTypeList = currTile.getHabitatTile().getHabitatTileTypeList();

                        if (currHabitatTileTypeList.size() == 1) {
                            colourOne = colourTwo = tileToColourTable.get(currHabitatTileTypeList.get(0));
                            tokenString = "  " + tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(0)) + "  ";
                        } else {
                            colourOne = tileToColourTable.get(currHabitatTileTypeList.get(0));
                            colourTwo = tileToColourTable.get(currHabitatTileTypeList.get(1));
                            tokenString = " " + tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(0)) + " " +
                                                tokenToStringTable.get(currTile.getHabitatTile().getWildlifeTokenTypeList().get(1)) + " ";
                        }

                        //tokenString should always length 5, hence the different if statements
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
                    }

                    //if tile is not placed, just print spaces
                    if (!playerBoard.get(row).get(col).isActive()) {
                        pattern.append("               ");
                    } else if (i == 0) {
                        pattern.append(colourOne +   "    *******    " + endString);
                    } else if (i == 1) {
                        pattern.append(colourOne +   "  ***********  " + endString);
                    } else if (i == 2) {
                        pattern.append(colourOne + " ****" + tokenString +  endString + colourOne + "**** " + endString);
                    } else if (i == 3) {
                        pattern.append(colourTwo +  " ****     **** " + endString);
                    } else if (i == 4) {
                        pattern.append(colourTwo +  "  ***********  " + endString);
                    } else {
                        pattern.append(colourTwo +   "    *******    " + endString);
                    }
                }
                pattern.append("\n");
            }
        }
        return pattern.toString();
    }

    public static void main(String[] args) {
        addToBoard();
        System.out.println(displayTiles(playerBoard));
    }
}
