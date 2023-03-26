/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone;

import static org.junit.jupiter.api.Assertions.*;

import org.grouptwentyone.controllers.ScoringController;
import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.models.*;
import org.grouptwentyone.views.BoardView;
import org.junit.jupiter.api.*;

class ScoringAlgorithmTest {


    @BeforeEach
    // Workaround for bug where after creating 5 instances of the Player class: the starterHabitatTilesBag runs out of tiles.
    void resetTileBag() {
        StarterHabitatTilesController.starterHabitatTilesBag = StarterHabitatTilesController.createBagOfStarterHabitatTiles();
    }

    @Nested
    @DisplayName("Test Fox Cards")
    class TestFoxCards {
        @Test
        void testFoxCardA() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));


//        System.out.println(BoardView.displayTiles(p1.getPlayerBoardObject().getPlayerBoardAs2dArray()));
            int score = ScoringController.scoreFoxScoringCardA(p1.getPlayerBoardObject());

            assertEquals(3, score);
        }

        @Test
        void testFoxCardB() {
//        System.out.println("ran");
//        PlayerBoard pb = new PlayerBoard();
            Player p1 = new Player("Ton");


            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));


//        System.out.println(p1.getPlayerBoardObject().getPlayerBoardAs2dArray());
//        System.out.println(BoardView.displayTiles(p1.getPlayerBoardObject().getPlayerBoardAs2dArray()));
//        System.out.printf("You have a score of: %d", p1.getScore());
            int score = ScoringController.scoreFoxScoringCardB(p1.getPlayerBoardObject());
            assertEquals(8, score);
        }

        @Test
        void testFoxCardC() {
//        System.out.println("ran");
//        PlayerBoard pb = new PlayerBoard();
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));


//        System.out.println(p1.getPlayerBoardObject().getPlayerBoardAs2dArray());
//        System.out.println(BoardView.displayTiles(p1.getPlayerBoardObject().getPlayerBoardAs2dArray()));
//        System.out.printf("You have a score of: %d", p1.getScore());

            int score = ScoringController.scoreFoxScoringCardC(p1.getPlayerBoardObject());

            assertEquals(3, score);
        }
    }

    @Nested
    @DisplayName("Test Bear Cards")
    class testBearCards {
        @Test
        void testBearCardA() {
//        System.out.println("ran");
//        PlayerBoard pb = new PlayerBoard();
            Player p1 = new Player("Ton");


            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));


//        System.out.println(p1.getPlayerBoardObject().getPlayerBoardAs2dArray());
//        System.out.println(BoardView.displayTiles(p1.getPlayerBoardObject().getPlayerBoardAs2dArray()));
//        System.out.printf("You have a score of: %d\n", p1.getScore());

            int score = ScoringController.scoreBearScoringCardA(p1.getPlayerBoardObject());
            assertEquals(11, score);
        }

        @Test
        void testBearCardB() {
//        System.out.println("ran");
//        PlayerBoard pb = new PlayerBoard();
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));


//            System.out.println(p1.getPlayerBoardObject().getPlayerBoardAs2dArray());
//            System.out.println(BoardView.displayTiles(p1.getPlayerBoardObject().getPlayerBoardAs2dArray()));
//            System.out.printf("You have a score of: %d\n", p1.getScore());

//        System.out.println(ScoringController.getTileGroupOfSizeNFromTile(p1.getPlayerBoardObject(), new HexCoordinate(2, 6), 1));
//        System.out.println(ScoringController.getTileGroupOfSizeNFromTile(p1.getPlayerBoardObject(), new HexCoordinate(2, 2), 1));
            int score = ScoringController.scoreBearScoringCardB(p1.getPlayerBoardObject());
            assertEquals(20, score);

        }

        @Test
        void testBearCardC() {
//        System.out.println("ran");
//        PlayerBoard pb = new PlayerBoard();
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));


//            System.out.println(p1.getPlayerBoardObject().getPlayerBoardAs2dArray());
//            System.out.println(BoardView.displayTiles(p1.getPlayerBoardObject().getPlayerBoardAs2dArray()));
//            System.out.printf("You have a score of: %d\n", p1.getScore());

            int score = ScoringController.scoreBearScoringCardC(p1.getPlayerBoardObject());
            assertEquals(18, score);
        }
    }


    @Nested
    @DisplayName("Test Hawk Cards")
    class TestHawkCards {

        @Test
        void testHawkCardA() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));

            //board has 7 hawks. After removing adjacent hawks we are left with 4 valid hawks which should yield 11 points

            int score = ScoringController.scoreHawkScoringCardA(p1.getPlayerBoardObject());
            assertEquals(11, score);
        }

        @Test
        void testHawkCardB() {
            Player p1 = new Player("Ton");
            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));


            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));


            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));


            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));


            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));


            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));

            //the board has 5 valid direct lines of sight shared amongst 4 hawks, which should yield 12 points

            int score = ScoringController.scoreHawkScoringCardB(p1.getPlayerBoardObject());
            assertEquals(12, score);

        }

        @Test
        void testHawkCardC() {
            Player p1 = new Player("Ton");
            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));

            //the board has 5 direct lines of sight, which should yield 3 * 5 = 15 points

            int score = ScoringController.scoreHawkScoringCardC(p1.getPlayerBoardObject());
            assertEquals(15, score);
        }
    }

    @Nested
    @DisplayName("Test Elk Cards")
    class TestElkCards {

        @Test
        void testElkCardA() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));

            //the largest line of elk is 4 (13 points). After removing this line, we are left with two lines of two elk each,
            //(5 points each), so all together we should yield 13 + 5 + 5 = 23 points

            int score = ScoringController.scoreElkScoringCardA(p1.getPlayerBoardObject());
            assertEquals(23, score);
        }

        @Test
        void testElkCardB() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));

            int score = ScoringController.scoreElkScoringCardB(p1.getPlayerBoardObject());
            assertEquals(21, score);
        }

        @Test
        //TODO
        void testElkCardC() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));

            int score = ScoringController.scoreElkScoringCardC(p1.getPlayerBoardObject());
            assertEquals(27, score);
        }
    }

    @Nested
    @DisplayName("Test Salmon Cards")
    class TestSalmonCards {

        @Test
        void testSalmonCardA() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));

            int score = ScoringController.scoreSalmonScoringCardA(p1.getPlayerBoardObject());
            //in the board we have two 'clumps' of salmon, of size 4 and size 5. The clump of size 5 is a valid run which yields 16 points
            //the clump with four salmon contains a salmon tile adjacent to three salmon tiles. We discard this salmon which leaves us
            //with two runs of size 2 and size 1. In total we get 16 + 5 + 2 = 23 points
            assertEquals(21, score);
        }

        @Test
        void testSalmonCardB() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));

            //in the board we have a run of 5, but since the max run size is 4, we only yield 11 points. We also have a cluster
            //of salmon with one of those salmon being adjacent to 3 salmon. After removing this salmon, we are left with a run
            //of size 2 and size 1 which yield 4 and 2 points respectively. We score 11 + 4 + 2 = 17 points in total
            int score = ScoringController.scoreSalmonScoringCardB(p1.getPlayerBoardObject());
            assertEquals(18, score);

        }

        @Test
        void testSalmonCardC() {
            Player p1 = new Player("Ton");

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,2));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,4));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,4));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,5));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,5));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,6));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,6));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(4,1));

            p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
            p1.getPlayerBoardObject().addNewTile(new HexCoordinate(5,1));
            p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
            p1.getPlayerBoardObject().addNewToken(new HexCoordinate(5,1));

            int score = ScoringController.scoreSalmonScoringCardC(p1.getPlayerBoardObject());
            assertEquals(23, score);
        }
    }




}