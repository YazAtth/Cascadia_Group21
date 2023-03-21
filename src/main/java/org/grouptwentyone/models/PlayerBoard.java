package org.grouptwentyone.models;

import org.grouptwentyone.controllers.ScoringController;
import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.models.Exceptions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashMap;

public class PlayerBoard {

    public enum PlayerBoardSide {LEFT, RIGHT, TOP, BOTTOM, MIDDLE}

    ArrayList<ArrayList<Tile>> playerBoard = new ArrayList<>();
    TokenOptions tokenOptions = new TokenOptions();
    Tile recentlyPlacedTile;
    HabitatTile selectedTile;
    WildlifeToken selectedToken;
    int numOfNatureTokens = 0;
    public int score = 0;



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

        //increment tiles' token types within tokenOptions
        for (HabitatTile tile : starterTile) {
            this.incrementTileTokenOption(tile.getWildlifeTokenTypeList());
        }
    }

    public void addNewTile(HexCoordinate newTileHexCoordinate) {
        //legacy code
//        // changed this so that the habitat tile is the one selected in an earlier select command
//        if (this.getSelectedTile().isNull()) {
//            System.out.println("No tile has been selected. Please select a tile first.");
//            return;
//        }
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

        //increment tile's token types within tokenOptions
        this.incrementTileTokenOption(newTile.getHabitatTile().getWildlifeTokenTypeList());

        // Checks to see if the returned array has a "middle" in it signifying it's not on the edge.
        ArrayList<PlayerBoard.PlayerBoardSide> tilePosition = this.getPartOfBoardCoordinateIsOn(newTile.getHexCoordinate());
        boolean isNewTileOnEdge = !tilePosition.contains(PlayerBoard.PlayerBoardSide.MIDDLE);
//        System.out.printf("Is the new tile on the edge? %s\n", isNewTileOnEdge);

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

        //decrement tile's token types within tokenOptions
        this.decrementTileTokenOption(focusedTile.getHabitatTile().getWildlifeTokenTypeList());

        //check if habitat tile is keystone and if so, increase numOfNatureTokens by 1
        if (focusedTile.getHabitatTile().isKeystone()) {
            this.addToNumOfNatureTokens();
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
        //TODO: Switch statement without breaks so all the relevant cases run.

        ArrayList<ScoringCards.ScoreCard> scoreCardList = ScoringCards.getScoreCardsList();
        ScoringCards.ScoreCard bearScoringCard = scoreCardList.get(0);
        ScoringCards.ScoreCard foxScoringCard = scoreCardList.get(1);
        ScoringCards.ScoreCard elkScoringCard = scoreCardList.get(2);
        ScoringCards.ScoreCard hawkScoringCard = scoreCardList.get(3);
        ScoringCards.ScoreCard salmonScoringCard = scoreCardList.get(4);

        // Score Bear Card
        switch(bearScoringCard.getScoreType()) {
            case A -> incrementScore(ScoringController.scoreBearScoringCardA(this));
            case B -> incrementScore(ScoringController.scoreBearScoringCardB(this));
            case C -> incrementScore(ScoringController.scoreBearScoringCardC(this));
        }

        // Score Fox Card
        switch(foxScoringCard.getScoreType()) {
            case A -> incrementScore(ScoringController.scoreFoxScoringCardA(this));
            case B -> incrementScore(ScoringController.scoreFoxScoringCardB(this));
            case C -> incrementScore(ScoringController.scoreFoxScoringCardC(this));

        }

        // Score Elk Card
        switch(elkScoringCard.getScoreType()) {
            case A -> incrementScore(ScoringController.scoreElkScoringCardA(this));
            case B -> incrementScore(ScoringController.scoreElkScoringCardB(this));
            case C -> incrementScore(ScoringController.scoreElkScoringCardC(this));
        }

        // Score Hawk Card
        switch(hawkScoringCard.getScoreType()) {
            case A -> incrementScore(ScoringController.scoreHawkScoringCardA(this));
            case B -> incrementScore(ScoringController.scoreHawkScoringCardB(this));
            case C -> incrementScore(ScoringController.scoreHawkScoringCardC(this));
        }

        // Score Salmon Card
        switch (salmonScoringCard.getScoreType()) {
            case A -> incrementScore(ScoringController.scoreSalmonScoringCardA(this));
            case B -> incrementScore(ScoringController.scoreSalmonScoringCardB(this));
            case C -> incrementScore(ScoringController.scoreSalmonScoringCardC(this));
        }


        return this.score;
    }

    public void incrementScore(int n) {
        this.score += n;
    }


    public Tile getTileByCoordinate(int row, int col) {

        return getPlayerBoardAs2dArray().get(row).get(col);
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

    public boolean canPlaceToken() {
        return tokenOptions.getNumOfTokenOption(this.selectedToken.wildlifeTokenType) > 0;
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

        private Integer getNumOfTokenOption(WildlifeToken.WildlifeTokenType tokenType) {
            return numTokenOptions.get(tokenType);
        }

        private void setNumOfTokenOption(WildlifeToken.WildlifeTokenType tokenType, Integer value) {
            this.numTokenOptions.replace(tokenType, value);
        }
    }
    public ArrayList<Tile> getConnectedSameTilesEast(Tile tile, PlayerBoard playerBoard) {
        ArrayList<Tile> sameTilesEast = new ArrayList<Tile>();

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
        ArrayList<Tile> sameTilesSouthEast = new ArrayList<Tile>();

        boolean hasElkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.ELK;

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
        ArrayList<Tile> sameTilesSouthEast = new ArrayList<Tile>();

        boolean hasElkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.ELK;

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

}
