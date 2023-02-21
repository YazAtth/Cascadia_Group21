package org.grouptwentyone.models;

import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.models.Exceptions.*;

import java.time.temporal.IsoFields;
import java.util.ArrayList;

import static java.lang.System.err;
import static org.grouptwentyone.controllers.StarterHabitatTilesController.getStarterTile;

public class Player {

    private static int playerIdCounter = 0;

    int playerId;
    final String userName;
    int Score = 0;
    int numOfNatureTokens = 0;
    ArrayList<ArrayList<Tile>> playerBoard;
    HabitatTile selectedTile;
    WildlifeToken selectedToken;

    public void setPlayerIdFromCounterAndIncrement() {
        this.playerId = playerIdCounter;
        playerIdCounter++;
    }

    public Player(String userName) {
        setPlayerIdFromCounterAndIncrement();
        this.userName = userName;
        this.numOfNatureTokens = 0;
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
        // changed this so that the habitat tile is the one selected in an earlier select command
        if (this.getSelectedTile().isNull()) {
            System.out.println("No tile has been selected. Please select a tile first.");
            return;
        }
        Tile newTile = new Tile(this.getSelectedTile(), newTileHexCoordinate);

        boolean isAdjacentToExistingTile = false;
        // Loop through entire scoreboard and compare the new tile with existing habitatTiles to see if the spot is occupied.
        for (int i=0; i<playerBoard.size()-1; i++) {
            for (int k=0; k<playerBoard.get(0).size()-1; k++) {
                Tile focusedTile = playerBoard.get(i).get(k);

                if (focusedTile.isActive() && focusedTile.getHexCoordinate().equals(newTileHexCoordinate)) {
                    throw new TilePlacedAtOccupiedPositionException(String.format("Tried to place %s onto existing %s", newTile, focusedTile));
                }

                // Checks if there's at least one existing tile that is adjacent to the new tile
                if (newTile.isAdjacentToTile(focusedTile) && !isAdjacentToExistingTile) {
                    isAdjacentToExistingTile = true;
                    System.out.printf("%s is adjacent to %s\n", newTile, focusedTile);
                }

            }
        }

        if (!isAdjacentToExistingTile) {
            throw new TileNotPlacedAdjacentlyException(String.format("Tried to place tile at %s of which there are no adjacent tiles", newTile));
        }
        this.playerBoard.get(newTile.getHexCoordinate().getX()).set(newTile.getHexCoordinate().getY(), newTile);

        //reset selectedTile to an empty tile
        this.setSelectedTile(new HabitatTile());
    }

    public void addNewToken(HexCoordinate newTokenHexCoordinate) {
        //check that player has selected a token
        if (this.getSelectedToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
            System.out.println("No token has been selected. Please select a token first.");
            return;
        }

        Tile focusedTile = playerBoard.get(newTokenHexCoordinate.getX()).get(newTokenHexCoordinate.getY());

        if (!focusedTile.isActive()) {
            throw new TokenPlacedAtEmptyPositionException("Tried to place Wildlife Token where there is no Habitat Tile");
        } else if (focusedTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY) {
            throw new TokenPlacedAtOccupiedPositionException("Tried to place Wildlife Token on an already occupied Habitat Tile");
        } else if (!focusedTile.getHabitatTile().getWildlifeTokenTypeList().contains(this.getSelectedToken().getWildlifeTokenType())){
            throw new TokenPlacedAtIllegalTileException("This type of Wildlife Token Type cannot be placed on this Habitat Tile");
        }
        //place token on selected tile
        focusedTile.getHabitatTile().setWildlifeToken(this.getSelectedToken());

        //check if habitat tile is keystone and if so, increase numOfNatureTokens by 1
        if (focusedTile.getHabitatTile().isKeystone()) this.addToNumOfNatureTokens();

        //reset selectedToken
        this.getSelectedToken().setWildlifeTokenType(WildlifeToken.WildlifeTokenType.EMPTY);
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

    public void setSelectedTile(HabitatTile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public void setSelectedToken(WildlifeToken selectedToken) {
        this.selectedToken = selectedToken;
    }

    public HabitatTile getSelectedTile() {
        return selectedTile;
    }

    public WildlifeToken getSelectedToken() {
        return selectedToken;
    }

    public void addToNumOfNatureTokens() {
        this.numOfNatureTokens++;
    }
}
