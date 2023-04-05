package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.views.BoardView;

import java.util.ArrayList;
import java.util.HashMap;

public class CascadiaBot extends Player{

    public CascadiaBot(String userName) {
        super(userName);
    }

    @Override
    public boolean playTurn() {
        System.out.println("Do bot stuff\n\n");

        ArrayList<Tile> placeableTileOptionsList = this.getPlayerBoardObject().getPlaceableTileOptionList();

        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokensOptionList = StartGame.selectedTokens;

//        System.out.printf("Looking at placeable tile %s\n", placeableTileOptionsList.get(0));
//        System.out.println(placeableTileOptionsList);
//        PlayerBoard duplicateBoard2 = this.getPlayerBoardObject().getDuplicate();
//
//        duplicateBoard2.setSelectedTile(habitatTileOptionList.get(0));
//        duplicateBoard2.setSelectedToken(wildlifeTokensOptionList.get(0));
////        duplicateBoard2.addNewTile(new HexCoordinate(0,0));
////        HexCoordinate placeableTileHexCoord = placeableTileOptionsList.get(0).getHexCoordinate();
//        duplicateBoard2.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));
//
//        System.out.println(placeableTileOptionsList);

//        System.out.println("\n\n\n");

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

                duplicateBoard.setSelectedTile(habitatTileOptionList.get(i));
//                duplicateBoard.setSelectedToken(wildlifeTokensOptionList.get(i));

                HexCoordinate placeableTileHexCoord = placeableTilePosition.getHexCoordinate();
                duplicateBoard.addNewTile(new HexCoordinate(placeableTileHexCoord.getX(), placeableTileHexCoord.getY()));

                HashMap<HexCoordinate, WildlifeToken> placeableTokenHash;



                System.out.println("");


                int localScore = duplicateBoard.getScore();
                if (localScore > highestScore) {
                    highestScore = localScore;

                    habitatTileHighScore = habitatTileOptionList.get(i);
                    wildlifeTokenHighScore = wildlifeTokensOptionList.get(i);
                    tileThatGivesHighestScore = placeableTilePosition;

                }
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
