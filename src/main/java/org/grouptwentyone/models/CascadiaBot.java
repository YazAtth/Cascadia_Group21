package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.views.BoardView;

import java.util.ArrayList;

public class CascadiaBot extends Player{

    public CascadiaBot(String userName) {
        super(userName);
    }

    @Override
    public boolean playTurn() {
        System.out.println("Do bot stuff");

        ArrayList<Tile> placeableTileOptionsList = this.getPlayerBoardObject().getPlaceableTileOptionList();

        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokensOptionList = StartGame.selectedTokens;

        System.out.println(placeableTileOptionsList);
        PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();

        System.out.println(duplicateBoard.getPlayerBoardAs2dArray().get(0).get(0).getHabitatTile().toString());
//        System.out.println(BoardView.displayTiles(duplicateBoard));
        System.out.println(this.getPlayerBoardObject().getPlayerBoardAs2dArray().get(0).get(0).getHabitatTile().toString());
//        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));


        duplicateBoard.setSelectedTile(habitatTileOptionList.get(0));
        duplicateBoard.setSelectedToken(wildlifeTokensOptionList.get(0));

        duplicateBoard.addNewTile(new HexCoordinate(0,0));
        System.out.println(placeableTileOptionsList);

//        int highestScore = -1;
//        Tile tileThatGivesHighestScore = placeableTileOptionsList.get(0);
//
//        // Going to be 4 tile/token pair options so we run the inner loop 4 times.
//        for (int i=0; i<4; i++) {
//
//            for (Tile placeableTilePosition : placeableTileOptionsList) {
//                System.out.printf("Looking at placeable tile %s\n", placeableTilePosition);
//                System.out.println(placeableTileOptionsList);
//
//                PlayerBoard duplicateBoard = this.getPlayerBoardObject().getDuplicate();
//
//                duplicateBoard.setSelectedTile(habitatTileOptionList.get(i));
//                duplicateBoard.setSelectedToken(wildlifeTokensOptionList.get(i));
//                duplicateBoard.addNewTile(placeableTilePosition.getHexCoordinate());
//                System.out.println(placeableTileOptionsList);
//
//
//
//
//                int localScore = duplicateBoard.getScore();
//                if (localScore > highestScore) {
//                    highestScore = localScore;
//                    tileThatGivesHighestScore = placeableTilePosition;
//                }
//            }
//        }
//
//        this.getPlayerBoardObject().setSelectedTile(tileThatGivesHighestScore.getHabitatTile());
//        this.getPlayerBoardObject().setSelectedToken(tileThatGivesHighestScore.getHabitatTile().getWildlifeToken());
//        this.getPlayerBoardObject().addNewTile(tileThatGivesHighestScore.getHexCoordinate());
//
//        System.out.printf("Placed %s to %s", tileThatGivesHighestScore, tileThatGivesHighestScore.getHexCoordinate());



        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
