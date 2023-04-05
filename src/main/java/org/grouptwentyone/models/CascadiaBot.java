package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.GameUiView;

import java.util.ArrayList;
import java.util.HashMap;

public class CascadiaBot extends Player{

    public CascadiaBot(String userName) {
        super(userName);
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
        Tile tileThatGivesHighestScore = placeableTileOptionsList.get(0);
        HabitatTile habitatTileHighScore = habitatTileOptionList.get(0);
        WildlifeToken wildlifeTokenHighScore = wildlifeTokensOptionList.get(0);

        // Going to be 4 tile/token pair options so we run the inner loop 4 times.
        for (int i=0; i<4; i++) {

            for (Tile placeableTilePosition : placeableTileOptionsList) {
                System.out.printf("Looking at placeable tile %s\n", placeableTilePosition);
                System.out.println(placeableTileOptionsList);

                PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();
                System.out.printf("Duplicate Board 1: %s\n", duplicateBoard.getPlayerBoard());


//                duplicateBoard.setSelectedToken(wildlifeTokensOptionList.get(i));

                // Place possible tile
                duplicateBoard.setSelectedTile(habitatTileOptionList.get(i));
                HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));

                ArrayList<CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType>> placeableWildlifeTokenList = this.getPlayerBoardObject().getPlaceableWildlifeTokenList();
                System.out.println(placeableWildlifeTokenList);

                for (CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType> placeableToken: placeableWildlifeTokenList) {

                    PlayerBoard duplicateBoard2 = duplicateBoard.getDuplicate();

                    System.out.printf("Duplicate Board 2: %s\n", duplicateBoard2.getPlayerBoard());
                    duplicateBoard2.setSelectedToken(new WildlifeToken(placeableToken.getField2()));
                    System.out.println(BoardView.displayTiles(duplicateBoard2));

                    HexCoordinate newTokenPosition = duplicateBoard2.getHexCoordinateAfterBoardShift(placeableToken.getField1());
                    System.out.printf("Placing wildlife token %s to position %s\n", placeableToken.getField2(), newTokenPosition);
                    duplicateBoard2.addNewToken(newTokenPosition);
                    System.out.println(BoardView.displayTiles(duplicateBoard2));



                    int localScore = duplicateBoard2.getScore();
                    if (localScore > highestScore) {
                        highestScore = localScore;

                        habitatTileHighScore = habitatTileOptionList.get(i);
                        wildlifeTokenHighScore = wildlifeTokensOptionList.get(i);
                        tileThatGivesHighestScore = placeableTilePosition;

                    }


                }

                System.out.println("");



            }
        }


//        System.out.printf("Going to place tile %s at position %s\n", tileThatGivesHighestScore, tileThatGivesHighestScore.getHexCoordinate());
        this.getPlayerBoardObject().setSelectedTile(habitatTileHighScore);
        this.getPlayerBoardObject().setSelectedToken(wildlifeTokenHighScore);
        this.getPlayerBoardObject().addNewTile(tileThatGivesHighestScore.getHexCoordinate());

        System.out.printf("Placed %s to %s", tileThatGivesHighestScore, tileThatGivesHighestScore.getHexCoordinate());




        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
