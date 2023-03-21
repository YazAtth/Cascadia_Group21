package org.grouptwentyone.controllers;

import org.grouptwentyone.models.Player;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerController {

    private ArrayList<Player> playerList;
    private int playerIndexCycle = 0;



    public PlayerController(ArrayList<Player> inputPlayerList) {
        setPlayerList(inputPlayerList);
    }


    public Player getFirstPlayer() {
        return playerList.get(0);
    }

    public Player cycleToNextPlayer() {

        // Makes index cycle instead of going out of bounds
        if (playerIndexCycle == playerList.size()-1) {
            playerIndexCycle = 0;
        } else {
            playerIndexCycle++;
        }


        return playerList.get(playerIndexCycle);
    }

    public void shufflePlayerList() {
        Collections.shuffle(playerList);
    }


    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void tallyUpAllScores() {
        for (Player player: getPlayerList()) {
            player.getPlayerBoardObject().tallyUpScore();
        }
    }

}
