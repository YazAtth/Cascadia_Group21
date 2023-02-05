package org.grouptwentyone.views;

import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.HexCoordinate;
import org.grouptwentyone.models.Tile;

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

        playerBoard.get(1).add(1, oneOne);
        //note oneTwo is added at index [2, 1]
        playerBoard.get(2).add(1, oneTwo);
        playerBoard.get(1).add(2, twoOne);

        //dummy loops //DELETE ONCE ARRAY LISTS ARE SIZE THREE
        System.out.println(playerBoard.size());
        System.out.println(playerBoard.get(0).size());
        System.out.println(playerBoard.get(1).size());
        System.out.println(playerBoard.get(2).size());


        for (int row = 0; row < playerBoard.size() ; row++) {
            for (int col = 0; col < playerBoard.get(row).size(); col++) {
                Tile currTile = playerBoard.get(row).get(col);
                System.out.println(currTile.isActive());
            }
        }  //END OF DUMMY LOOPS
    }

    //static method to display tiles
    public static String displayTiles(ArrayList<ArrayList<Tile>> playerBoard) {
        StringBuilder pattern = new StringBuilder();

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
                    HabitatTile currHabitatTile = playerBoard.get(row).get(col).getHabitatTile();


                    if (!playerBoard.get(row).get(col).isActive()) {
                        pattern.append("               ");
                    } else if (i == 0 || i == 5) {
                        pattern.append("    *  *  *    ");
                    } else if (i == 1 || i == 4) {
                        pattern.append("  *         *  ");
                    } else if (i == 2 || i == 3) {
                        pattern.append(" *           * ");
                    } else {
                        pattern.append("*             *");
                    }
                }
                pattern.append("\n");
            }
        }
        return pattern.toString();
    }

    public static void main(String[] args) {
        addToBoard();
        //System.out.println(displayTiles(playerBoard));
    }
}
