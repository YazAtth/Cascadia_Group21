package org.grouptwentyone.views;

import org.grouptwentyone.controllers.HabitatTilesController;
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
    }

    public static void main(String[] args) {
        addToBoard();
    }
}
