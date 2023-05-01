/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone;

import org.grouptwentyone.controllers.*;
import org.grouptwentyone.models.*;
import org.grouptwentyone.views.*;

import java.util.ArrayList;

public class StartGame {
    public static ArrayList<HabitatTile> selectedTiles = SelectionOptionsView.getFourHabitatTiles();

    public static ArrayList<WildlifeToken> selectedTokens = SelectionOptionsView.getFourWildlifeTokens();

    public static boolean tilesRemain = true;

    public static void start() {

        GameUiView.printLargeSpace();

        //print opening logo
        LandingPageView.show();
        GameUiView.printLargeSpace();

        CustomPair<Integer, Integer> numOfPlayersAndBots = GameSetupView.getNumberOfPlayersFromUser();
        ArrayList<Player> playerList = GameSetupView.getPlayerInformationFromUser(numOfPlayersAndBots);

        PlayerManager playerManager = new PlayerManager(playerList);
        playerManager.shufflePlayerList();

        GameUiView.printPageBorder();
        GameUiView.printScoreCardRules();
        PlayerBoardInstructionsView.displayBoardInstructions();
        GameSetupView.displayPlayerOrder(playerList);

        //remove habitat tiles depending on number of players
        int totalPlayers = numOfPlayersAndBots.getField1() + numOfPlayersAndBots.getField2();
        int tilesToRemove = (((totalPlayers-4)*-1)*20)+2;
        if (tilesToRemove > 0)
            HabitatTilesController.habitatTilesBag.subList(0, tilesToRemove).clear();

        Player activePlayer = playerManager.getFirstPlayer();


        while (tilesRemain) {

            // If the user wants to quit the game: playTurn() returns false which breaks the loop
            // otherwise it ends with returning true.
            if (!activePlayer.playTurn()) {
                break;
            }

            //next player
            activePlayer = playerManager.cycleToNextPlayer();
        }

        System.out.println("No tiles remain so play is finished, calculating player score...");

        playerManager.tallyUpAllScores();
        ScoreDisplayView.displayScorePage(playerManager);

        //end program
        UserTerminationController.endProgram();
        GameUiView.printLargeSpace();
    }
}
