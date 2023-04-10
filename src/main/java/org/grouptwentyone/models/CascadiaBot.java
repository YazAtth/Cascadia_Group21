package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.WildlifeTokensController;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.GameUiView;
import org.grouptwentyone.views.SelectionOptionsView;
import org.grouptwentyone.views.UserInputView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class CascadiaBot extends Player{

    static boolean displayBotActions = true;

    public CascadiaBot(String userName) {
        super(userName);
        this.getPlayerBoardObject().setVerbose(false);
    }


    @Override
    public boolean playTurn() {

        long startime = System.currentTimeMillis();

        ArrayList<Tile> placeableTileOptionsList = this.getPlayerBoardObject().getPlaceableTileOptionList();
        Collections.shuffle(placeableTileOptionsList);

        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokensOptionList = StartGame.selectedTokens;


        int highestScore = -1;

        //dummy initialisers
        Tile tilePositionThatGivesHighestScore = placeableTileOptionsList
                .get(RandomNumberGenerator.getRandomNumberInRange(0, placeableTileOptionsList.size()-1));
        HexCoordinate wildlifeTokenPositionThatGivesHighestScore = new HexCoordinate(-1, -1);
        HabitatTile habitatTileHighScore = new HabitatTile(habitatTileOptionList.get(0));
        WildlifeToken wildlifeTokenHighScore = new WildlifeToken(wildlifeTokensOptionList.get(0).getWildlifeTokenType());
        int indexOfSelectedTileAndTokenPair = 0;
        int indexOfToken = 0;



        //check nature token options (runs essentially the same code as below except the indexes for tile and token
        //choice is separate) the spending of nature tokens is more likely as this goes first but oh well
        if (this.getNumOfNatureTokens() > 0) {
            for (int i=0; i<4; i++) {

                // Copying habitat tile by value
                HabitatTile habitatTileFromSelectedTiles = habitatTileOptionList.get(i);

                for (Tile placeableTilePosition : placeableTileOptionsList) {
                    PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();

                    // Place possible tile
                    HabitatTile selectedHabitatTile = new HabitatTile(habitatTileFromSelectedTiles);
                    duplicateBoard.setSelectedTile(selectedHabitatTile);

                    HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                    duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));
                    for (int j = 0; j < 4; j++) {
                        //Copying wildlife token by value
                        WildlifeToken wildlifeTokenFromSelectedTokens = wildlifeTokensOptionList.get(j);

                        ArrayList<CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType>> placeableWildlifeTokenList = duplicateBoard.getPlaceableWildlifeTokenList(j);

                        //check if token can be placed anywhere on board then...
                        // Get list of placeable tokens and their corresponding coordinates
                        // Loop through each to try and find the token/coordinate pair that will give the highest score.
                        if (this.getPlayerBoardObject().getTokenOptions().getNumOfTokenOption(wildlifeTokenFromSelectedTokens.getWildlifeTokenType()) > 0) {
                            for (CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType> placeableToken : placeableWildlifeTokenList) {
                                PlayerBoard duplicateBoard2 = duplicateBoard.getDuplicate();

                                //place token at possible position
                                duplicateBoard2.setSelectedToken(new WildlifeToken(placeableToken.getField2()));
                                HexCoordinate placeableTokenPosition = placeableToken.getField1();
                                duplicateBoard2.addNewToken(placeableTokenPosition);

                                int localScore = duplicateBoard2.getScore();
                                if (localScore > highestScore) {
                                    highestScore = localScore;

                                    // Reset Wildlife Token in Habitat Tile that was placed when testing for the highest score
                                    habitatTileOptionList.get(i).setWildlifeToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));

                                    // Save the tiles and tokens that result in the highest score
                                    habitatTileHighScore = habitatTileOptionList.get(i);
                                    tilePositionThatGivesHighestScore = placeableTilePosition;
                                    wildlifeTokenHighScore = new WildlifeToken(placeableToken.getField2());
                                    wildlifeTokenPositionThatGivesHighestScore = placeableTokenPosition;
                                    indexOfSelectedTileAndTokenPair = i;
                                    indexOfToken = j;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Going to be 4 tile/token pair options so we run the inner loop 4 times.
            for (int i = 0; i < 4; i++) {

                // Copying wildlife token and habitat tile by value
                WildlifeToken wildlifeTokenFromSelectedTokens = wildlifeTokensOptionList.get(i);
                HabitatTile habitatTileFromSelectedTiles = habitatTileOptionList.get(i);

                for (Tile placeableTilePosition : placeableTileOptionsList) {
                    PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();

                    // Place possible tile
                    HabitatTile selectedHabitatTile = new HabitatTile(habitatTileFromSelectedTiles);
                    duplicateBoard.setSelectedTile(selectedHabitatTile);

                    HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                    duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));

                    ArrayList<CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType>> placeableWildlifeTokenList = duplicateBoard.getPlaceableWildlifeTokenList(i);

                    //check if token can be placed anywhere on board then...
                    // Get list of placeable tokens and their corresponding coordinates
                    // Loop through each to try and find the token/coordinate pair that will give the highest score.
                    if (this.getPlayerBoardObject().getTokenOptions().getNumOfTokenOption(wildlifeTokenFromSelectedTokens.getWildlifeTokenType()) > 0) {
                        for (CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType> placeableToken : placeableWildlifeTokenList) {
                            PlayerBoard duplicateBoard2 = duplicateBoard.getDuplicate();

                            //place token at possible position
                            duplicateBoard2.setSelectedToken(new WildlifeToken(placeableToken.getField2()));
                            HexCoordinate placeableTokenPosition = placeableToken.getField1();
                            duplicateBoard2.addNewToken(placeableTokenPosition);

                            int localScore = duplicateBoard2.getScore();
                            if (localScore > highestScore) {
                                highestScore = localScore;

                                // Reset Wildlife Token in Habitat Tile that was placed when testing for the highest score
                                habitatTileOptionList.get(i).setWildlifeToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));

                                // Save the tiles and tokens that result in the highest score
                                habitatTileHighScore = habitatTileOptionList.get(i);
                                tilePositionThatGivesHighestScore = placeableTilePosition;
                                wildlifeTokenHighScore = new WildlifeToken(placeableToken.getField2());
                                wildlifeTokenPositionThatGivesHighestScore = placeableTokenPosition;
                                indexOfSelectedTileAndTokenPair = i;
                                indexOfToken = i;
                            }
                        }
                    }
                }
            }
        }


        // Add the tiles and tokens we determined to give the highest score to the actual playerboard
        this.getPlayerBoardObject().setSelectedTile(habitatTileHighScore);
        this.getPlayerBoardObject().addNewTile(tilePositionThatGivesHighestScore.getHexCoordinate());
        if (highestScore > 0) {
            this.getPlayerBoardObject().setSelectedToken(wildlifeTokenHighScore);
            this.getPlayerBoardObject().addNewToken(wildlifeTokenPositionThatGivesHighestScore);
            //if the indexes are different that means a nature token must have been spent
            if (indexOfToken != indexOfSelectedTileAndTokenPair)
                this.spendNatureToken();
        } else {
            //return token
            WildlifeTokensController.wildlifeTokenBag.add(this.getPlayerBoardObject().getSelectedToken());
        }

        // Remove the tile and token we placed from the list of selected tiles and tokens
        StartGame.selectedTokens.remove(indexOfSelectedTileAndTokenPair);
        StartGame.selectedTiles.remove(indexOfSelectedTileAndTokenPair);

        //detects that no tiles remain so ends player turns
        if (!SelectionOptionsView.replaceTileAndToken()) {
            StartGame.tilesRemain = false;
        }

        // Display the bots actions to the user. Will not display if the user has requested the feature be turned off.
        if (displayBotActions) {
            // Displays the bots playerboard
            System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));

            // String to display the habitats of the habitat tiles
            StringBuilder placedHabitatTile = new StringBuilder();
            if (habitatTileHighScore.getHabitatTileTypeList().size() == 1) {
                placedHabitatTile.append(habitatTileHighScore.getHabitatTileTypeList().get(0));
            } else {
                placedHabitatTile.append(habitatTileHighScore.getHabitatTileTypeList().get(0));
                placedHabitatTile.append(" & ");
                placedHabitatTile.append(habitatTileHighScore.getHabitatTileTypeList().get(1));
            }

            long endTime = System.currentTimeMillis();

            System.out.printf("""
                            STATS
                            -----------------------------------------
                            - placed a %s habitat tile on position %s.
                            - placed a %s wildlife token on position %s                                  
                            Score       |    %d points
                            Time Taken  |    %d milliseconds
                            
                            """,

                    placedHabitatTile,
                    tilePositionThatGivesHighestScore.getHexCoordinate(),
                    wildlifeTokenHighScore.getWildlifeTokenType(),
                    wildlifeTokenPositionThatGivesHighestScore,
                    this.getScore(),
                    (endTime - startime)
            );

            System.out.println("Press \"ENTER\" on your keyboard to continue or press \"1\" to disable bot action description.");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine().toLowerCase().trim();

            if (userInput.equals("1")) {
                displayBotActions = false;
            }

            GameUiView.printLargeSpace();
        }


        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
