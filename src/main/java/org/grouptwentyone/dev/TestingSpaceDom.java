package org.grouptwentyone.dev;

import org.grouptwentyone.models.CascadiaBot;
import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.HexCoordinate;

public class TestingSpaceDom {
    public static void testingReserve() {

        CascadiaBot p1 = new CascadiaBot("Ton");

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.playTurn();
    }

    public static void main(String[] args) {
//        testingCascadiaBot1();
//        testingDuplication();

//        testingSetBotPage();
        testingReserve();
    }
}
