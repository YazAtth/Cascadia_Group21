package org.grouptwentyone.models;

public class Player {

    private static int playerIdCounter = 0;

    int playerId;
    final String userName;
//    int Score = 0;
    //int numOfNatureTokens = 0;
    //ArrayList<ArrayList<Tile>> playerBoard;

    PlayerBoard playerBoardObject = new PlayerBoard();

    public void setPlayerIdFromCounterAndIncrement() {
        this.playerId = playerIdCounter;
        playerIdCounter++;
    }

    public Player(String userName) {
        setPlayerIdFromCounterAndIncrement();
        this.userName = userName;
        //this.numOfNatureTokens = 0;
//        setupPlayerBoard();
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
        return this.playerBoardObject.getScore();
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
