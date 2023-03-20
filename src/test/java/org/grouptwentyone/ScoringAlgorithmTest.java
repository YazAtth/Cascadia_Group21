package org.grouptwentyone;

import static org.junit.jupiter.api.Assertions.*;

import org.grouptwentyone.controllers.ScoringController;
import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.models.*;
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



}