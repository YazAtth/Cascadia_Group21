package org.grouptwentyone;

import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.HexCoordinate;
import org.grouptwentyone.models.Player;

public class TestingSpace {
    //moved code to separate method so main can be used to call specific tests
    private static void playerBoardBufferTest() {
        Player p1 = new Player("bob");
        HabitatTile habitatTile = new HabitatTile(2);
        p1.setSelectedTile(habitatTile);
        p1.addNewTile(new HexCoordinate(0,0));

//        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.TOP);
//        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.TOP);
        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.LEFT);
        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.BOTTOM);
        p1.addPlayerBoardBuffer(Player.PlayerBoardSide.RIGHT);


//        System.out.println(p1.getPlayerBoard().get(0));

//        System.out.println(p1.isCoordinateOnBoardEdge(new HexCoordinate(0, 4)));
//        System.out.println(p1.getPartOfBoardCoordinateIsOn(new HexCoordinate(4,5)));
//        System.out.println(p1.getPlayerBoard().get(3).get(4));
//        System.out.println("\n");
        p1.printPlayerBoard();
    }
    public static void main(String[] args) {
        playerBoardBufferTest();
    }
}
