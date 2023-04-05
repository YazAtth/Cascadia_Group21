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
//        System.out.println("Do bot stuff\n\n");
//        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
//        System.out.println("\n\n");


        ArrayList<Tile> placeableTileOptionsList = this.getPlayerBoardObject().getPlaceableTileOptionList();

        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokensOptionList = StartGame.selectedTokens;


        int highestScore = -1;
        Tile tilePositionThatGivesHighestScore = placeableTileOptionsList.get(RandomNumberGenerator.getRandomNumberInRange(0, placeableTileOptionsList.size()-1));
//        System.out.println(tilePositionThatGivesHighestScore);

        HexCoordinate wildlifeTokenPositionThatGivesHighestScore =
                this.getPlayerBoardObject().getPlaceableWildlifeTokenList().get(0).getField1();

        // We copy the habitat tile by value before playing around with it so the orignal tile is not affected.
        HabitatTile habitatTileFromSelectedTiles = habitatTileOptionList.get(0);
        HabitatTile habitatTileHighScore = new HabitatTile(
                habitatTileFromSelectedTiles.getHabitatTileTypeList(),
                habitatTileFromSelectedTiles.getWildlifeTokenTypeList(),
                habitatTileFromSelectedTiles.getWildlifeToken().getWildlifeTokenType(),
                habitatTileFromSelectedTiles.isKeystone(),
                habitatTileFromSelectedTiles.isNull()
        );

        // Copying wildlife token by value
        WildlifeToken wildlifeTokenFromSelectedTokens = wildlifeTokensOptionList.get(0);
        WildlifeToken wildlifeTokenHighScore = new WildlifeToken(wildlifeTokenFromSelectedTokens.getWildlifeTokenType());

        // Going to be 4 tile/token pair options so we run the inner loop 4 times.
        for (int i=0; i<4; i++) {

            for (Tile placeableTilePosition : placeableTileOptionsList) {
                PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();

                // Place possible tile
                HabitatTile habitatTileFromSelectedTiles2 = habitatTileOptionList.get(i);
                HabitatTile selectedHabitatTile = new HabitatTile(
                        habitatTileFromSelectedTiles2.getHabitatTileTypeList(),
                        habitatTileFromSelectedTiles2.getWildlifeTokenTypeList(),
                        habitatTileFromSelectedTiles2.getWildlifeToken().getWildlifeTokenType(),
                        habitatTileFromSelectedTiles2.isKeystone(),
                        habitatTileFromSelectedTiles2.isNull()
                );
                duplicateBoard.setSelectedTile(selectedHabitatTile);


                HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));

                ArrayList<CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType>> placeableWildlifeTokenList = duplicateBoard.getPlaceableWildlifeTokenList();


                for (CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType> placeableToken: placeableWildlifeTokenList) {

                    PlayerBoard duplicateBoard2 = duplicateBoard.getDuplicate();

                    duplicateBoard2.setSelectedToken(new WildlifeToken(placeableToken.getField2()));
                    HexCoordinate placeableTokenPosition = placeableToken.getField1();
                    duplicateBoard2.addNewToken(placeableTokenPosition);

                    int localScore = duplicateBoard2.getScore();
                    if (localScore > highestScore) {

                        highestScore = localScore;


                        // Reset Wildlife Token in Habitat Tile that was placed when testing for the highest score
                        habitatTileOptionList.get(i).setWildlifeToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));
                        habitatTileHighScore = habitatTileOptionList.get(i);
//                        System.out.printf("Habitat Tile has wildlife token of %s\n", habitatTileHighScore.toString(true));
                        tilePositionThatGivesHighestScore = placeableTilePosition;

                        wildlifeTokenHighScore = new WildlifeToken(placeableToken.getField2());
                        wildlifeTokenPositionThatGivesHighestScore = placeableTokenPosition;

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

//        GameUiView.printPageBorder();



        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
