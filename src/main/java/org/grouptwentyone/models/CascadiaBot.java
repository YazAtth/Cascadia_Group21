package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.ReservePopulationController;

import java.util.*;

public class CascadiaBot extends Player {

    static boolean displayBotActions = true;

    public CascadiaBot(String userName) {
        super(userName);
        this.getPlayerBoardObject().setVerbose(false);
    }


    @Override
    public boolean playTurn() {

        // Calculate reserve values
        ArrayList<Tile> placedTiles = this.getPlayerBoardObject().getActiveTiles();

        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokenOptionList = StartGame.selectedTokens;

        WildlifeToken.WildlifeTokenType bestWildlifeTokenToPlace = wildlifeTokenOptionList.get(0).getWildlifeTokenType();
        ArrayList<CustomPair<Tile, ReserveValueContainer>> adjacentTileReservePairs = new ArrayList<>();

        for (Tile tile: placedTiles) {
            // Run code to populate reserve values for each tile.
            // Only populate the tiles we need.

            ArrayList<Tile> adjacentTiles = this.getPlayerBoardObject().getAdjacentTileList(tile);

            for (Tile adjacentTile: adjacentTiles) {
                ReserveValueContainer reserveValueContainer = new ReserveValueContainer();

                // Code that will set the reserve values for each wildlife token go here.
                // Only populate reserves of wildlife tokens that are presented as options to bot.
                if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX))) {
                    reserveValueContainer.setWildlifeReserveWeight(
                            WildlifeToken.WildlifeTokenType.FOX,
                            ReservePopulationController.getNumberOfAdjacentUniquePlacedWildlifeTokensToFox(
                                    this.getPlayerBoardObject()
                            )
                    );
                }
                if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR))) {
                    reserveValueContainer.setWildlifeReserveWeight(
                            WildlifeToken.WildlifeTokenType.BEAR,
                            ReservePopulationController.getNumberOfBearPairsAfterPlacingToken(
                                    this.getPlayerBoardObject()
                            )
                    );
                }

                //TODO: Fill in reserves for Elk, Hawk and Salmon


                adjacentTileReservePairs.add(new CustomPair<>(adjacentTile, reserveValueContainer));
            }

//            System.out.println(bestTileReservePair);







//            Map.Entry<WildlifeToken.WildlifeTokenType, Double> bestWildlifeTokenReserveValuePairToPlace = wildlifeTokenOptionList.get(0).getWildlifeTokenType();

//            for (CustomPair<Tile, ReserveValueContainer> tileReservePair: adjacentTileReservePairs) {
//                // Get best reserve within tile
//                Map.Entry<WildlifeToken.WildlifeTokenType, Double> bestWildlifeTokenReserveValuePairToPlace =
//                        tileReservePair.getField2().getLargestWildlifeReserveValue();
//
//                Map.Entry<WildlifeToken.WildlifeTokenType, Double> wildlifeTokenToPlace =
//                        tileReservePair.getField2().getLargestWildlifeReserveValue();
//
//                boolean isReserveValueLargerThanRecorded = wildlifeTokenToPlace.getValue() > bestWildlifeTokenReserveValuePairToPlace.getValue();
//                //TODO: Check if the below boolean actually does anything
//                boolean isWildlifeTokenInOptionList = wildlifeTokenOptionList.contains(new WildlifeToken(wildlifeTokenToPlace.getKey()));
//
//                if (isReserveValueLargerThanRecorded && isWildlifeTokenInOptionList) {
//                    System.out.printf("Changed to %s token", wildlifeTokenToPlace.getKey());
//                    bestWildlifeTokenReserveValuePairToPlace = wildlifeTokenToPlace;
//                    bestWildlifeTokenToPlace = bestWildlifeTokenReserveValuePairToPlace.getKey();
//                }
//            }

//            bestWildlifeTokenToPlace = bestWildlifeTokenReserveValuePairToPlace.getKey();
        }


        // Identify the largest reserve value that is ALSO available in the wildlife token options
        CustomPair<Tile, ReserveValueContainer> bestTileReservePair = adjacentTileReservePairs.get(0); // Defaults to the first tile/reserve pair

        for (CustomPair<Tile, ReserveValueContainer> tileReservePair: adjacentTileReservePairs) {
            boolean foundReserveValueLargerThanRecorded = tileReservePair.getField2().getLargestWildlifeReserveValue().getValue() >
                    bestTileReservePair.getField2().getLargestWildlifeReserveValue().getValue();

            if (foundReserveValueLargerThanRecorded) {
                bestTileReservePair = tileReservePair;
            }
        }

        WildlifeToken.WildlifeTokenType wildlifeTokenTypeToPlace = bestTileReservePair.getField2().getLargestWildlifeReserveValue().getKey();




        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
