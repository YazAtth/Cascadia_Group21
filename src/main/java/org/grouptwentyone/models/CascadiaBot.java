package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.BoardStateAnalyseController;
import org.grouptwentyone.controllers.ScoringController;
import org.grouptwentyone.controllers.WeightController;
import org.grouptwentyone.dev.DebugController;
import org.grouptwentyone.models.WeightValueMaps.BearWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.ElkWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.FoxWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.HawkWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.SalmonWeightValueMap;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.SelectionOptionsView;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static org.grouptwentyone.controllers.HabitatTilesController.habitatTilesBag;
import static org.grouptwentyone.controllers.WildlifeTokensController.wildlifeTokenBag;


// NOTE: Program will sometimes crash until the elk, salmon and hawk reserve values are implemented.

public class CascadiaBot extends Player {

    static boolean displayBotActions = true;

    public CascadiaBot(String userName) {
        super(userName);
        this.getPlayerBoardObject().setVerbose(false);
    }

    private PriorityQueue<CustomPair<Tile, WildlifeTokenWeightContainer>> getOptimalWildlifeTokenTypeAndPositionToPlace() {
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
        //sort array by wildlife token weights, from greatest to least
        PriorityQueue<CustomPair<Tile, WildlifeTokenWeightContainer>> tileAndWeightPairs = new PriorityQueue<>(
                (o1, o2) -> o2.getField2().getLargestWildlifeWeightValue().getValue().compareTo(o1.getField2().getLargestWildlifeWeightValue().getValue())
        );

        // Loop through all the tiles that have been placed on the board and calculate the reserve values for each tile.
        for (Tile tile: placedTiles) {

            //check if token has already been placed on tile
            if (tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY)
                continue;

            // Run code to populate reserve values for each tile.
            // Only populate the tiles we need.

            ArrayList<WildlifeToken.WildlifeTokenType> tokenTypePossibilities = wildlifeTokenOptionList.stream()
                    .map(token -> token.getWildlifeTokenType())
                    .filter(token -> tile.getHabitatTile().getWildlifeTokenTypeList().contains(token))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (tokenTypePossibilities.size() < 1) continue;
            WildlifeTokenWeightContainer wildlifeTokenWeightContainer = new WildlifeTokenWeightContainer(tokenTypePossibilities);
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

//                System.out.printf("Elk Weight: %.2f, at tile %s\n", elkWeight, tile.getHexCoordinate());

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

            tileAndWeightPairs.add(new CustomPair<>(tile, wildlifeTokenWeightContainer));
        }


        // Identify the largest reserve value that is ALSO available in the wildlife token options
//        CustomPair<Tile, WildlifeTokenWeightContainer> bestTileWeightPair = tileAndWeightPairs.poll(); // Defaults to the first tile/reserve pair
//
//        // Loop through all the tile/reserve pairs and find the one with the largest reserve value.
//        for (CustomPair<Tile, WildlifeTokenWeightContainer> tileWeightPair: tileAndWeightPairs) {
//            boolean foundWeightValueLargerThanRecorded = tileWeightPair.getField2().getLargestWildlifeWeightValue().getValue() >
//                    bestTileWeightPair.getField2().getLargestWildlifeWeightValue().getValue();
//
//            if (foundWeightValueLargerThanRecorded) {
//                bestTileWeightPair = tileWeightPair;
//            }
//        }
//
//        // Extract the wildlife token type from the best tile/reserve pair and return it.
//        WildlifeToken.WildlifeTokenType wildlifeTokenTypeToPlace = bestTileWeightPair.getField2().getLargestWildlifeWeightValue().getKey();
//        HexCoordinate hexCoordinateToPlaceWildlifeToken = bestTileWeightPair.getField1().getHexCoordinate();
//
//        return new CustomPair<>(wildlifeTokenTypeToPlace, hexCoordinateToPlaceWildlifeToken);
        return tileAndWeightPairs;
    }

    public PriorityQueue<Triple<HabitatTile, Tile, Double>> getOptimalHabitatTileAndPositionToPlace() {

//        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
//        System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
//        System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));

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
                Tile tileWithFoxPlaceable = new Tile(new HabitatTile(true), new HexCoordinate(ghostTile.getHexCoordinate().getX(),
                        ghostTile.getHexCoordinate().getY()
                ));                duplicatePlayerBoard.setSelectedTile(tileWithFoxPlaceable.getHabitatTile());
                duplicatePlayerBoard.addNewTile(tileWithFoxPlaceable.getHexCoordinate());

                // Get state of duplicate PlayerBoard
                int numberOfAdjacentUniquePlacedWildlifeTokensToFox =
                        BoardStateAnalyseController.getNumberOfAdjacentUniquePlacedWildlifeTokensToFox(duplicatePlayerBoard, tileWithFoxPlaceable);


                // Get weight based on state
                FoxWeightValueMap foxWeightValueMap = new FoxWeightValueMap();
                double foxWeight = foxWeightValueMap.getWeightValue(numberOfAdjacentUniquePlacedWildlifeTokensToFox);

                if (ghostTile.getHabitatTile().isKeystone())
                    foxWeight += 1.2;

                // Set weight container based on weight
                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.FOX,
                        foxWeight
                );
            }

            if (wildlifeTokenOptionList.contains(WildlifeToken.WildlifeTokenType.ELK)) {

                // Duplicate PlayerBoard is needed to simulate placing a tile with an elk placeable on the ghost tile position.
                PlayerBoard duplicatePlayerBoard = this.getPlayerBoardObject().getDuplicate();
                Tile tileWithElkPlaceable = new Tile(new HabitatTile(true), new HexCoordinate(ghostTile.getHexCoordinate().getX(),
                        ghostTile.getHexCoordinate().getY()
                ));                duplicatePlayerBoard.setSelectedTile(tileWithElkPlaceable.getHabitatTile());
                duplicatePlayerBoard.addNewTile(tileWithElkPlaceable.getHexCoordinate());

                double elkWeight = 0;
                ElkWeightValueMap elkWeightValueMap = new ElkWeightValueMap();

                PriorityQueue<Integer> linesOfElk = BoardStateAnalyseController.getLinesOfElkFromPosition(duplicatePlayerBoard, tileWithElkPlaceable.getHexCoordinate());
                elkWeight = elkWeightValueMap.getWeightValue(linesOfElk);

                if (ghostTile.getHabitatTile().isKeystone())
                    elkWeight += 1.2;

//                System.out.printf("Elk Weight: %.2f, at tile %s\n", elkWeight, tile.getHexCoordinate());

                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.ELK,
                        elkWeight
                );
            }

            if (wildlifeTokenOptionList.contains(WildlifeToken.WildlifeTokenType.HAWK)) {

                // Duplicate PlayerBoard is needed to simulate placing a tile with a fox placeable on the ghost tile position.
                PlayerBoard duplicatePlayerBoard = this.getPlayerBoardObject().getDuplicate();
                Tile tileWithHawkPlaceable = new Tile(new HabitatTile(true), new HexCoordinate(ghostTile.getHexCoordinate().getX(),
                        ghostTile.getHexCoordinate().getY()
                ));                duplicatePlayerBoard.setSelectedTile(tileWithHawkPlaceable.getHabitatTile());
                duplicatePlayerBoard.addNewTile(tileWithHawkPlaceable.getHexCoordinate());

                double hawkWeight = 0;
                HawkWeightValueMap hawkWeightValueMap = new HawkWeightValueMap();

                // Get state of duplicate PlayerBoard
                int numberOfScorableHawkPairsBeforePlacingToken = BoardStateAnalyseController.getNumberOfScorableHawksBeforePlacingToken (
                        duplicatePlayerBoard
                );

                if (numberOfScorableHawkPairsBeforePlacingToken < 8 &&
                        !BoardStateAnalyseController.doesHawkPlacementMakeAdjacentHawks(duplicatePlayerBoard, ghostTile.getHexCoordinate())) {
                    hawkWeight = hawkWeightValueMap.getWeightValue(numberOfScorableHawkPairsBeforePlacingToken + 1);

                    if (ghostTile.getHabitatTile().isKeystone())
                        hawkWeight += 1.2;
                }

                // Set weight container based on weight
                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.HAWK,
                        hawkWeight
                );
            }

            if (wildlifeTokenOptionList.contains(WildlifeToken.WildlifeTokenType.BEAR)) {

                // Duplicate PlayerBoard is needed to simulate placing a tile with a fox placeable on the ghost tile position.
                PlayerBoard duplicatePlayerBoard = this.getPlayerBoardObject().getDuplicate();
                Tile tileWithBearPlaceable = new Tile(new HabitatTile(true), new HexCoordinate(ghostTile.getHexCoordinate().getX(),
                        ghostTile.getHexCoordinate().getY()
                ));
                duplicatePlayerBoard.setSelectedTile(tileWithBearPlaceable.getHabitatTile());
                duplicatePlayerBoard.addNewTile(tileWithBearPlaceable.getHexCoordinate());
                double bearWeight = 0;
                BearWeightValueMap bearWeightValueMap = new BearWeightValueMap();

                // Get state of duplicate PlayerBoard
                int numberOfBearPairsAfterPlacingToken = BoardStateAnalyseController.getNumberOfBearPairsBeforePlacingToken (
                        duplicatePlayerBoard
                );

                numberOfBearPairsAfterPlacingToken += 1;
                bearWeight = bearWeightValueMap.getWeightValue(numberOfBearPairsAfterPlacingToken); // Get weight value for that pair from the table.

                // If placing bear makes a pair, get the weight value for that pair.
                boolean doesPlacingBearMakePair = BoardStateAnalyseController.doesPlacingBearMakePair(duplicatePlayerBoard, ghostTile.getHexCoordinate());
                if (!doesPlacingBearMakePair) {
                    bearWeight -= WeightController.WeightConstants.nonPairBearPlacementReduction;
                }

                // Custom weight if a bear pair gets ruined.
                boolean doesPlacingBearRuinPair = BoardStateAnalyseController.doesPlacingBearRuinPair(duplicatePlayerBoard, ghostTile.getHexCoordinate());
                if (doesPlacingBearRuinPair) bearWeight = bearWeightValueMap.ruinsPairWeight();

                if (ghostTile.getHabitatTile().isKeystone())
                    bearWeight += 1.2;

                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.BEAR,
                        bearWeight
                );
            }

            if (wildlifeTokenOptionList.contains(WildlifeToken.WildlifeTokenType.SALMON)) {

                // Duplicate PlayerBoard is needed to simulate placing a tile with a fox placeable on the ghost tile position.
                PlayerBoard duplicatePlayerBoard = this.getPlayerBoardObject().getDuplicate();
                Tile tileWithSalmonPlaceable = new Tile(new HabitatTile(true), new HexCoordinate(ghostTile.getHexCoordinate().getX(),
                        ghostTile.getHexCoordinate().getY()
                ));                duplicatePlayerBoard.setSelectedTile(tileWithSalmonPlaceable.getHabitatTile());
                duplicatePlayerBoard.addNewTile(tileWithSalmonPlaceable.getHexCoordinate());

                double salmonWeight = 0;
                SalmonWeightValueMap salmonWeightValueMap = new SalmonWeightValueMap();

                ArrayList<Tile> salmonInRun = new ArrayList<>();

                int lengthOfSalmonRun = BoardStateAnalyseController.getLengthOfRunTileIsIn(ghostTile, duplicatePlayerBoard, salmonInRun);
                salmonWeight = salmonWeightValueMap.getWeightValue(lengthOfSalmonRun);

                if (ghostTile.getHabitatTile().isKeystone())
                    salmonWeight += 1.2;

                wildlifeTokenWeightContainer.setWildlifeWeight(
                        WildlifeToken.WildlifeTokenType.SALMON,
                        salmonWeight
                );
            }
        }



        // List of selected tile/ghost/weight tile triples, with front of queue being greatest value
        PriorityQueue<Triple<HabitatTile, Tile, Double>> selectedTileGhostTileAndWeightTriple = new PriorityQueue<>((o1, o2) -> o2.getField3().compareTo(o1.getField3()));


        Collections.shuffle(ghostTileList); // Stops bot from placing lines of tiles heading to top left corner

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

//        // Get the triple with the largest weight value
//        Triple<HabitatTile, Tile, Double> tripleWithLargestWeight = selectedTileGhostTileAndWeightTriple.poll();
//        for (Triple<HabitatTile, Tile, Double> triple: selectedTileGhostTileAndWeightTriple) {
//            if (triple.getField3() > tripleWithLargestWeight.getField3()) {
//                tripleWithLargestWeight = triple;
//            }
//        }
//
//        HabitatTile optimalSelectedTile = tripleWithLargestWeight.getField1();
//        HexCoordinate optimalHexCoordinatePosition = tripleWithLargestWeight.getField2().getHexCoordinate();
//
//        return new CustomPair<>(optimalSelectedTile, optimalHexCoordinatePosition);
        return selectedTileGhostTileAndWeightTriple;
    }


    @Override
    public boolean playTurn() {
//        System.out.println("\n\nStarted playTurn()");
//        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
//        System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));


        // Get list of habitat tiles and their positions that can be placed on the board
        PriorityQueue<Triple<HabitatTile, Tile, Double>> listOfHabitatAndPositionOptions = getOptimalHabitatTileAndPositionToPlace();

        // From listofHabitatAndPositions get the 4 distinct habitat tile triple with the highest weight value
        ArrayList<Triple<HabitatTile, Tile, Double>> chosenHabitatTriples = new ArrayList<>();
        ArrayList<HabitatTile> selectedHabitatTileList = new ArrayList<>(StartGame.selectedTiles);
        while (listOfHabitatAndPositionOptions.size() > 0 || selectedHabitatTileList.size() > 0) {
            // Keep removing from listOfHabitatAndPositionOptions until it is empty.
            Triple<HabitatTile, Tile, Double> habitatTriple = listOfHabitatAndPositionOptions.poll();

            // If a habitat tile is found that exists inside of selectedHabitatTileList, add it to chosenHabitatTriples and remove it from selectedHabitatTileList
            if (selectedHabitatTileList.contains(habitatTriple.getField1())) {
                chosenHabitatTriples.add(habitatTriple);
                selectedHabitatTileList.remove(habitatTriple.getField1());
            }
        }

        // Get list of tokens and their positions that can be placed on the board
        ArrayList<CustomPair<Tile, WildlifeTokenWeightContainer>> tokenPairs = new ArrayList<>(getOptimalWildlifeTokenTypeAndPositionToPlace());

        // Pair up the four selected habitat tile with an optimal token that can be placed on it.
        ArrayList<Triple<Triple<HabitatTile, Tile, Double>, CustomPair<Tile, WildlifeTokenWeightContainer>, WildlifeToken.WildlifeTokenType>>
                chosenHabitatTileAndTokenPairList = new ArrayList<>();

        for (int i=0; i<4; i++) {
            Triple<HabitatTile, Tile, Double> focusedHabitatTriple = chosenHabitatTriples.get(i);
            int index = StartGame.selectedTiles.indexOf(focusedHabitatTriple.getField1());
            WildlifeToken.WildlifeTokenType requiredWildlifeTokenType = StartGame.selectedTokens.get(index).getWildlifeTokenType();

            // Ensure the list of tokens can all be placed on the habitat tile
            ArrayList<CustomPair<Tile, WildlifeTokenWeightContainer>> validTokenPairs = tokenPairs
                    .stream()
                    .filter(tokenPair -> tokenPair.getField1().getHabitatTile().getWildlifeTokenTypeList().contains(requiredWildlifeTokenType))
                    .collect(Collectors.toCollection(ArrayList::new));

//            System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
//            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
//            System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));
//            System.out.println("Looking at placing " + focusedHabitatTriple.getField1() + " at " + focusedHabitatTriple.getField2().getHexCoordinate().toString());
//            System.out.println(requiredWildlifeTokenType);
//            System.out.println(tokenPairs);
//            System.out.println(validTokenPairs);

            //TODO: In the case where you can place the tile but there is nowhere for the token to be placed:
            // validTokenPairs is empty and we get an index out of bounds exception in the following line.

            CustomPair<Tile, WildlifeTokenWeightContainer> largestWeightTokenPair =
                    new CustomPair<>(null, new WildlifeTokenWeightContainer(new ArrayList<>(
                            Arrays.asList(WildlifeToken.WildlifeTokenType.FOX,
                                    WildlifeToken.WildlifeTokenType.BEAR,
                                    WildlifeToken.WildlifeTokenType.ELK,
                                    WildlifeToken.WildlifeTokenType.HAWK,
                                    WildlifeToken.WildlifeTokenType.SALMON
                            )
                    )));

            if (validTokenPairs.size() != 0) {
                largestWeightTokenPair = validTokenPairs.get(0);
                for (CustomPair<Tile, WildlifeTokenWeightContainer> tokenPair : validTokenPairs) {
                    if (tokenPair.getField2().getWeightOfSpecificAnimal(requiredWildlifeTokenType) > largestWeightTokenPair.getField2().getWeightOfSpecificAnimal(requiredWildlifeTokenType)) {
                        largestWeightTokenPair = tokenPair;
                    }
                }
            }

            chosenHabitatTileAndTokenPairList.add(new Triple<>(focusedHabitatTriple, largestWeightTokenPair, requiredWildlifeTokenType));
        }



        // Get optimal habitat tile and wildlife token pairing
        int largestWeightPairIndex = 0;
        double largestTokenPairingWeight = -1;
        for (Triple<Triple<HabitatTile, Tile, Double>, CustomPair<Tile, WildlifeTokenWeightContainer>, WildlifeToken.WildlifeTokenType>
                habitatTileAndTokenPair: chosenHabitatTileAndTokenPairList) {
            Triple<HabitatTile, Tile, Double> habitatTriple = habitatTileAndTokenPair.getField1();
            CustomPair<Tile, WildlifeTokenWeightContainer> tokenPair = habitatTileAndTokenPair.getField2();
            WildlifeToken.WildlifeTokenType requiredWildlifeTokenType = habitatTileAndTokenPair.getField3();

            // Optimality is determined by the sum of the weight of the habitat tile and the weight of the token that's required to be placed on it
            double tileTokenPairingWeight = habitatTriple.getField3() + tokenPair.getField2().getWeightOfSpecificAnimal(requiredWildlifeTokenType);
            if (tileTokenPairingWeight > largestTokenPairingWeight) {
                largestTokenPairingWeight = tileTokenPairingWeight;
                largestWeightPairIndex = chosenHabitatTileAndTokenPairList.indexOf(habitatTileAndTokenPair);
            }
        }


        HabitatTile optimalHabitatTile = chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField1().getField1();




        HexCoordinate optimalHabitatTilePosition = chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField1().getField2().getHexCoordinate();

        this.getPlayerBoardObject().setSelectedTile(optimalHabitatTile);
        this.getPlayerBoardObject().addNewTile(optimalHabitatTilePosition);

        if (chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField2().getField1() != null) {
//            System.out.println("Placing tokens");
            WildlifeToken optimalWildlifeToken = new WildlifeToken(chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField3());
            HexCoordinate optimalWildlifeTokenPosition = chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField2().getField1().getHexCoordinate();

            this.getPlayerBoardObject().setSelectedToken(optimalWildlifeToken);
            this.getPlayerBoardObject().addNewToken(optimalWildlifeTokenPosition);

            StartGame.selectedTokens.remove(StartGame.selectedTiles.indexOf(optimalHabitatTile));
        } else {
//            System.out.println("Not placing tokens");
            StartGame.selectedTokens.remove(StartGame.selectedTiles.indexOf(optimalHabitatTile));
        }
        StartGame.selectedTiles.remove(optimalHabitatTile);

        StartGame.tilesRemain = SelectionOptionsView.replaceTileAndToken();
        this.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));
        this.getPlayerBoardObject().setSelectedTile(new HabitatTile());






//        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
//        System.out.println(StartGame.selectedTiles);
//        System.out.println(StartGame.selectedTokens);
//
//        System.out.printf("Placed %s at position %s\n", optimalHabitatTile, optimalHabitatTilePosition);
//        System.out.printf("Places %s at position %s\n", optimalWildlifeToken, optimalWildlifeTokenPosition);





































        // YASITH'S CODE BELOW


//
//        // Match each of the four habitat tile triples to a wildlife token and position to place it that has the highest weight
//
//        PriorityQueue<CustomPair<Tile, WildlifeTokenWeightContainer>> optimalWildlifeTokenTypeAndPositionToPlaceList = getOptimalWildlifeTokenTypeAndPositionToPlace();
//        System.out.println("Token and position list " + optimalWildlifeTokenTypeAndPositionToPlaceList);
//
//        // listOfTokenAndPositionOptions can be empty if the tokens the tokens in selectedTokens are not placeable on anything on the board.
//        // In this case, we just place the tile and remove the token in the corresponding index in selectedTokens.
//        if (optimalWildlifeTokenTypeAndPositionToPlaceList.size() == 0) {
//            Triple<HabitatTile, Tile, Double> bestHabitatTileTriple = listOfHabitatAndPositionOptions.poll();
//            HabitatTile bestHabitatTile = bestHabitatTileTriple.getField1();
//            HexCoordinate bestTileCoord = bestHabitatTileTriple.getField2().getHexCoordinate();
//
//            StartGame.selectedTokens.remove(StartGame.selectedTiles.indexOf(bestHabitatTile));
//            this.getPlayerBoardObject().setSelectedTile(bestHabitatTile);
//            StartGame.selectedTiles.remove(bestHabitatTile);
//            this.getPlayerBoardObject().addNewTile(bestTileCoord);
//        }
//
//
//        ArrayList<CustomPair<Triple<HabitatTile, Tile, Double>, CustomPair<Tile, WildlifeTokenWeightContainer>>> chosenHabitatTileAndTokenPairList = new ArrayList<>();
//
//        for (int i=0; i<4; i++) {
//            Triple<HabitatTile, Tile, Double> habitatTriple = chosenHabitatTriples.get(i);
//            WildlifeToken.WildlifeTokenType requiredWildlifeTokenType = StartGame.selectedTokens.get(i).getWildlifeTokenType();
//
//            PriorityQueue<CustomPair<Tile, WildlifeTokenWeightContainer>> optimalWildlifeTokenTypeAndPositionToPlaceListCopy = new PriorityQueue<>(optimalWildlifeTokenTypeAndPositionToPlaceList);
//
//            CustomPair<Tile, WildlifeTokenWeightContainer> tokenPair = optimalWildlifeTokenTypeAndPositionToPlaceListCopy.poll();
//            WildlifeToken.WildlifeTokenType chosenWildlifeTokenType = tokenPair.getField2().getLargestWildlifeWeightValue().getKey();
//
//            System.out.println("Required wildlifetokentype: " + requiredWildlifeTokenType);
//
//            //TODO: Bug very likely to happen here
//            while (!(chosenWildlifeTokenType.equals(requiredWildlifeTokenType))) {
//                tokenPair = optimalWildlifeTokenTypeAndPositionToPlaceListCopy.poll();
//                chosenWildlifeTokenType = tokenPair.getField2().getLargestWildlifeWeightValue().getKey();
//            }
//
//            chosenHabitatTileAndTokenPairList.add(new CustomPair<>(habitatTriple, tokenPair));
//
//        }
//
//
//        for (CustomPair<Triple<HabitatTile, Tile, Double>, CustomPair<Tile, WildlifeTokenWeightContainer>> pair: chosenHabitatTileAndTokenPairList) {
//            Triple<HabitatTile, Tile, Double> habitatTriple = pair.getField1();
//            CustomPair<Tile, WildlifeTokenWeightContainer> tokenPair = pair.getField2();
//
//            System.out.printf("Placed %s tile on position %s\n", habitatTriple.getField1(), habitatTriple.getField2().getHexCoordinate());
//            System.out.printf("Places %s on position %s\n", tokenPair.getField1(), tokenPair.getField2().getLargestWildlifeWeightValue().getValue());
//        }










// COLMS CODE BELOW




//        Triple<HabitatTile, Tile, Double> bestHabitatAndPositionOption = listOfHabitatAndPositionOptions.poll();
//        HabitatTile bestHabitatTile = bestHabitatAndPositionOption.getField1();
//        HexCoordinate bestTileCoord = bestHabitatAndPositionOption.getField2().getHexCoordinate();
//
////        find best token option
//        PriorityQueue<CustomPair<Tile, WildlifeTokenWeightContainer>> listOfTokenAndPositionOptions = getOptimalWildlifeTokenTypeAndPositionToPlace();
//        CustomPair<Tile, WildlifeTokenWeightContainer> bestTokenAndPosition = listOfTokenAndPositionOptions.poll();
//
//        DebugController.printUserTrace(this, "Tile Priority Queue: %s", listOfHabitatAndPositionOptions);
//        DebugController.printUserTrace(this, "Token Priority Queue: %s", listOfTokenAndPositionOptions);
//        DebugController.printUserTrace(this, "....");
//
//
//
//
//
//
//        if (bestTokenAndPosition != null) { // If listOfTokenAndPositionOptions is not empty
//            WildlifeToken bestToken = new WildlifeToken(bestTokenAndPosition.getField2().getLargestWildlifeWeightValue().getKey());
//            HexCoordinate bestTokenCoord = bestTokenAndPosition.getField1().getHexCoordinate();
//
//            DebugController.printUserTrace(this, "Selected Tiles: %s", StartGame.selectedTiles);
//            DebugController.printUserTrace(this, "Selected Tokens: %s", StartGame.selectedTokens);
//
//            //place both tile and token
//            this.getPlayerBoardObject().setSelectedTile(bestHabitatTile);
//            StartGame.selectedTiles.remove(bestHabitatTile);
//            this.getPlayerBoardObject().addNewTile(bestTileCoord);
//            this.getPlayerBoardObject().setSelectedToken(bestToken);
//            this.getPlayerBoardObject().addNewToken(bestTokenCoord);
//
//
//            DebugController.printUserTrace(this, "Placed %s at %s and %s token at %s", bestHabitatTile, bestTileCoord, bestToken.getWildlifeTokenType(), bestTokenCoord);
//            DebugController.printUserTrace(this,"\n\n");
//
//            //attempt to remove token from token selection
////            System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
//            boolean check = true;
//            for (WildlifeToken token : StartGame.selectedTokens) {
//                if (token.getWildlifeTokenType() == bestToken.getWildlifeTokenType()) {
//                    StartGame.selectedTokens.remove(token);
////                    System.out.println("token removed");
//                    check = false;
//                    break;
//                }
//            }
//            if (check) throw new IllegalStateException(String.format("token %s, not removed", bestToken.getWildlifeTokenType()));
//        } else {
//            // listOfTokenAndPositionOptions can be empty if the tokens the tokens in selectedTokens are not placeable on anything on the board.
//            // In this case, we just place the tile and remove the token in the corresponding index in selectedTokens.
//            StartGame.selectedTokens.remove(StartGame.selectedTiles.indexOf(bestHabitatTile));
//            this.getPlayerBoardObject().setSelectedTile(bestHabitatTile);
//            StartGame.selectedTiles.remove(bestHabitatTile);
//            this.getPlayerBoardObject().addNewTile(bestTileCoord);
//        }
//        StartGame.tilesRemain = SelectionOptionsView.replaceTileAndToken();



        // Finds the most optimal token and its position to place from the selected tokens.
//        CustomPair<WildlifeToken.WildlifeTokenType, HexCoordinate> wildlifeTokenTypeAndPositionToPlace = getOptimalWildlifeTokenTypeAndPositionToPlace();
//        WildlifeToken.WildlifeTokenType wildlifeTokenTypeToPlace = wildlifeTokenTypeAndPositionToPlace.getField1();
//        HexCoordinate wildlifeTokenPositionToPlace = wildlifeTokenTypeAndPositionToPlace.getField2();
//
//        System.out.println(wildlifeTokenTypeToPlace);
//        System.out.println(wildlifeTokenPositionToPlace);

//        // Finds the most optimal tile and its position from the selected tiles.
//        CustomPair<HabitatTile, HexCoordinate> habitatTileAndPositionToPlace = getOptimalHabitatTileAndPositionToPlace();
//        HabitatTile habitatTileToPlace = habitatTileAndPositionToPlace.getField1();
//        HexCoordinate habitatTilePositionToPlace = habitatTileAndPositionToPlace.getField2();



        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
