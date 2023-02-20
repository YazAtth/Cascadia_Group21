package org.grouptwentyone.models;

import org.grouptwentyone.controllers.HabitatTilesController;

import java.util.ArrayList;

import static org.grouptwentyone.controllers.StarterHabitatTilesController.getStarterTile;

public class Player {

    private static int playerIdCounter = 0;

    int playerId;
    final String userName;
    int Score = 0;
    int numOfNatureTokens = 0;
    ArrayList<ArrayList<Tile>> playerBoard;


    public void setPlayerIdFromCounterAndIncrement() {
        this.playerId = playerIdCounter;
        playerIdCounter++;
    }

    public Player(String userName) {
        setPlayerIdFromCounterAndIncrement();
        this.userName = userName;
        setupPlayerBoard();
    }

    private void setupPlayerBoard() {
        //these for loops initialise the board with null habitat tiles
        this.playerBoard = new ArrayList<>();
        int sizeX = 4, sizeY = 4;
        for (int i = 0; i < sizeX; i++) {
            this.playerBoard.add(new ArrayList<>());
        }
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                this.playerBoard.get(i).add(j, new Tile(new HexCoordinate(i, j)));
            }
        }

        //insert random starter tile into players board
        ArrayList<HabitatTile> starterTile = getStarterTile();

        this.playerBoard.get(1).set(1, new Tile(starterTile.get(0), new HexCoordinate(1, 1)));
        //note: a hex coordinate of [1, 2] is set at array index [2, 1]
        this.playerBoard.get(2).set(1, new Tile(starterTile.get(1), new HexCoordinate(1, 2)));
        this.playerBoard.get(1).set(2, new Tile(starterTile.get(2), new HexCoordinate(2, 1)));

    }


    public void addNewTile(HexCoordinate newTileHexCoordinate) {
        // .get() is set to "3" idk what that means but it felt right lol
        Tile newTile = new Tile(HabitatTilesController.habitatTilesBag.get(3), newTileHexCoordinate);
        this.playerBoard.get(newTile.getHexCoordinate().getX()).set(newTile.getHexCoordinate().getY(), newTile);
    }

    public String toString() {
        return String.format("\nPlayer ID: %d\nUsername: %s\nScore: %d\n\n", getPlayerId(), getUserName(), getScore());
    }

    public ArrayList<ArrayList<Tile>> getPlayerBoard() {
        return playerBoard;
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

    public int getNumOfNatureTokens() {
        return numOfNatureTokens;
    }



}
