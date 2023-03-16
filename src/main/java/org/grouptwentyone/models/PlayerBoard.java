package org.grouptwentyone.models;

import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.models.Exceptions.*;

import java.util.ArrayList;

public class PlayerBoard {

    public enum PlayerBoardSide {LEFT, RIGHT, TOP, BOTTOM, MIDDLE}

    ArrayList<ArrayList<Tile>> playerBoard = new ArrayList<>();
    ArrayList<Tile> activeTiles = new ArrayList<>();
    Tile recentlyPlacedTile;
    HabitatTile selectedTile;
    WildlifeToken selectedToken;
    int numOfNatureTokens = 0;



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


    public void addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide playerBoardSide) {

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
                        newRow.add(new Tile(new HexCoordinate(k, i)));
                    }
                    playerBoard.add(k, newRow);
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
        int lastRow = this.getPlayerBoardAs2dArray().size()-1;
        int lastColumn = this.getPlayerBoardAs2dArray().get(0).size()-1;

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

    public ArrayList<PlayerBoard.PlayerBoardSide> getPartOfBoardCoordinateIsOn(HexCoordinate coordinate) {
        int lastRow = this.getPlayerBoardAs2dArray().size()-1;
        int lastColumn = this.getPlayerBoardAs2dArray().get(0).size()-1;
        int coordinateYVal = coordinate.getY();
        int coordinateXVal = coordinate.getX();

        // Checks if coordinate is on the board.
//        System.out.printf("y-val: %d > lastColumn: %d...x-val: %d > lastRow: %d\n", coordinateYVal, lastColumn, coordinateXVal, lastRow);
//        System.out.println(lastRow);
        if (coordinateXVal < 0 || coordinateXVal > lastRow || coordinateYVal < 0 || coordinateYVal > lastColumn) {
            throw new IllegalArgumentException("Entered coordinate in not on the board");
        }

        ArrayList<PlayerBoard.PlayerBoardSide> coordinateSideList = new ArrayList<>();

        if (coordinateXVal == 0) {
            coordinateSideList.add(PlayerBoard.PlayerBoardSide.TOP);
        }
        if (coordinateXVal == lastRow) {
            coordinateSideList.add(PlayerBoard.PlayerBoardSide.BOTTOM);
        }
        if (coordinateYVal == 0) {
            coordinateSideList.add(PlayerBoard.PlayerBoardSide.LEFT);
        }
        if (coordinateYVal == lastColumn) {
            coordinateSideList.add(PlayerBoard.PlayerBoardSide.RIGHT);
        }

        // If coordinate is not on any edges it must be in the middle.
        if (coordinateSideList.isEmpty()) {
            coordinateSideList.add(PlayerBoard.PlayerBoardSide.MIDDLE);
        }

        return coordinateSideList;
    }

    public void setupPlayerBoard() {
        //these for loops initialise the board with empty habitat tiles
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

        //add starterTile to activeTiles
        activeTiles.add(this.playerBoard.get(1).get(1));
        activeTiles.add(this.playerBoard.get(2).get(1));
        activeTiles.add(this.playerBoard.get(1).get(2));
    }

    public void addNewTile(HexCoordinate newTileHexCoordinate) {
        //legacy code
//        // changed this so that the habitat tile is the one selected in an earlier select command
//        if (this.getSelectedTile().isNull()) {
//            System.out.println("No tile has been selected. Please select a tile first.");
//            return;
//        }
        Tile newTile = new Tile(this.getSelectedTile(), newTileHexCoordinate);

        //checks if the spot on the playerboard is already occupied
        Tile focusedTile = playerBoard.get(newTileHexCoordinate.getX()).get(newTileHexCoordinate.getY());
        if (focusedTile.isActive()) {
            throw new TilePlacedAtOccupiedPositionException(String.format("Tried to place %s onto existing %s",
                    newTile, focusedTile));
        }

        //checks if spot is adjacent to a placed tile
        boolean isAdjacentToExistingTile = false;
        for (Tile currActiveTile : activeTiles) {
            if (newTile.isAdjacentToTile(currActiveTile)) {
                isAdjacentToExistingTile = true;
                break;
            }
        }

        if (!isAdjacentToExistingTile) {
            throw new TileNotPlacedAdjacentlyException(String.format("Tried to place tile at %s of which there are no adjacent tiles", newTile));
        }

        // Adds the new tile to board and then activeTiles
        this.playerBoard.get(newTile.getHexCoordinate().getX()).set(newTile.getHexCoordinate().getY(), newTile);
        activeTiles.add(newTile);

        // Checks to see if the returned array has a "middle" in it signifying it's not on the edge.
        ArrayList<PlayerBoard.PlayerBoardSide> tilePosition = this.getPartOfBoardCoordinateIsOn(newTile.getHexCoordinate());
        boolean isNewTileOnEdge = !tilePosition.contains(PlayerBoard.PlayerBoardSide.MIDDLE);
        System.out.printf("Is the new tile on the edge? %s\n", isNewTileOnEdge);

        if (isNewTileOnEdge) {
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.LEFT)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.LEFT);
            }
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.RIGHT)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.RIGHT);
            }
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.TOP)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.TOP);
            }
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.BOTTOM)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.BOTTOM);
            }
        }

        //makes reference to placed tile so user can choose to rotate tile
        this.recentlyPlacedTile = this.playerBoard.get(newTile.getHexCoordinate().getX()).get(newTile.getHexCoordinate().getY());

        //reset selectedTile to an empty tile
        this.setSelectedTile(new HabitatTile());
    }


    public void addNewToken(HexCoordinate newTokenHexCoordinate) {
        //legacy code
//        //check that player has selected a token
//        if (this.getSelectedToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
//            System.out.println("No token has been selected. Please select a token first.");
//            return;
//        }

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
        if (focusedTile.getHabitatTile().isKeystone()) {
            this.addToNumOfNatureTokens();
            System.out.println("You have just received 1 nature token for placing a token on a keystone tile");
        }

        //reset selectedToken
        this.setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));
    }


    public ArrayList<ArrayList<Tile>> getPlayerBoardAs2dArray() {
        return playerBoard;
    }
    public Tile getRecentlyPlacedTile() {
        return recentlyPlacedTile;
    }

    public WildlifeToken getSelectedToken() {
        return selectedToken;
    }

    public HabitatTile getSelectedTile() {
        return selectedTile;
    }
    public void setSelectedTile(HabitatTile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public void addToNumOfNatureTokens() {
        this.numOfNatureTokens++;
    }

    public int getNumOfNatureTokens() {
        return numOfNatureTokens;
    }

    public void spendNatureToken() {
        this.numOfNatureTokens--;
    }

    public void setSelectedToken(WildlifeToken selectedToken) {
        this.selectedToken = selectedToken;
    }

    public ArrayList<Tile> getActiveTiles() {
        return activeTiles;
    }
}
