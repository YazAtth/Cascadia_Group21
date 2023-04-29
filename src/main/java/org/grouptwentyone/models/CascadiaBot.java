package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.BoardStateAnalyseController;
import org.grouptwentyone.controllers.ScoringController;
import org.grouptwentyone.controllers.WeightController;
import org.grouptwentyone.models.WeightValueMaps.BearWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.ElkWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.FoxWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.HawkWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.SalmonWeightValueMap;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.SelectionOptionsView;

import java.util.*;
import java.util.stream.Collectors;


// NOTE: Program will sometimes crash until the elk, salmon and hawk reserve values are implemented.

public class CascadiaBot extends Player {

    static boolean displayBotActions = true;

    public CascadiaBot(String userName) {
        super(userName);
        this.getPlayerBoardObject().setVerbose(false);
    }

    private CustomPair<WildlifeToken.WildlifeTokenType, HexCoordinate> getOptimalWildlifeTokenTypeAndPositionToPlace() {
        // Calculate reserve values
        // Ensure only tiles with empty tokens are collected
        ArrayList<Tile> placedTiles =
                this.getPlayerBoardObject().getActiveTiles()
                        .stream()
                        .filter(tile -> tile.getHabitatTile().getWildlifeToken().equals(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY)))
                        .collect(Collectors.toCollection(ArrayList::new));


//        System.out.println(placedTiles);

        // List of habitat tiles and wildlife tokens that are available to the bot
        ArrayList<HabitatTile> habitatTileOptionList = StartGame.selectedTiles;
        ArrayList<WildlifeToken> wildlifeTokenOptionList = StartGame.selectedTokens;

        WildlifeToken.WildlifeTokenType bestWildlifeTokenToPlace = wildlifeTokenOptionList.get(0).getWildlifeTokenType();
        ArrayList<CustomPair<Tile, WildlifeTokenWeightContainer>> tileAndWeightPairs = new ArrayList<>();

        // Loop through all the tiles that have been placed on the board and calculate the reserve values for each tile.
        for (Tile tile: placedTiles) {

            //check if token has already been placed on tile
            if (tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY)
                continue;

            // Run code to populate reserve values for each tile.
            // Only populate the tiles we need.

            WildlifeTokenWeightContainer wildlifeTokenWeightContainer = new WildlifeTokenWeightContainer(tile.getHabitatTile().getWildlifeTokenTypeList());
            ArrayList<WildlifeToken.WildlifeTokenType> placeableWildlifeTokenTypes = tile.getHabitatTile().getWildlifeTokenTypeList();

            // Code that will set the reserve values for each wildlife token go here.
            // Only populate reserves of wildlife tokens that are presented as options to bot AND are placeable on placed tile (in outer loop).
            if (wildlifeTokenOptionList.contains( new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX) )
                    && placeableWildlifeTokenTypes.contains(WildlifeToken.WildlifeTokenType.FOX)) {

                // Get state of board
                int numberOfAdjacentUniquePlacedWildlifeTokensToFox =
                        BoardStateAnalyseController.getNumberOfAdjacentUniquePlacedWildlifeTokensToFox(this.getPlayerBoardObject(), tile);

                //System.out.printf("Number of adjacent unique wildlife tokens to fox at %s: %d\n", tile.getHexCoordinate(), numberOfAdjacentUniquePlacedWildlifeTokensToFox);

                // Get weight based on state
                FoxWeightValueMap foxWeightValueMap = new FoxWeightValueMap();
                double foxWeight = foxWeightValueMap.getWeightValue(numberOfAdjacentUniquePlacedWildlifeTokensToFox);

                // Set weight container based on weight
                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.FOX,
                        foxWeight
                );
            }

            if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR))
                    && placeableWildlifeTokenTypes.contains(WildlifeToken.WildlifeTokenType.BEAR)) {

//                System.out.printf("Looking at tile at %s\n", tile.getHexCoordinate());

                double bearWeight = 0;
                BearWeightValueMap bearWeightValueMap = new BearWeightValueMap();

                // Get number of bear pairs before placing token
                int numberOfBearPairsAfterPlacingToken = BoardStateAnalyseController.getNumberOfBearPairsBeforePlacingToken (
                        this.getPlayerBoardObject()
                );

//                System.out.println("\tNumber of bear pairs before placing token: " + numberOfBearPairsAfterPlacingToken);
                numberOfBearPairsAfterPlacingToken += 1; // We plus one to account for the fact that there will be an extra pair after placing the token.
                bearWeight = bearWeightValueMap.getWeightValue(numberOfBearPairsAfterPlacingToken); // Get weight value for that pair from the table.

                // If placing bear makes a pair, get the weight value for that pair.
                boolean doesPlacingBearMakePair = BoardStateAnalyseController.doesPlacingBearMakePair(this.playerBoardObject, tile.getHexCoordinate());
                if (!doesPlacingBearMakePair) {
                    //System.out.println("\tDoes not make pair");
                    // If placing bear doesn't make a pair we subtract "n" (e.g. 0.75) as the token will not increase the number of pairs.
                    // But it will allow for the possibility of a pair being made in the future.
                    bearWeight -= WeightController.WeightConstants.nonPairBearPlacementReduction;
                }

                // Custom weight if a bear pair gets ruined.
                boolean doesPlacingBearRuinPair = BoardStateAnalyseController.doesPlacingBearRuinPair(this.playerBoardObject, tile.getHexCoordinate());
                if (doesPlacingBearRuinPair) bearWeight = bearWeightValueMap.ruinsPairWeight();

//                System.out.println("\tBear weight: " + bearWeight);


                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.BEAR,
                        bearWeight
                );
            }

            if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK))
                    && placeableWildlifeTokenTypes.contains(WildlifeToken.WildlifeTokenType.ELK)) {

                double elkWeight = 0;
                ElkWeightValueMap elkWeightValueMap = new ElkWeightValueMap();

                PriorityQueue<Integer> linesOfElk = BoardStateAnalyseController.getLinesOfElkFromPosition(this.getPlayerBoardObject(), tile.getHexCoordinate());
                elkWeight = elkWeightValueMap.getWeightValue(linesOfElk);

                System.out.println("Elk Weight: " + elkWeight);

                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.ELK,
                        elkWeight
                );
            }

            if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON))
                    && placeableWildlifeTokenTypes.contains(WildlifeToken.WildlifeTokenType.SALMON)) {
                SalmonWeightValueMap salmonWeightValueMap = new SalmonWeightValueMap();
                ArrayList<Tile> salmonInRun = new ArrayList<>();

                int lengthOfSalmonRun = BoardStateAnalyseController.getLengthOfRunTileIsIn(tile, playerBoardObject, salmonInRun);
                double salmonWeight = salmonWeightValueMap.getWeightValue(lengthOfSalmonRun);

                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.SALMON,
                        salmonWeight
                );
            }

            if (wildlifeTokenOptionList.contains(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK))
                    && placeableWildlifeTokenTypes.contains(WildlifeToken.WildlifeTokenType.HAWK)) {

                double hawkWeight = 0;
                HawkWeightValueMap hawkWeightValueMap = new HawkWeightValueMap();

                int numberOfScorableHawkPairsBeforePlacingToken = BoardStateAnalyseController.getNumberOfScorableHawksBeforePlacingToken (
                        this.getPlayerBoardObject()
                );

                if (numberOfScorableHawkPairsBeforePlacingToken < 8 &&
                        !BoardStateAnalyseController.doesHawkPlacementMakeAdjacentHawks(playerBoardObject, tile.getHexCoordinate())) {
                    hawkWeight = hawkWeightValueMap.getWeightValue(numberOfScorableHawkPairsBeforePlacingToken + 1);
                }

                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.HAWK,
                        hawkWeight
                );

            }

            //TODO: Fill in reserves for Elk, Hawk and Salmon


            tileAndWeightPairs.add(new CustomPair<>(tile, wildlifeTokenWeightContainer));
        }


        // Identify the largest reserve value that is ALSO available in the wildlife token options
        CustomPair<Tile, WildlifeTokenWeightContainer> bestTileWeightPair = tileAndWeightPairs.get(0); // Defaults to the first tile/reserve pair

        // Loop through all the tile/reserve pairs and find the one with the largest reserve value.
        for (CustomPair<Tile, WildlifeTokenWeightContainer> tileWeightPair: tileAndWeightPairs) {
            boolean foundWeightValueLargerThanRecorded = tileWeightPair.getField2().getLargestWildlifeWeightValue().getValue() >
                    bestTileWeightPair.getField2().getLargestWildlifeWeightValue().getValue();

            if (foundWeightValueLargerThanRecorded) {
                bestTileWeightPair = tileWeightPair;
            }
        }

        // Extract the wildlife token type from the best tile/reserve pair and return it.
        WildlifeToken.WildlifeTokenType wildlifeTokenTypeToPlace = bestTileWeightPair.getField2().getLargestWildlifeWeightValue().getKey();
        HexCoordinate hexCoordinateToPlaceWildlifeToken = bestTileWeightPair.getField1().getHexCoordinate();

        return new CustomPair<>(wildlifeTokenTypeToPlace, hexCoordinateToPlaceWildlifeToken);

    }

    public CustomPair<HabitatTile, HexCoordinate> getOptimalHabitatTileAndPositionToPlace() {

        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
        System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
        System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));

        ArrayList<Tile> ghostTileList = this.getPlayerBoardObject().getPlaceableTileOptionList();

        // Gets list of token types that can be placed on the tiles in selectedTiles list (optimisation)
        ArrayList<WildlifeToken.WildlifeTokenType> wildlifeTokenOptionList =
                StartGame.selectedTiles
                        .stream()
                        .flatMap(habitatTile -> habitatTile.getWildlifeTokenTypeList().stream())
                        .distinct()
                        .collect(Collectors.toCollection(ArrayList::new));


        // Hash of ghost tiles and their wildlife token weight containers.
        HashMap<Tile, WildlifeTokenWeightContainer> ghostTileAndWildlifeWeightHash = ghostTileList
                .stream()
                .map(ghostTile -> new CustomPair<>(ghostTile, new WildlifeTokenWeightContainer(wildlifeTokenOptionList)))
                .collect(Collectors.toMap(CustomPair::getField1, CustomPair::getField2, (a, b) -> b, HashMap::new));


        for (Map.Entry<Tile, WildlifeTokenWeightContainer> ghostTileWeightPair:
                ghostTileAndWildlifeWeightHash.entrySet()) {


            Tile ghostTile = ghostTileWeightPair.getKey();
            WildlifeTokenWeightContainer wildlifeTokenWeightContainer = ghostTileWeightPair.getValue();

            // TODO: Populate the weight containers of each ghost tile, fox entries have already been populated.
            // Populate Fox tokens
            if (wildlifeTokenOptionList.contains(WildlifeToken.WildlifeTokenType.FOX)) {

                // Duplicate PlayerBoard is needed to simulate placing a tile with a fox placeable on the ghost tile position.
                PlayerBoard duplicatePlayerBoard = this.getPlayerBoardObject().getDuplicate();
                Tile tileWithFoxPlaceable = new Tile(new HabitatTile(true), ghostTile.getHexCoordinate());
                duplicatePlayerBoard.setSelectedTile(tileWithFoxPlaceable.getHabitatTile());
                duplicatePlayerBoard.addNewTile(tileWithFoxPlaceable.getHexCoordinate());

                // Get state of duplicate PlayerBoard
                int numberOfAdjacentUniquePlacedWildlifeTokensToFox =
                        BoardStateAnalyseController.getNumberOfAdjacentUniquePlacedWildlifeTokensToFox(duplicatePlayerBoard, tileWithFoxPlaceable);


                // Get weight based on state
                FoxWeightValueMap foxWeightValueMap = new FoxWeightValueMap();
                double foxWeight = foxWeightValueMap.getWeightValue(numberOfAdjacentUniquePlacedWildlifeTokensToFox);

                // Set weight container based on weight
                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.FOX,
                        foxWeight
                );


            }
        }





        // List of selected tile/ghost/weight tile triples.
        ArrayList<Triple<HabitatTile, Tile, Double>> selectedTileGhostTileAndWeightTriple = new ArrayList<>();

        // Populate the list with all the selected tile/ghost tile pair and the weight of each pair (making it a triple)
        for (HabitatTile selectedTile: StartGame.selectedTiles) {
            for (Tile ghostTile: ghostTileList) {

                // Get the list of wildlife tokens that can be placed on the selected tile
                ArrayList<WildlifeToken.WildlifeTokenType> placeableWildlifeTokensOnSelectedTileList = new ArrayList<>(selectedTile.getWildlifeTokenTypeList());
                // Get WildlifeWeightContainer of ghost tile
                WildlifeTokenWeightContainer ghostTileWildlifeWeightContainer = ghostTileAndWildlifeWeightHash.get(ghostTile);
                // Get the combined weight of the wildlife tokens that can be placed on the selected tile
                double localWeight = ghostTileWildlifeWeightContainer.getCombinedWeightValue(placeableWildlifeTokensOnSelectedTileList);

                selectedTileGhostTileAndWeightTriple.add(new Triple<>(selectedTile, ghostTile, localWeight));
            }
        }

        // Get the triple with the largest weight value
        Triple<HabitatTile, Tile, Double> tripleWithLargestWeight = selectedTileGhostTileAndWeightTriple.get(0);
        for (Triple<HabitatTile, Tile, Double> triple: selectedTileGhostTileAndWeightTriple) {
            if (triple.getField3() > tripleWithLargestWeight.getField3()) {
                tripleWithLargestWeight = triple;
            }
        }

        HabitatTile optimalSelectedTile = tripleWithLargestWeight.getField1();
        HexCoordinate optimalHexCoordinatePosition = tripleWithLargestWeight.getField2().getHexCoordinate();

        return new CustomPair<>(optimalSelectedTile, optimalHexCoordinatePosition);
    }


    @Override
    public boolean playTurn() {
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
        System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));

        // Finds the most optimal token and its position to place from the selected tokens.
        CustomPair<WildlifeToken.WildlifeTokenType, HexCoordinate> wildlifeTokenTypeAndPositionToPlace = getOptimalWildlifeTokenTypeAndPositionToPlace();
        WildlifeToken.WildlifeTokenType wildlifeTokenTypeToPlace = wildlifeTokenTypeAndPositionToPlace.getField1();
        HexCoordinate wildlifeTokenPositionToPlace = wildlifeTokenTypeAndPositionToPlace.getField2();

        System.out.println(wildlifeTokenTypeToPlace);
        System.out.println(wildlifeTokenPositionToPlace);

        // Finds the most optimal tile and its position from the selected tiles.
        CustomPair<HabitatTile, HexCoordinate> habitatTileAndPositionToPlace = getOptimalHabitatTileAndPositionToPlace();
        HabitatTile habitatTileToPlace = habitatTileAndPositionToPlace.getField1();
        HexCoordinate habitatTilePositionToPlace = habitatTileAndPositionToPlace.getField2();



        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
