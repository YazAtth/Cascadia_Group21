package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.GameUiView;
import org.grouptwentyone.views.SelectionOptionsView;

import java.util.ArrayList;
import java.util.HashMap;

public class CascadiaBot extends Player {

    public CascadiaBot(String userName) {
        super(userName);
        this.getPlayerBoardObject().setVerbose(false);
    }


    @Override
    public boolean playTurn() {

        //record the time that the bot starts playing its move
        long startTime = System.currentTimeMillis();

        ArrayList<Tile> placeableTileOptionsList = this.getPlayerBoardObject().getPlaceableTileOptionList();
        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokensOptionList = StartGame.selectedTokens;

        int highestScore = -1;
        Tile tilePositionThatGivesHighestScore =
                placeableTileOptionsList.get(RandomNumberGenerator.getRandomNumberInRange(0, placeableTileOptionsList.size() - 1 ));

        HexCoordinate wildlifeTokenPositionThatGivesHighestScore =
                this.getPlayerBoardObject().getPlaceableWildlifeTokenList().get(0).getField1();

        // We copy the habitat tile by value before playing around with it so the original tile is not affected.
        HabitatTile habitatTileFromSelectedTiles = habitatTileOptionList.get(0);
        HabitatTile habitatTileHighScore = new HabitatTile(habitatTileFromSelectedTiles);

        // Copying wildlife token by value
        WildlifeToken wildlifeTokenFromSelectedTokens = wildlifeTokensOptionList.get(0);
        WildlifeToken wildlifeTokenHighScore = new WildlifeToken(wildlifeTokenFromSelectedTokens.getWildlifeTokenType());

        int indexOfSelectedTileAndTokenPair = 0;

        // Going to be 4 tile/token pair options so we run the inner loop 4 times.
        for (int i = 0; i < 4; i++) {

            //iterate over all available locations to place a tile
            for (Tile placeableTilePosition : placeableTileOptionsList) {
                PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();

                // Place possible tile
                HabitatTile habitatTileFromSelectedTiles2 = habitatTileOptionList.get(i);
                HabitatTile selectedHabitatTile = new HabitatTile(habitatTileFromSelectedTiles2);
                duplicateBoard.setSelectedTile(selectedHabitatTile);

                HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));

                ArrayList<CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType>> placeableWildlifeTokenList
                        = duplicateBoard.getPlaceableWildlifeTokenList();


                for (CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType> placeableToken: placeableWildlifeTokenList) {

                    PlayerBoard duplicateBoard2 = duplicateBoard.getDuplicate();

                    duplicateBoard2.setSelectedToken(new WildlifeToken(placeableToken.getField2()));
                    HexCoordinate placeableTokenPosition = placeableToken.getField1();
                    duplicateBoard2.addNewToken(placeableTokenPosition);

                    int localScore = duplicateBoard2.getScore();

                    //check if tile/token pair yields a higher score than the max score
                    if (localScore > highestScore) {

                        highestScore = localScore;

                        // Reset Wildlife Token in Habitat Tile that was placed when testing for the highest score
                        habitatTileOptionList.get(i).setWildlifeToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));
                        habitatTileHighScore = habitatTileOptionList.get(i);
//                        System.out.printf("Habitat Tile has wildlife token of %s\n", habitatTileHighScore.toString(true));
                        tilePositionThatGivesHighestScore = placeableTilePosition;

                        wildlifeTokenHighScore = new WildlifeToken(placeableToken.getField2());
                        wildlifeTokenPositionThatGivesHighestScore = placeableTokenPosition;

                        indexOfSelectedTileAndTokenPair = i;
                    }
                }
            }
        }

//        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));

//        System.out.printf("Placing tile %s at position %s\n", habitatTileHighScore, tilePositionThatGivesHighestScore.getHexCoordinate());
        this.getPlayerBoardObject().setSelectedTile(habitatTileHighScore);
        this.getPlayerBoardObject().addNewTile(tilePositionThatGivesHighestScore.getHexCoordinate());

//        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));

//        System.out.printf("Placing token %s at position %s\n", wildlifeTokenHighScore, wildlifeTokenPositionThatGivesHighestScore);
        this.getPlayerBoardObject().setSelectedToken(wildlifeTokenHighScore);
        this.getPlayerBoardObject().addNewToken(wildlifeTokenPositionThatGivesHighestScore);

        StartGame.selectedTokens.remove(indexOfSelectedTileAndTokenPair);
        StartGame.selectedTiles.remove(indexOfSelectedTileAndTokenPair);

        GameUiView.printPlayerHeader(this);
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));


//        boolean test = false;
//        for (int i = 0; i < 4; i++) {
//            if (StartGame.selectedTokens.get(i).equals(wildlifeTokenHighScore) && StartGame.selectedTiles.get(i).equals(habitatTileHighScore)) {
//                StartGame.selectedTokens.remove(i);
//                StartGame.selectedTiles.remove(i);
//                test = true;
//                break;
//            }
//        }
//        if (test == false) throw new IllegalArgumentException("removal of selected tile/token pair failed");

        //detects that no tiles remain so ends player turns
        if (!SelectionOptionsView.replaceTileAndToken()) {
            StartGame.tilesRemain = false;
        }

        long endTime = System.currentTimeMillis();
        //double seconds = ((endTime - startTime) / 1000) % 60;
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");

        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
