/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

import org.grouptwentyone.controllers.ScoringController;
import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.models.Exceptions.*;
import org.grouptwentyone.views.BoardView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PlayerBoard {

    public enum PlayerBoardSide {LEFT, RIGHT, TOP, BOTTOM, MIDDLE}
    public enum BoardShift {LEFT_SHIFT, RIGHT_SHIFT, TOP_SHIFT, BOTTOM_SHIFT, NO_SHIFT}

    ArrayList<ArrayList<Tile>> playerBoard = new ArrayList<>();
    ArrayList<Tile> activeTiles = new ArrayList<>();
    TokenOptions tokenOptions = new TokenOptions();
    Tile recentlyPlacedTile;
    HabitatTile selectedTile;
    WildlifeToken selectedToken;
    int numOfNatureTokens = 0;
    ArrayList<BoardShift> lastBoardShift = new ArrayList<>(List.of(BoardShift.NO_SHIFT));
    boolean verbose = true;

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

    public ArrayList<PlayerBoard.PlayerBoardSide> getPartOfBoardCoordinateIsOn(HexCoordinate coordinate) {
        int lastRow = this.getPlayerBoardAs2dArray().size()-1;
        int lastColumn = this.getPlayerBoardAs2dArray().get(0).size()-1;
        int coordinateYVal = coordinate.getY();
        int coordinateXVal = coordinate.getX();

        // Checks if coordinate is on the board.
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

        //increment tiles' token types within tokenOptions
        for (HabitatTile tile : starterTile) {
            this.incrementTileTokenOption(tile.getWildlifeTokenTypeList());
        }
    }

    public void addNewTile(HexCoordinate newTileHexCoordinate) {
        Tile newTile = new Tile(this.getSelectedTile(), newTileHexCoordinate);

        //checks if the spot on the playerBoard is already occupied
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
        this.activeTiles.add(newTile);

        //increment tile's token types within tokenOptions
        this.incrementTileTokenOption(newTile.getHabitatTile().getWildlifeTokenTypeList());

        // Checks to see if the returned array has a "middle" in it signifying it's not on the edge.
        ArrayList<PlayerBoard.PlayerBoardSide> tilePosition = this.getPartOfBoardCoordinateIsOn(newTile.getHexCoordinate());
        boolean isNewTileOnEdge = !tilePosition.contains(PlayerBoard.PlayerBoardSide.MIDDLE);

        ArrayList<BoardShift> currentBoardShift = new ArrayList<>();
        if (isNewTileOnEdge) {
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.LEFT)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.LEFT);
                currentBoardShift.add(BoardShift.LEFT_SHIFT);
            }
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.RIGHT)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.RIGHT);
                currentBoardShift.add(BoardShift.RIGHT_SHIFT);
            }
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.TOP)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.TOP);
                currentBoardShift.add(BoardShift.TOP_SHIFT);
            }
            if (tilePosition.contains(PlayerBoard.PlayerBoardSide.BOTTOM)) {
                addPlayerBoardBuffer(PlayerBoard.PlayerBoardSide.BOTTOM);
                currentBoardShift.add(BoardShift.BOTTOM_SHIFT);
            }
        } else {
            currentBoardShift.add(BoardShift.NO_SHIFT);
        }

        this.setLastBoardShift(currentBoardShift);


        //makes reference to placed tile so user can choose to rotate tile
        this.recentlyPlacedTile = this.playerBoard.get(newTile.getHexCoordinate().getX()).get(newTile.getHexCoordinate().getY());

        //reset selectedTile to an empty tile
        this.setSelectedTile(new HabitatTile());
    }


    public void addNewToken(HexCoordinate newTokenHexCoordinate) {

        Tile focusedTile;
        try {
            focusedTile = this.playerBoard.get(newTokenHexCoordinate.getX()).get(newTokenHexCoordinate.getY());
        } catch (IndexOutOfBoundsException ex) {
            throw new TokenPlacedAtEmptyPositionException("Tried to place Wildlife Token where there is no Habitat Tile");
        }

        if (!focusedTile.isActive()) {
            throw new TokenPlacedAtEmptyPositionException("Tried to place Wildlife Token where there is no Habitat Tile");
        } else if (focusedTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY) {
            throw new TokenPlacedAtOccupiedPositionException(String.format("Tried to place Wildlife Token on an already occupied Habitat Tile at position %s", focusedTile.getHexCoordinate()));
        } else if (!focusedTile.getHabitatTile().getWildlifeTokenTypeList().contains(this.getSelectedToken().getWildlifeTokenType())){
            throw new TokenPlacedAtIllegalTileException("This type of Wildlife Token Type cannot be placed on this Habitat Tile");
        }
        //place token on selected tile
        focusedTile.getHabitatTile().setWildlifeToken(this.getSelectedToken());

        //decrement tile's token types within tokenOptions
        this.decrementTileTokenOption(focusedTile.getHabitatTile().getWildlifeTokenTypeList());

        //check if habitat tile is keystone and if so, increase numOfNatureTokens by 1
        if (focusedTile.getHabitatTile().isKeystone()) {
            this.addToNumOfNatureTokens();
            if (this.isVerbose())
                System.out.println("You have just received 1 nature token for placing a token on a keystone tile");
        }

        //reset selectedToken
        this.setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));
    }

    //increments the wildlife token types of the list's token(s) within the NumTokenOptions hashtable
    private void incrementTileTokenOption(ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList) {
        for (WildlifeToken.WildlifeTokenType tokenType : wildlifeTokenTypeList) {
            int value = this.tokenOptions.getNumOfTokenOption(tokenType);
            this.tokenOptions.setNumOfTokenOption(tokenType, ++value);
        }
    }

    //decrements the wildlife token types of the list's token(s) within the NumTokenOptions hashtable
    private void decrementTileTokenOption(ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenTypeList) {
        for (WildlifeToken.WildlifeTokenType tokenType : wildlifeTokenTypeList) {
            int value = this.tokenOptions.getNumOfTokenOption(tokenType);
            this.tokenOptions.setNumOfTokenOption(tokenType, --value);
        }
    }
    public ArrayList<Tile> getAdjacentTileList(Tile inputTile) {
        ArrayList<Tile> adjacentTileList = new ArrayList<>();

        for (ArrayList<Tile> row: getPlayerBoardAs2dArray()) {
            for (Tile tile: row) {
                if (inputTile.isAdjacentToTile(tile)) {
                    adjacentTileList.add(tile);
                }
            }
        }

        return adjacentTileList;
    }

    public ArrayList<Tile> getAdjacentNonEmptyTileList(Tile inputTile) {
        ArrayList<Tile> adjacentTileList = new ArrayList<>();

        for (ArrayList<Tile> row: getPlayerBoardAs2dArray()) {
            for (Tile tile: row) {
                boolean tileIsNotEmpty = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY;
                if (inputTile.isAdjacentToTile(tile) && tileIsNotEmpty) {
                    adjacentTileList.add(tile);
                }
            }
        }

        return adjacentTileList;
    }


    public int getScore() {

        int score = 0;

        ArrayList<ScoringCards.ScoreCard> scoreCardList = ScoringCards.getScoreCardsList();
        ScoringCards.ScoreCard bearScoringCard = scoreCardList.get(0);
        ScoringCards.ScoreCard foxScoringCard = scoreCardList.get(1);
        ScoringCards.ScoreCard elkScoringCard = scoreCardList.get(2);
        ScoringCards.ScoreCard hawkScoringCard = scoreCardList.get(3);
        ScoringCards.ScoreCard salmonScoringCard = scoreCardList.get(4);

        // Score Bear Card
        switch(bearScoringCard.getScoreType()) {
            case A -> score += ScoringController.scoreBearScoringCardA(this);
            case B -> score += ScoringController.scoreBearScoringCardB(this);
            case C -> score += ScoringController.scoreBearScoringCardC(this);
        }

        // Score Fox Card
        switch(foxScoringCard.getScoreType()) {
            case A -> score += ScoringController.scoreFoxScoringCardA(this);
            case B -> score += ScoringController.scoreFoxScoringCardB(this);
            case C -> score += ScoringController.scoreFoxScoringCardC(this);
        }

        // Score Elk Card
        switch(elkScoringCard.getScoreType()) {
            case A -> score += ScoringController.scoreElkScoringCardA(this);
            case B -> score += ScoringController.scoreElkScoringCardB(this);
            case C -> score += ScoringController.scoreElkScoringCardC(this);
        }

        // Score Hawk Card
        switch(hawkScoringCard.getScoreType()) {
            case A -> score += ScoringController.scoreHawkScoringCardA(this);
            case B -> score += ScoringController.scoreHawkScoringCardB(this);
            case C -> score += ScoringController.scoreHawkScoringCardC(this);
        }

        // Score Salmon Card
        switch (salmonScoringCard.getScoreType()) {
            case A -> score += ScoringController.scoreSalmonScoringCardA(this);
            case B -> score += ScoringController.scoreSalmonScoringCardB(this);
            case C -> score += ScoringController.scoreSalmonScoringCardC(this);
        }

        //add nature tokens to score, if any
        score += this.numOfNatureTokens;

        return score;

    }

    public Tile getTileByCoordinate(int row, int col) {

        return this.getPlayerBoardAs2dArray().get(row).get(col);
    }


    public ArrayList<ArrayList<Tile>> getPlayerBoardAs2dArray() {
        return this.playerBoard;
    }
    public Tile getRecentlyPlacedTile() {
        return this.recentlyPlacedTile;
    }

    public WildlifeToken getSelectedToken() {
        return this.selectedToken;
    }

    public HabitatTile getSelectedTile() {
        return this.selectedTile;
    }
    public void setSelectedTile(HabitatTile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public void addToNumOfNatureTokens() {
        this.numOfNatureTokens++;
    }

    public int getNumOfNatureTokens() {
        return this.numOfNatureTokens;
    }

    public void spendNatureToken() {
        this.numOfNatureTokens--;
    }

    public void setSelectedToken(WildlifeToken selectedToken) {
        this.selectedToken = selectedToken;
    }

    public ArrayList<Tile> getActiveTiles() {
        return this.activeTiles;
    }

    public boolean canPlaceToken() {
        return this.tokenOptions.getNumOfTokenOption(this.selectedToken.wildlifeTokenType) > 0;
    }

    //nested class that will store the number of options for each type of token on a player's board
    protected class TokenOptions {
        Hashtable<WildlifeToken.WildlifeTokenType, Integer> numTokenOptions;

        public TokenOptions() {
            this.numTokenOptions = new Hashtable<>();
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.BEAR, 0);
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.ELK, 0);
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.FOX, 0);
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.SALMON, 0);
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.HAWK, 0);
        }

        //constructor for copy by value
        public TokenOptions(TokenOptions oldTokenOptions) {
            this.numTokenOptions = new Hashtable<>();
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.BEAR, oldTokenOptions.getNumOfTokenOption(WildlifeToken.WildlifeTokenType.BEAR));
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.ELK, oldTokenOptions.getNumOfTokenOption(WildlifeToken.WildlifeTokenType.ELK));
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.FOX, oldTokenOptions.getNumOfTokenOption(WildlifeToken.WildlifeTokenType.FOX));
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.SALMON, oldTokenOptions.getNumOfTokenOption(WildlifeToken.WildlifeTokenType.SALMON));
            this.numTokenOptions.put(WildlifeToken.WildlifeTokenType.HAWK, oldTokenOptions.getNumOfTokenOption(WildlifeToken.WildlifeTokenType.HAWK));
        }

        public Integer getNumOfTokenOption(WildlifeToken.WildlifeTokenType tokenType) {
            return this.numTokenOptions.get(tokenType);
        }

        private void setNumOfTokenOption(WildlifeToken.WildlifeTokenType tokenType, Integer value) {
            this.numTokenOptions.replace(tokenType, value);
        }
    }
    public ArrayList<Tile> getConnectedSameTilesEast(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesEast = new ArrayList<>();

        boolean hasElkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.ELK;

        int moveOneEast = 0;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();

        while(playerBoard.getTileByCoordinate(xCoord, yCord + moveOneEast).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
                sameTilesEast.add(playerBoard.getTileByCoordinate(xCoord, yCord + moveOneEast));
            moveOneEast++;
        }
        return sameTilesEast;
    }
    public ArrayList<Tile> getConnectedSameTilesSouthEast(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesSouthEast = new ArrayList<>();

        int moveOneEast = 0;
        int moveOneSouth = 0;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();

        while(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord + moveOneEast).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesSouthEast.add(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord + moveOneEast));
            if (((xCoord + moveOneSouth) % 2 == 0)) {
                moveOneEast++;
            }
            moveOneSouth++;

        }
        return sameTilesSouthEast;
    }

    public ArrayList<Tile> getConnectedSameTilesSouthWest(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesSouthEast = new ArrayList<>();

        int moveOneSouth = 0;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();

        while(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesSouthEast.add(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord));
            if ((xCoord + moveOneSouth) % 2 == 1 && yCord > 0) {
                yCord--;
            }
            moveOneSouth++;

        }
        return sameTilesSouthEast;
    }

    ////////////////////////

    public ArrayList<Tile> getConnectedSameTilesEastWeight(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesEast = new ArrayList<>();
        sameTilesEast.add(tile);

        int moveOneEast = 1;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();

        while(playerBoard.getTileByCoordinate(xCoord, yCord + moveOneEast).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesEast.add(playerBoard.getTileByCoordinate(xCoord, yCord + moveOneEast));
            moveOneEast++;
        }
        return sameTilesEast;
    }
    public ArrayList<Tile> getConnectedSameTilesSouthEastWeight(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesSouthEast = new ArrayList<>();
        sameTilesSouthEast.add(tile);

        int moveOneEast = 0;
        int moveOneSouth = 1;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();
        if (((xCoord + moveOneSouth) % 2 == 1)) {
            moveOneEast++;
        }

        while(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord + moveOneEast).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesSouthEast.add(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord + moveOneEast));
            moveOneSouth++;
            if (((xCoord + moveOneSouth) % 2 == 1)) {
                moveOneEast++;
            }

        }
        return sameTilesSouthEast;
    }

    public ArrayList<Tile> getConnectedSameTilesSouthWestWeight(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesSouthWest = new ArrayList<>();
        sameTilesSouthWest.add(tile);

        int moveOneSouth = 1;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();
        if ((xCoord + moveOneSouth) % 2 == 1 && yCord > 0) {
            yCord--;
        }

        while(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesSouthWest.add(playerBoard.getTileByCoordinate(xCoord + moveOneSouth, yCord));
            if ((xCoord + moveOneSouth) % 2 == 1 && yCord > 0) {
                yCord--;
            }
            moveOneSouth++;

        }
        return sameTilesSouthWest;
    }

    public ArrayList<Tile> getConnectedSameTilesWestWeight(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesWest = new ArrayList<>();
        sameTilesWest.add(tile);

        int moveOneWest = -1;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();

        while(playerBoard.getTileByCoordinate(xCoord, yCord + moveOneWest).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesWest.add(playerBoard.getTileByCoordinate(xCoord, yCord + moveOneWest));
            moveOneWest--;
        }
        return sameTilesWest;
    }

    public ArrayList<Tile> getConnectedSameTilesNorthWestWeight(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesNorthWest = new ArrayList<>();
        sameTilesNorthWest.add(tile);

        int moveOneWest = 0;
        int moveOneNorth = -1;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();
        if (((xCoord + moveOneNorth) % 2 == 0)) {
            moveOneWest--;
        }

        while(playerBoard.getTileByCoordinate(xCoord + moveOneNorth, yCord + moveOneWest).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesNorthWest.add(playerBoard.getTileByCoordinate(xCoord + moveOneNorth, yCord + moveOneWest));
            moveOneNorth--;
            if ((xCoord + moveOneNorth) % 2 == 0) {
                moveOneWest--;
            }

        }
        return sameTilesNorthWest;
    }

    public ArrayList<Tile> getConnectedSameTilesNorthEastWeight(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesNorthEast = new ArrayList<>();
        sameTilesNorthEast.add(tile);

        int moveOneNorth = -1;
        int xCoord = tile.getHexCoordinate().getX();
        int yCord =  tile.getHexCoordinate().getY();
        if ((xCoord + moveOneNorth) % 2 == 1 && yCord > 0) {
            yCord++;
        }

        while(playerBoard.getTileByCoordinate(xCoord + moveOneNorth, yCord).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                == WildlifeToken.WildlifeTokenType.ELK) {
            sameTilesNorthEast.add(playerBoard.getTileByCoordinate(xCoord + moveOneNorth, yCord));
            moveOneNorth--;
            if ((xCoord + moveOneNorth) % 2 == 1 && yCord > 0) {
                yCord++;
            }

        }
        return sameTilesNorthEast;
    }

    public ArrayList<Tile> getPlacedTilesList() {
        ArrayList<Tile> placedTileList = new ArrayList<>();
        for (ArrayList<Tile> row: this.getPlayerBoardAs2dArray()) {
            for (Tile tile: row) {
                if (!tile.getHabitatTile().isNull()) {
                    placedTileList.add(tile);
                }
            }
        }

        return placedTileList;
    }

    public ArrayList<Tile> getAllAdjacentPositions(Tile o) {
        ArrayList<Tile> output = new ArrayList<>();

        for (ArrayList<Tile> row: this.getPlayerBoardAs2dArray()) {
            for (Tile tile: row) {
                if (tile.getHexCoordinate().isAdjacentToHexCoordinate(o.getHexCoordinate())) {
                    output.add(tile);
                }
            }
        }

        return output;
    }

    // Gets us a list of options for where we can place the tiles
    public ArrayList<Tile> getPlaceableTileOptionList() {
        ArrayList<Tile> placeableTileOptionsList = new ArrayList<>();
        ArrayList<Tile> placedTileList = this.getPlacedTilesList();

        for (Tile tile: placedTileList) {
            ArrayList<Tile> adjacentTiles = this.getAllAdjacentPositions(tile);

            for (Tile adjacentTile: adjacentTiles) {

                boolean alreadyInOutputList = placeableTileOptionsList.contains(adjacentTile);
                boolean tileIsAlreadyPlaced = placedTileList.contains(adjacentTile);


                if (!alreadyInOutputList && !tileIsAlreadyPlaced) {
                    placeableTileOptionsList.add(adjacentTile);
                }
            }
        }
        return placeableTileOptionsList;
    }


    public PlayerBoard getDuplicate() {

        // Copies the code by value instead of reference.
        PlayerBoard newPlayerBoard = new PlayerBoard();
        ArrayList<ArrayList<Tile>> newPlayerBoardAs2dArray = new ArrayList<>();
        for (ArrayList<Tile> row: this.getPlayerBoardAs2dArray()) {

            ArrayList<Tile> newRow = new ArrayList<>();
            for (Tile tile: row) {
                HabitatTile oldHabitatTile = tile.getHabitatTile();
                HabitatTile newHabitatTile =  new HabitatTile(oldHabitatTile);
                newRow.add(new Tile(newHabitatTile, tile));
            }
            newPlayerBoardAs2dArray.add(newRow);
        }
        newPlayerBoard.setPlayerBoard(newPlayerBoardAs2dArray);


        //TODO: Not duplicating correctly below
        ArrayList<Tile> newActiveTiles = new ArrayList<>();
        for (Tile tile: this.getActiveTiles()) {
            HabitatTile oldHabitatTile = tile.getHabitatTile();
            HabitatTile newHabitatTile = new HabitatTile(oldHabitatTile);

            Tile newTile = new Tile(newHabitatTile, tile);

            newActiveTiles.add(newTile);
        }
        newPlayerBoard.setActiveTiles(newActiveTiles);

        if (this.getRecentlyPlacedTile() != null) {
            Tile oldRecentlyPlacedTile = this.getRecentlyPlacedTile();
            HabitatTile oldHabitatTile = oldRecentlyPlacedTile.getHabitatTile();
            HabitatTile newHabitatTile = new HabitatTile(oldHabitatTile);
            Tile newRecentlyPlacedTile = new Tile(newHabitatTile, oldRecentlyPlacedTile);
            newPlayerBoard.setRecentlyPlacedTile(newRecentlyPlacedTile);
        }

        HabitatTile oldSelectedTile = this.getSelectedTile();
        if (oldSelectedTile != null) {
            HabitatTile newSelectedTile = new HabitatTile(oldSelectedTile);
            newPlayerBoard.setSelectedTile(newSelectedTile);
        }

        WildlifeToken oldWildlifeToken = this.getSelectedToken();
        if (oldWildlifeToken != null) {
            WildlifeToken newWildlifeToken = new WildlifeToken(oldWildlifeToken.getWildlifeTokenType());
            newPlayerBoard.setSelectedToken(newWildlifeToken);
        }

        newPlayerBoard.setTokenOptions(new TokenOptions(this.getTokenOptions()));
        newPlayerBoard.setLastBoardShift(new ArrayList<>(this.getLastBoardShift()));
        newPlayerBoard.setNumOfNatureTokens(this.getNumOfNatureTokens());
        newPlayerBoard.setVerbose(this.isVerbose());




        return newPlayerBoard;
    }

    public ArrayList<ArrayList<Tile>> getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(ArrayList<ArrayList<Tile>> playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void setActiveTiles(ArrayList<Tile> activeTiles) {
        this.activeTiles = activeTiles;
    }

    public TokenOptions getTokenOptions() {
        return tokenOptions;
    }

    public void setTokenOptions(TokenOptions tokenOptions) {
        this.tokenOptions = tokenOptions;
    }

    public void setRecentlyPlacedTile(Tile recentlyPlacedTile) {
        this.recentlyPlacedTile = recentlyPlacedTile;
    }

    public void setNumOfNatureTokens(int numOfNatureTokens) {
        this.numOfNatureTokens = numOfNatureTokens;
    }



    @Override
    public boolean equals(Object inputO) {

        PlayerBoard o;

        if (!(inputO instanceof PlayerBoard)) {
            return false;
        } else {
            o = (PlayerBoard) inputO;
        }


//        System.out.printf("Equivalence: %s\n", this.getActiveTiles().equals(o.getActiveTiles()));
        System.out.printf("Equivalence: %s\n", this.getActiveTiles().containsAll(o.getActiveTiles()) && o.getActiveTiles().containsAll(this.getActiveTiles()));

        System.out.printf("to1: %s\nto2: %s\n\n",this.getActiveTiles(), o.getActiveTiles());

        // Recently placed tiles, selected tiles and selected tokens can be null so we have to deal with those conditions separately
        boolean isRecentlyPlacedTilesEqual;
        if (this.getRecentlyPlacedTile() == null || o.getRecentlyPlacedTile() == null) {
            isRecentlyPlacedTilesEqual = this.getRecentlyPlacedTile() == null && o.getRecentlyPlacedTile() == null;
        } else {
            isRecentlyPlacedTilesEqual = this.getRecentlyPlacedTile().equals(o.getRecentlyPlacedTile());
        }

        boolean isSelectedTilesEqual;
        if (this.getSelectedTile() == null || o.getSelectedTile() == null) {
            isSelectedTilesEqual = this.getSelectedTile() == null && o.getSelectedTile() == null;
        } else {
            isSelectedTilesEqual = this.getSelectedTile().equals(o.getSelectedTile());
        }

        boolean isSelectedTokensEqual;
        if (this.getSelectedToken() == null || o.getSelectedToken() == null) {
            isSelectedTokensEqual = this.getSelectedToken() == null && o.getSelectedToken() == null;
        } else {
            isSelectedTokensEqual = this.getSelectedToken().equals(o.getSelectedToken());
        }


        return (this.getPlayerBoard().containsAll(o.getPlayerBoard()) && o.getPlayerBoard().containsAll(this.getPlayerBoard())) &&
                (this.getActiveTiles().containsAll(o.getActiveTiles()) && o.getActiveTiles().containsAll(this.getActiveTiles())) &&
                (this.getTokenOptions().equals(o.getTokenOptions())) &&
                isRecentlyPlacedTilesEqual &&
                isSelectedTilesEqual &&
                isSelectedTokensEqual &&
                (this.getNumOfNatureTokens() == o.getNumOfNatureTokens());

    }

    public ArrayList<BoardShift> getLastBoardShift() {
        return lastBoardShift;
    }

    public void setLastBoardShift(ArrayList<BoardShift> lastBoardShift) {
        this.lastBoardShift = lastBoardShift;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public String toString() {
        return BoardView.displayTiles(this);
    }
}
