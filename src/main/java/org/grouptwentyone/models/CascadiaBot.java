package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.GameUiView;

import java.util.ArrayList;
import java.util.HashMap;

public class CascadiaBot extends Player{

    public CascadiaBot(String userName) {
        super(userName);
        this.getPlayerBoardObject().setVerbose(false);
    }


    @Override
    public boolean playTurn() {
        System.out.println("Do bot stuff\n\n");
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
//        System.out.printf("Original Board: %s\n", this.getPlayerBoardObject().getPlayerBoard());

//        PlayerBoard dupBoard1 = this.getPlayerBoardObject().getDuplicate();
//        System.out.printf("dupBoard1: %s\n", dupBoard1.getPlayerBoard());
//
////        dupBoard1.setSelectedTile(StartGame.selectedTiles.get(0));
//        HabitatTile oldHabitatTile = StartGame.selectedTiles.get(0);
//        HabitatTile newHabitatTile = new HabitatTile(
//                oldHabitatTile.getHabitatTileTypeList(),
//                oldHabitatTile.getWildlifeTokenTypeList(),
//                oldHabitatTile.getWildlifeToken().getWildlifeTokenType(),
//                oldHabitatTile.isKeystone(),
//                oldHabitatTile.isNull()
//        );
//
//        dupBoard1.setSelectedTile(newHabitatTile);
//        dupBoard1.addNewTile(new HexCoordinate(0,0));
//
//        System.out.printf("dupBoard1: %s\n", dupBoard1.getPlayerBoard());
        System.out.println("\n\n");


        ArrayList<Tile> placeableTileOptionsList = this.getPlayerBoardObject().getPlaceableTileOptionList();

        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokensOptionList = StartGame.selectedTokens;


        int highestScore = -1;
        Tile tilePositionThatGivesHighestScore = placeableTileOptionsList.get(RandomNumberGenerator.getRandomNumberInRange(0, placeableTileOptionsList.size()-1));
        System.out.println(tilePositionThatGivesHighestScore);

        HexCoordinate wildlifeTokenPositionThatGivesHighestScore =
                this.getPlayerBoardObject().getPlaceableWildlifeTokenList().get(0).getField1();

        HabitatTile habitatTileHighScore = habitatTileOptionList.get(0);
        WildlifeToken wildlifeTokenHighScore = wildlifeTokensOptionList.get(0);

        // Going to be 4 tile/token pair options so we run the inner loop 4 times.
        for (int i=0; i<4; i++) {

            for (Tile placeableTilePosition : placeableTileOptionsList) {
//                System.out.printf("Looking at placeable tile %s\n", placeableTilePosition);
//                System.out.println(placeableTileOptionsList);

                PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();
//                System.out.printf("Duplicate Board 1: %s\n", duplicateBoard.getPlayerBoard());


//                duplicateBoard.setSelectedToken(wildlifeTokensOptionList.get(i));

                // Place possible tile
                duplicateBoard.setSelectedTile(habitatTileOptionList.get(i));
                HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));

                ArrayList<CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType>> placeableWildlifeTokenList = duplicateBoard.getPlaceableWildlifeTokenList();
//                System.out.printf("pleaceableWildlifeTokenList: %s\n", placeableWildlifeTokenList);
//                System.out.println(placeableWildlifeTokenList);

                for (CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType> placeableToken: placeableWildlifeTokenList) {

                    PlayerBoard duplicateBoard2 = duplicateBoard.getDuplicate();

//                    System.out.printf("Duplicate Board 2: %s\n", duplicateBoard2.getPlayerBoard());
                    duplicateBoard2.setSelectedToken(new WildlifeToken(placeableToken.getField2()));
//                    System.out.println(BoardView.displayTiles(duplicateBoard2));

//                    HexCoordinate newTokenPosition = duplicateBoard2.getHexCoordinateAfterBoardShift(placeableToken.getField1());
                    HexCoordinate newTokenPosition = placeableToken.getField1();

//                    System.out.printf("Placeable token %s in placeable list: %s\n", placeableToken, placeableWildlifeTokenList);
//                    System.out.printf("Placing wildlife token %s to position %s\n", placeableToken.getField2(), newTokenPosition);
//                    System.out.println(BoardView.displayTiles(duplicateBoard2));
                    duplicateBoard2.addNewToken(newTokenPosition);
//                    System.out.println(BoardView.displayTiles(duplicateBoard2));



                    int localScore = duplicateBoard2.getScore();
//                    System.out.println(localScore);
                    if (localScore > highestScore) {

//                        System.out.printf("Score was %d, now is %d\nNew tile was at position %s now at %s\n\n",
//                                highestScore, localScore, tilePositionThatGivesHighestScore, placeableTilePosition);
                        highestScore = localScore;

                        habitatTileHighScore = habitatTileOptionList.get(i);
                        wildlifeTokenHighScore = new WildlifeToken(placeableToken.getField2());
                        tilePositionThatGivesHighestScore = placeableTilePosition;
                        wildlifeTokenPositionThatGivesHighestScore = newTokenPosition;

//                        System.out.printf("New Token Position: %s\n", newTokenPosition);
//                        System.out.println(duplicateBoard2.getPlaceableWildlifeTokenList());
//                        System.out.println(BoardView.displayTiles(duplicateBoard2));

                    }


                }

//                System.out.println("");



            }
        }


//        System.out.printf("Going to place tile %s at position %s\n", tilePositionThatGivesHighestScore, tilePositionThatGivesHighestScore.getHexCoordinate());
        this.getPlayerBoardObject().setSelectedTile(habitatTileHighScore);
        this.getPlayerBoardObject().addNewTile(tilePositionThatGivesHighestScore.getHexCoordinate());

        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));

//        System.out.printf("Going to place %s token at position %s\n", wildlifeTokenHighScore.getWildlifeTokenType(), wildlifeTokenPositionThatGivesHighestScore);
        this.getPlayerBoardObject().setSelectedToken(wildlifeTokenHighScore);

//        System.out.printf("TOKEN: %s\n",
//                this.getPlayerBoardObject().getPlayerBoard().get(wildlifeTokenPositionThatGivesHighestScore.getX())
//                        .get(wildlifeTokenPositionThatGivesHighestScore.getY()).getHabitatTile().toString(true));

        this.getPlayerBoardObject().addNewToken(wildlifeTokenPositionThatGivesHighestScore);


//        System.out.printf("Placed %s to %s\n", tilePositionThatGivesHighestScore, tilePositionThatGivesHighestScore.getHexCoordinate());
        GameUiView.printPageBorder();
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));




        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
