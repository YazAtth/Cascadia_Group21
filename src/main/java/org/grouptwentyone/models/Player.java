/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

import org.jetbrains.annotations.NotNull;

public class Player implements Comparable<Player>{

    private static int playerIdCounter = 0;

    private int playerId;
    private final String userName;
//    int Score = 0;
    PlayerBoard playerBoardObject = new PlayerBoard();

    public void setPlayerIdFromCounterAndIncrement() {
        this.playerId = playerIdCounter;
        playerIdCounter++;
    }

    public Player(String userName) {
        setPlayerIdFromCounterAndIncrement();
        this.userName = userName;
        this.getPlayerBoardObject().setupPlayerBoard();
    }

    public int compareTo(@NotNull Player p) {
            return Integer.compare(this.getScore(), p.getScore());
    }


    public String toString() {
        return String.format("\nPlayer ID: %d\nUsername: %s\nScore: %d\n\n", getPlayerId(), getUserName(), getScore());
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getScore() {
        return this.playerBoardObject.getScore();
    }

    public PlayerBoard getPlayerBoardObject() {
        return this.playerBoardObject;
    }

    public void spendNatureToken() {
        this.playerBoardObject.spendNatureToken();
    }

    public int getNumOfNatureTokens() {
        return this.playerBoardObject.getNumOfNatureTokens();
    }
}
