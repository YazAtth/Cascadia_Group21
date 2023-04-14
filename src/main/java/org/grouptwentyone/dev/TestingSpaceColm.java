package org.grouptwentyone.dev;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.controllers.UserTerminationController;
import org.grouptwentyone.controllers.WildlifeTokensController;
import org.grouptwentyone.models.CascadiaBot;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.models.PlayerManager;
import org.grouptwentyone.views.*;

import java.util.ArrayList;

import static org.grouptwentyone.controllers.WildlifeTokensController.createBagOfWildlifeTokens;

public class TestingSpaceColm {
    public static int maxScore = -1;
    public static String maxName = "";
    public static int minScore = 1000;
    public static String minName = "";
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
            //System.out.println("Moving to next player");
            activePlayer = playerManager.cycleToNextPlayer();
            //GameUiView.printLargeSpace();
        }

        System.out.println("No tiles remain so play is finished, calculating player score...");

        playerManager.tallyUpAllScores();
        ScoreDisplayView.displayScorePage(playerManager);

        //end program
        //UserTerminationController.endProgram();
        //GameUiView.printLargeSpace();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            StartGame.tilesRemain = true;
            WildlifeTokensController.wildlifeTokenBag = createBagOfWildlifeTokens();
            HabitatTilesController.habitatTilesBag = HabitatTilesController.createBagOfHabitatTiles();
            StartGame.selectedTiles = SelectionOptionsView.getFourHabitatTiles();
            StartGame.selectedTokens = SelectionOptionsView.getFourWildlifeTokens();
            StarterHabitatTilesController.starterHabitatTilesBag = StarterHabitatTilesController.createBagOfStarterHabitatTiles();
            testBotGame();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime-startTime));
        System.out.println("Max Score = " + maxScore + ", name: " + maxName);
        System.out.println("Min Score = " + minScore + ", name: " + minName);
    }
}
