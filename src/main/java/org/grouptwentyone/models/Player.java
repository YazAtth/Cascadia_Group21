package org.grouptwentyone.models;

public class Player {

    private static int playerIdCounter = 0;

    int playerId;
    final String userName;
    int Score = 0;
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


    public String toString() {
        return String.format("\nPlayer ID: %d\nUsername: %s\nScore: %d\n\n", getPlayerId(), getUserName(), getScore());
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return Score;
    }

    public PlayerBoard getPlayerBoardObject() {
        return playerBoardObject;
    }

    public void spendNatureToken() {
        playerBoardObject.spendNatureToken();
    }

    public int getNumOfNatureTokens() {
        return playerBoardObject.getNumOfNatureTokens();
    }
}
