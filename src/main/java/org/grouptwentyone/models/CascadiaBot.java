package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.GameUiView;
import org.grouptwentyone.views.SelectionOptionsView;
import org.grouptwentyone.views.UserInputView;

import java.util.ArrayList;
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

        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokensOptionList = StartGame.selectedTokens;


        int highestScore = -1;
        Tile tilePositionThatGivesHighestScore = placeableTileOptionsList
                .get(RandomNumberGenerator.getRandomNumberInRange(0, placeableTileOptionsList.size()-1));

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
        for (int i=0; i<4; i++) {

            for (Tile placeableTilePosition : placeableTileOptionsList) {
                PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();

                // Place possible tile
                HabitatTile habitatTileFromSelectedTiles2 = habitatTileOptionList.get(i);
                HabitatTile selectedHabitatTile = new HabitatTile(habitatTileFromSelectedTiles2);
                duplicateBoard.setSelectedTile(selectedHabitatTile);


                HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));

                ArrayList<CustomPair<HexCoordinate, WildlifeToken.WildlifeTokenType>> placeableWildlifeTokenList = duplicateBoard.getPlaceableWildlifeTokenList();

                // Get list of placeable tokens and their corresponding coordinates
                // Loop through each to try and find the token/coordinate pair that will give the highest score.
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

                        // Save the tiles and tokens that result in the highest score
                        habitatTileHighScore = habitatTileOptionList.get(i);
                        tilePositionThatGivesHighestScore = placeableTilePosition;
                        wildlifeTokenHighScore = new WildlifeToken(placeableToken.getField2());
                        wildlifeTokenPositionThatGivesHighestScore = placeableTokenPosition;
                        indexOfSelectedTileAndTokenPair = i;

                    }
                }
            }
        }

        // Add the tiles and tokens we determined to give the highest score to the actual playerboard
        this.getPlayerBoardObject().setSelectedTile(habitatTileHighScore);
        this.getPlayerBoardObject().addNewTile(tilePositionThatGivesHighestScore.getHexCoordinate());
        this.getPlayerBoardObject().setSelectedToken(wildlifeTokenHighScore);
        this.getPlayerBoardObject().addNewToken(wildlifeTokenPositionThatGivesHighestScore);

        // Remove the tile and token we placed from the list of selected tiles and tokens
        StartGame.selectedTokens.remove(indexOfSelectedTileAndTokenPair);
        StartGame.selectedTiles.remove(indexOfSelectedTileAndTokenPair);

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

        //detects that no tiles remain so ends player turns
        if (!SelectionOptionsView.replaceTileAndToken()) {
            StartGame.tilesRemain = false;
        }


        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
