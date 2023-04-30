package org.grouptwentyone.models;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.BoardStateAnalyseController;
import org.grouptwentyone.controllers.WeightController;
import org.grouptwentyone.dev.DebugController;
import org.grouptwentyone.models.WeightValueMaps.BearWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.ElkWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.FoxWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.HawkWeightValueMap;
import org.grouptwentyone.models.WeightValueMaps.SalmonWeightValueMap;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.GameUiView;
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

    @Override
    public boolean playTurn() {
        //record the time that the bot starts playing its move
        long startTime = System.currentTimeMillis();


        Triple<CustomPair<HabitatTile, HexCoordinate>, CustomPair<WildlifeToken, HexCoordinate>, Boolean> placements =
                placeOptimalHabitatTileAndWildlifeToken();
        HabitatTile placedHabitatTile = placements.getField1().getField1();
        HexCoordinate placedWildlifeTokenPosition = placements.getField1().getField2();

        boolean wildlifeTokenGotPlaced = true;
        WildlifeToken.WildlifeTokenType placedWildlifeTokenType = null;
        HexCoordinate placedHabitatTilePosition = null;
        try {
            placedWildlifeTokenType = placements.getField2().getField1().getWildlifeTokenType();
            placedHabitatTilePosition = placements.getField2().getField2();
        } catch (NullPointerException e) {
            wildlifeTokenGotPlaced = false;
        }


        // Display the bots actions to the user. Will not display if the user has requested the feature be turned off.
        if (displayBotActions) {
            // Displays the bots playerboard
            GameUiView.printPlayerHeader(this);
            System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));

            // String to display the habitats of the habitat tiles
            StringBuilder placedHabitatTileBiomes = new StringBuilder();
            if (placedHabitatTile.getHabitatTileTypeList().size() == 1) {
                placedHabitatTileBiomes.append(placedHabitatTile.getHabitatTileTypeList().get(0));
            } else {
                placedHabitatTileBiomes.append(placedHabitatTile.getHabitatTileTypeList().get(0));
                placedHabitatTileBiomes.append(" & ");
                placedHabitatTileBiomes.append(placedHabitatTile.getHabitatTileTypeList().get(1));
            }

            long endTime = System.currentTimeMillis();

            System.out.printf("""
                            STATS
                            -----------------------------------------
                            - placed a %s habitat tile on position %s.
                            - %s                                 \s
                            Score       |    %d points
                            Time Taken  |    %d milliseconds
                            
                            """,

                    placedHabitatTileBiomes,
                    placedHabitatTilePosition,
                    wildlifeTokenGotPlaced?
                            String.format("placed a %s wildlife token on position %s", placedWildlifeTokenType, placedWildlifeTokenPosition)
                            :
                            "did not place wildlife token (nowhere to place)",
                    this.getScore(),
                    (endTime - startTime)
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

        return tileAndWeightPairs;
    }

    private PriorityQueue<Triple<HabitatTile, Tile, Double>> getOptimalHabitatTileAndPositionToPlace() {


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

        return selectedTileGhostTileAndWeightTriple;
    }

    public Triple<CustomPair<HabitatTile, HexCoordinate>, CustomPair<WildlifeToken, HexCoordinate>, Boolean> placeOptimalHabitatTileAndWildlifeToken() {
        //create variable that will hold the return variable
        Triple<CustomPair<HabitatTile, HexCoordinate>, CustomPair<WildlifeToken, HexCoordinate>, Boolean> placements = null;
        Boolean natureTokenSpent = false;

        //will always try use nature token to choose any combination of tile and token as it will yield a higher score
        if (this.getPlayerBoardObject().getNumOfNatureTokens() > 0) {
            // Get list of habitat tiles and their positions that can be placed on the board
            PriorityQueue<Triple<HabitatTile, Tile, Double>> listOfHabitatAndPositionOptions = getOptimalHabitatTileAndPositionToPlace();

            // Get list of tokens and their positions that can be placed on the board
            PriorityQueue<CustomPair<Tile, WildlifeTokenWeightContainer>> listOfTokenAndPositionOptions = getOptimalWildlifeTokenTypeAndPositionToPlace();

            Triple<HabitatTile, Tile, Double> optimalHabitatTileAndPosition = listOfHabitatAndPositionOptions.poll();
            CustomPair<Tile, WildlifeTokenWeightContainer> optimalWildlifeTokenAndPosition = listOfTokenAndPositionOptions.poll();

            //spend nature token if they don't match in selection
            int habitatTileIndexInSelection = StartGame.selectedTiles.indexOf(optimalHabitatTileAndPosition.getField1());
            WildlifeToken.WildlifeTokenType optimalTokenType = optimalWildlifeTokenAndPosition.getField2().getLargestWildlifeWeightValue().getKey();
            boolean tileAndTokenMatch = StartGame.selectedTokens.get(habitatTileIndexInSelection).getWildlifeTokenType()
                    .equals(optimalTokenType);
            if (!tileAndTokenMatch) {
                this.spendNatureToken();
                natureTokenSpent = true;
            }

            //place tile
            this.getPlayerBoardObject().setSelectedTile(optimalHabitatTileAndPosition.getField1());
            HexCoordinate tileCoord = optimalHabitatTileAndPosition.getField2().getHexCoordinate();
            this.getPlayerBoardObject().addNewTile(tileCoord);
            //place token
            this.getPlayerBoardObject().setSelectedToken(new WildlifeToken(optimalTokenType));
            HexCoordinate tokenCoord = optimalWildlifeTokenAndPosition.getField1().getHexCoordinate();
            this.getPlayerBoardObject().addNewToken(tokenCoord);

            //remove tile/token from future selection
            if (tileAndTokenMatch) {
                StartGame.selectedTokens.remove(StartGame.selectedTiles.indexOf(optimalHabitatTileAndPosition.getField1()));
                StartGame.selectedTiles.remove(optimalHabitatTileAndPosition.getField1());
            } else {
                StartGame.selectedTiles.remove(optimalHabitatTileAndPosition.getField1());
                for (WildlifeToken token: StartGame.selectedTokens) {
                    if (token.getWildlifeTokenType().equals(optimalTokenType)) {
                        StartGame.selectedTokens.remove(token);
                        break;
                    }
                }
            }

            placements = new Triple<>(new CustomPair<>(optimalHabitatTileAndPosition.getField1(), tileCoord),
                            new CustomPair<>(new WildlifeToken(optimalTokenType), tokenCoord), natureTokenSpent);
        } else {

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

            for (int i = 0; i < 4; i++) {
                Triple<HabitatTile, Tile, Double> focusedHabitatTriple = chosenHabitatTriples.get(i);
                int index = StartGame.selectedTiles.indexOf(focusedHabitatTriple.getField1());
                WildlifeToken.WildlifeTokenType requiredWildlifeTokenType = StartGame.selectedTokens.get(index).getWildlifeTokenType();

                // Ensure the list of tokens can all be placed on the habitat tile
                ArrayList<CustomPair<Tile, WildlifeTokenWeightContainer>> validTokenPairs = tokenPairs
                        .stream()
                        .filter(tokenPair -> tokenPair.getField1().getHabitatTile().getWildlifeTokenTypeList().contains(requiredWildlifeTokenType))
                        .collect(Collectors.toCollection(ArrayList::new));

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
                    habitatTileAndTokenPair : chosenHabitatTileAndTokenPairList) {
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

            WildlifeToken optimalWildlifeToken = null;
            HexCoordinate optimalWildlifeTokenPosition = null;
            if (chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField2().getField1() != null) {
                optimalWildlifeToken = new WildlifeToken(chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField3());
                optimalWildlifeTokenPosition = chosenHabitatTileAndTokenPairList.get(largestWeightPairIndex).getField2().getField1().getHexCoordinate();

                this.getPlayerBoardObject().setSelectedToken(optimalWildlifeToken);
                this.getPlayerBoardObject().addNewToken(optimalWildlifeTokenPosition);

                StartGame.selectedTokens.remove(StartGame.selectedTiles.indexOf(optimalHabitatTile));
            } else {
                StartGame.selectedTokens.remove(StartGame.selectedTiles.indexOf(optimalHabitatTile));
            }
            StartGame.selectedTiles.remove(optimalHabitatTile);

            placements = new Triple<>(new CustomPair<>(optimalHabitatTile, optimalHabitatTilePosition),
                            new CustomPair<>(optimalWildlifeToken, optimalWildlifeTokenPosition), false);
        }

        StartGame.tilesRemain = SelectionOptionsView.replaceTileAndToken();
        this.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY));
        this.getPlayerBoardObject().setSelectedTile(new HabitatTile());


        return placements;
    }
}
