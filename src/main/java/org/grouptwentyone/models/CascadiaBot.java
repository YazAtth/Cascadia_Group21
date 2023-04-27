package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.ReservePopulationController;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.SelectionOptionsView;

import java.util.*;


// NOTE: Program will sometimes crash until the elk, salmon and hawk reserve values are implemented.

public class CascadiaBot extends Player {

    static boolean displayBotActions = true;

    public CascadiaBot(String userName) {
        super(userName);
        this.getPlayerBoardObject().setVerbose(false);
    }

    private CustomPair<WildlifeToken.WildlifeTokenType, HexCoordinate> getOptimalWildlifeTokenTypeAndPositionToPlace() {
        // Calculate reserve values
        ArrayList<Tile> placedTiles = this.getPlayerBoardObject().getActiveTiles();

        // List of habitat tiles and wildlife tokens that are available to the bot
        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokenOptionList = StartGame.selectedTokens;

        WildlifeToken.WildlifeTokenType bestWildlifeTokenToPlace = wildlifeTokenOptionList.get(0).getWildlifeTokenType();
        ArrayList<CustomPair<Tile, ReserveValueContainer>> adjacentTileReservePairs = new ArrayList<>();

        // Loop through all the tiles that have been placed on the board and calculate the reserve values for each tile.
        for (Tile tile: placedTiles) {

            // Run code to populate reserve values for each tile.
            // Only populate the tiles we need.

            ReserveValueContainer reserveValueContainer = new ReserveValueContainer(tile.getHabitatTile().getWildlifeTokenTypeList());
            ArrayList<WildlifeToken.WildlifeTokenType> placeableWildlifeTokenTypes = tile.getHabitatTile().getWildlifeTokenTypeList();

            // Code that will set the reserve values for each wildlife token go here.
            // Only populate reserves of wildlife tokens that are presented as options to bot AND are placeable on placed tile (in outer loop).
            if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX))
                    && placeableWildlifeTokenTypes.contains(WildlifeToken.WildlifeTokenType.FOX)) {
                reserveValueContainer.setWildlifeReserveWeight(
                        WildlifeToken.WildlifeTokenType.FOX,
                        ReservePopulationController.getNumberOfAdjacentUniquePlacedWildlifeTokensToFox(
                                this.getPlayerBoardObject()
                        )
                );
            }

            if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR))
                    && placeableWildlifeTokenTypes.contains(WildlifeToken.WildlifeTokenType.BEAR)) {
                reserveValueContainer.setWildlifeReserveWeight(
                        WildlifeToken.WildlifeTokenType.BEAR,
                        ReservePopulationController.getNumberOfBearPairsAfterPlacingToken(
                                this.getPlayerBoardObject()
                        )
                );
            }

            //TODO: Fill in reserves for Elk, Hawk and Salmon


            adjacentTileReservePairs.add(new CustomPair<>(tile, reserveValueContainer));


        }


        // Identify the largest reserve value that is ALSO available in the wildlife token options
        CustomPair<Tile, ReserveValueContainer> bestTileReservePair = adjacentTileReservePairs.get(0); // Defaults to the first tile/reserve pair

        // Loop through all the tile/reserve pairs and find the one with the largest reserve value.
        for (CustomPair<Tile, ReserveValueContainer> tileReservePair: adjacentTileReservePairs) {
            boolean foundReserveValueLargerThanRecorded = tileReservePair.getField2().getLargestWildlifeReserveValue().getValue() >
                    bestTileReservePair.getField2().getLargestWildlifeReserveValue().getValue();

            if (foundReserveValueLargerThanRecorded) {
                bestTileReservePair = tileReservePair;
            }
        }

        // Extract the wildlife token type from the best tile/reserve pair and return it.
        WildlifeToken.WildlifeTokenType wildlifeTokenTypeToPlace = bestTileReservePair.getField2().getLargestWildlifeReserveValue().getKey();
        HexCoordinate hexCoordinateToPlaceWildlifeToken = bestTileReservePair.getField1().getHexCoordinate();

        return new CustomPair<>(wildlifeTokenTypeToPlace, hexCoordinateToPlaceWildlifeToken);

    }

    private HabitatTile getOptimalHabitatTileToPlace() {

        // Returns empty habitat tile for the time being.
        return new HabitatTile();
    }


    @Override
    public boolean playTurn() {
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
        System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));

        CustomPair<WildlifeToken.WildlifeTokenType, HexCoordinate> wildlifeTokenTypeAndPositionToPlace = getOptimalWildlifeTokenTypeAndPositionToPlace();
        WildlifeToken.WildlifeTokenType wildlifeTokenTypeToPlace = wildlifeTokenTypeAndPositionToPlace.getField1();
        HexCoordinate wildlifeTokenPositionToPlace = wildlifeTokenTypeAndPositionToPlace.getField2();

//        System.out.println(wildlifeTokenTypeToPlace);
//        System.out.println(wildlifeTokenPositionToPlace);

        HabitatTile habitatTileToPlace = getOptimalHabitatTileToPlace();


        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
