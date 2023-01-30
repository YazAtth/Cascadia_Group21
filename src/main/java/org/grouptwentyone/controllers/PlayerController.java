package org.grouptwentyone.controllers;

import org.grouptwentyone.models.Player;

import java.util.ArrayList;

public class PlayerController {

    ArrayList<Player> playerList;
    int playerIndexCycle = 0;



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






    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }
}
