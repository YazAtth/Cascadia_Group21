package org.grouptwentyone.dev;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.controllers.UserTerminationController;
import org.grouptwentyone.controllers.WildlifeTokensController;
import org.grouptwentyone.models.*;
import org.grouptwentyone.views.*;

import java.util.ArrayList;

import static org.grouptwentyone.controllers.HabitatTilesController.createBagOfHabitatTiles;
import static org.grouptwentyone.controllers.StarterHabitatTilesController.createBagOfStarterHabitatTiles;
import static org.grouptwentyone.controllers.WildlifeTokensController.createBagOfWildlifeTokens;

public class TestingSpaceColm {

    private static void testPlacingElkTokens() {
        CascadiaBot p1 = new CascadiaBot("Ton");

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(4, 1));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 2));
//
        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,3));

        p1.playTurn();
    }
    private static void testBotGame() {
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(new CascadiaBot("bot1"));
        playerList.add(new CascadiaBot("bot2"));
        PlayerManager playerManager = new PlayerManager(playerList);
        playerManager.shufflePlayerList();

        //remove habitat tiles depending on number of players
        int tilesToRemove = (((2-4)*-1)*20)+2;
        if (tilesToRemove > 0)
            HabitatTilesController.habitatTilesBag.subList(0, tilesToRemove).clear();

        Player activePlayer = playerManager.getFirstPlayer();

        while (StartGame.tilesRemain) {

            // If the user wants to quit the game: playTurn() returns false which breaks the loop
            // otherwise it ends with returning true.
            if (!activePlayer.playTurn()) {
                break;
            }

            //next player
            System.out.println("Moving to next player");
            activePlayer = playerManager.cycleToNextPlayer();
            GameUiView.printLargeSpace();
        }

        System.out.println("No tiles remain so play is finished, calculating player score...");

        playerManager.tallyUpAllScores();
        ScoreDisplayView.displayScorePage(playerManager);

        //end program
        //UserTerminationController.endProgram();
//        GameUiView.printLargeSpace();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        testBotGame();
        HabitatTilesController.habitatTilesBag = createBagOfHabitatTiles();
        StarterHabitatTilesController.starterHabitatTilesBag = createBagOfStarterHabitatTiles();
        WildlifeTokensController.wildlifeTokenBag = createBagOfWildlifeTokens();
        StartGame.selectedTiles = SelectionOptionsView.getFourHabitatTiles();
        StartGame.selectedTiles = SelectionOptionsView.getFourHabitatTiles();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime-startTime));

    }
}
