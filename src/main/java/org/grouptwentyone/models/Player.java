package org.grouptwentyone.models;

import org.grouptwentyone.models.Exceptions.*;

import java.util.ArrayList;

import org.grouptwentyone.controllers.StarterHabitatTilesController;

public class Player {
    public enum PlayerBoardSide {LEFT, RIGHT, TOP, BOTTOM, MIDDLE}

    private static int playerIdCounter = 0;

    int playerId;
    final String userName;
    int Score = 0;
    int numOfNatureTokens = 0;
    ArrayList<ArrayList<Tile>> playerBoard;
    HabitatTile selectedTile;
    Tile recentlyPlacedTile;
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

    public void printPlayerBoard() {

//        System.out.println(playerBoard.size());
//        System.out.println(playerBoard.get(0).size());

        for (int i=0; i<playerBoard.size(); i++) {
            for (int k=0; k<playerBoard.get(0).size(); k++) {
//                System.out.printf("%d, %d\n", i, k);
                System.out.printf("[%s]", playerBoard.get(i).get(k));
            }
            System.out.println();
        }
    }

    public void addPlayerBoardBuffer(PlayerBoardSide playerBoardSide) {

        ArrayList<Tile> newRow = new ArrayList<>();

        // Add empty tiles for the specified row/column based on user input of side.
        switch(playerBoardSide) {
            case LEFT:
                for (int i=0; i<playerBoard.size(); i++) {
                    for (int k=0; k<playerBoard.get(0).size(); k++) {
                        int oldValue = playerBoard.get(i).get(k).getHexCoordinate().getY();
                        playerBoard.get(i).get(k).getHexCoordinate().setY(oldValue+1);
                    }
                }

                for (int i=0; i<playerBoard.size(); i++) {
                    playerBoard.get(i).add(0, new Tile(new HexCoordinate(i, 0)));
                }
                break;

            case TOP:
                // In this case: 2 lines of buffer space are added
                // This is because of the way every second line is shifted to the left in the hex-coordinate system.
                // Adding two lines ensures the parity of the system is maintained, so we don't have to care about the left shifting.

                for (int i=0; i<playerBoard.size(); i++) {
                    for (int k = 0; k < playerBoard.get(0).size(); k++) {
                        int oldValue = playerBoard.get(i).get(k).getHexCoordinate().getX();
                        playerBoard.get(i).get(k).getHexCoordinate().setX(oldValue+2);
                    }
                }

                for (int k=0; k<2; k++) {
                    newRow = new ArrayList<>();
                    for (int i = 0; i < playerBoard.get(0).size(); i++) {
                        newRow.add(new Tile(new HexCoordinate(0, i)));
                    }
                    playerBoard.add(0, newRow);
                }
                break;

            case BOTTOM:
                int indexOfLastRow = playerBoard.size();
                for (int i=0; i<playerBoard.get(0).size(); i++) {
                    newRow.add(new Tile(new HexCoordinate(indexOfLastRow, i)));
                }
                playerBoard.add(indexOfLastRow, newRow);
                break;

            case RIGHT:
                int indexOfLastColumn = playerBoard.get(0).size();
                for (int i=0; i<playerBoard.size(); i++) {
                    playerBoard.get(i).add(indexOfLastColumn, new Tile(new HexCoordinate(i, indexOfLastColumn)));
                }
                break;
            default:
                throw new IllegalArgumentException("Did not enter a valid \"playerBoardSide\" type");
        }

    }

    public boolean isCoordinateOnBoardEdge(HexCoordinate coordinate) {
        int lastRow = this.getPlayerBoard().size()-1;
        int lastColumn = this.getPlayerBoard().get(0).size()-1;

//        System.out.printf("%d and %d\n", lastRow-1, lastColumn-1);

        int coordinateYVal = coordinate.getY();
        int coordinateXVal = coordinate.getX();

        boolean isCoordinateOnFirstOrLastRow = coordinateXVal == 0 || coordinateXVal == lastRow;
        boolean isCoordinateOnFirstOrLastColumn = coordinateYVal == 0 || coordinateYVal == lastColumn;


//        System.out.printf("y-val: %d > lastRow: %d...x-val: %d > lastColumn: %d\n", coordinateYVal, lastRow, coordinateXVal, lastColumn);
        if (coordinateXVal < 0 || coordinateXVal > lastRow || coordinateYVal < 0 || coordinateYVal > lastColumn) {
            throw new IllegalArgumentException("Entered coordinate in not on the board");
        }

        return isCoordinateOnFirstOrLastRow || isCoordinateOnFirstOrLastColumn;
    }

    public ArrayList<PlayerBoardSide> getPartOfBoardCoordinateIsOn(HexCoordinate coordinate) {
        int lastRow = this.getPlayerBoard().size()-1;
        int lastColumn = this.getPlayerBoard().get(0).size()-1;
        int coordinateYVal = coordinate.getY();
        int coordinateXVal = coordinate.getX();

        // Checks if coordinate is on the board.
//        System.out.printf("y-val: %d > lastColumn: %d...x-val: %d > lastRow: %d\n", coordinateYVal, lastColumn, coordinateXVal, lastRow);
//        System.out.println(lastRow);
        if (coordinateXVal < 0 || coordinateXVal > lastRow || coordinateYVal < 0 || coordinateYVal > lastColumn) {
            throw new IllegalArgumentException("Entered coordinate in not on the board");
        }

        ArrayList<PlayerBoardSide> coordinateSideList = new ArrayList<>();

        if (coordinateXVal == 0) {
            coordinateSideList.add(PlayerBoardSide.TOP);
        }
        if (coordinateXVal == lastRow) {
            coordinateSideList.add(PlayerBoardSide.BOTTOM);
        }
        if (coordinateYVal == 0) {
            coordinateSideList.add(PlayerBoardSide.LEFT);
        }
        if (coordinateYVal == lastColumn) {
            coordinateSideList.add(PlayerBoardSide.RIGHT);
        }

        // If coordinate is not on any edges it must be in the middle.
        if (coordinateSideList.isEmpty()) {
            coordinateSideList.add(PlayerBoardSide.MIDDLE);
        }

        return coordinateSideList;
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
        ArrayList<HabitatTile> starterTile = StarterHabitatTilesController.getStarterTile();

        this.playerBoard.get(1).set(1, new Tile(starterTile.get(0), new HexCoordinate(1, 1)));
        //note: a hex coordinate of [1, 2] is set at array index [2, 1]
        this.playerBoard.get(2).set(1, new Tile(starterTile.get(1), new HexCoordinate(2, 1)));
        this.playerBoard.get(1).set(2, new Tile(starterTile.get(2), new HexCoordinate(1, 2)));

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
//                    System.out.printf("%s is adjacent to %s\n", newTile, focusedTile);
                }

            }
        }

        if (!isAdjacentToExistingTile) {
            throw new TileNotPlacedAdjacentlyException(String.format("Tried to place tile at %s of which there are no adjacent tiles", newTile));
        }

        // Adds the new tile
        this.playerBoard.get(newTile.getHexCoordinate().getX()).set(newTile.getHexCoordinate().getY(), newTile);

        // Checks to see if the returned array has a "middle" in it signifying it's not on the edge.
        ArrayList<PlayerBoardSide> tilePosition = this.getPartOfBoardCoordinateIsOn(newTile.getHexCoordinate());
        boolean isNewTileOnEdge = !tilePosition.contains(PlayerBoardSide.MIDDLE);
        System.out.printf("Is the new tile on the edge? %s\n", isNewTileOnEdge);

        if (isNewTileOnEdge) {
            if (tilePosition.contains(PlayerBoardSide.LEFT)) {
                addPlayerBoardBuffer(PlayerBoardSide.LEFT);
            }
            if (tilePosition.contains(PlayerBoardSide.RIGHT)) {
                addPlayerBoardBuffer(PlayerBoardSide.RIGHT);
            }
            if (tilePosition.contains(PlayerBoardSide.TOP)) {
                addPlayerBoardBuffer(PlayerBoardSide.TOP);
            }
            if (tilePosition.contains(PlayerBoardSide.BOTTOM)) {
                addPlayerBoardBuffer(PlayerBoardSide.BOTTOM);
            }
        }

        //makes reference to placed tile so user can choose to rotate tile
        for (int i = 0; i < playerBoard.size()-1; i++) {
            for (int j = 0; j < playerBoard.get(0).size()-1; j++) {
                if (playerBoard.get(i).get(j) == newTile) {
                    this.recentlyPlacedTile = playerBoard.get(i).get(j);
                    break;
                }
            }
        }

        //reset selectedTile to an empty tile
        this.setSelectedTile(new HabitatTile());
    }

    public void addNewToken(HexCoordinate newTokenHexCoordinate) {
        //check that player has selected a token
        if (this.getSelectedToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
            System.out.println("No token has been selected. Please select a token first.");
            return;
        }

        Tile focusedTile;
        try {
            focusedTile = playerBoard.get(newTokenHexCoordinate.getX()).get(newTokenHexCoordinate.getY());
        } catch (IndexOutOfBoundsException ex) {
            throw new TokenPlacedAtEmptyPositionException("Tried to place Wildlife Token where there is no Habitat Tile");
        }

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
        this.setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));
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

    public Tile getRecentlyPlacedTile() {
        return recentlyPlacedTile;
    }

    public WildlifeToken getSelectedToken() {
        return selectedToken;
    }

    public void addToNumOfNatureTokens() {
        this.numOfNatureTokens++;
    }
}
