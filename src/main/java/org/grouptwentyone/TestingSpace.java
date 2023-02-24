package org.grouptwentyone;

import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.HexCoordinate;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.models.Tile;

import java.util.ArrayList;

public class TestingSpace {
    //moved code to separate method so main can be used to call specific tests
    private static void playerBoardBufferTest() {
        Player p1 = new Player("bob");
        HabitatTile habitatTile = new HabitatTile(2);
//        p1.setSelectedTile(habitatTile);
//        p1.addNewTile(new HexCoordinate(0,0));
//        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(0,0));

//        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.TOP);
//        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.TOP);
//        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.LEFT);
//        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.BOTTOM);
//        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.RIGHT);


//        System.out.println(p1.getPlayerBoard().get(0));

//        System.out.println(p1.isCoordinateOnBoardEdge(new HexCoordinate(0, 4)));
//        System.out.println(p1.getPartOfBoardCoordinateIsOn(new HexCoordinate(4,5)));
//        System.out.println(p1.getPlayerBoard().get(3).get(4));
//        System.out.println("\n");
//        p1.printPlayerBoard();
    }

    private static ArrayList<ArrayList<Tile>> playerBoard = new ArrayList<>();

    //sample code for testing board display (moved from BoardView)
    public static void testBoardDisplay() {
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

    public static void main(String[] args) {
        playerBoardBufferTest();
    }
}
