package org.grouptwentyone.dev;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.controllers.StarterHabitatTilesController;
import org.grouptwentyone.controllers.WeightController;
import org.grouptwentyone.controllers.WildlifeTokensController;
import org.grouptwentyone.models.*;
import org.grouptwentyone.models.WeightValueMaps.FoxWeightValueMap;
import org.grouptwentyone.views.*;

import java.util.ArrayList;
import java.util.List;

import static org.grouptwentyone.controllers.HabitatTilesController.createBagOfHabitatTiles;
import static org.grouptwentyone.controllers.StarterHabitatTilesController.createBagOfStarterHabitatTiles;
import static org.grouptwentyone.controllers.WildlifeTokensController.createBagOfWildlifeTokens;

public class TestingSpaceYasith {

    public static void testingCascadiaBot1() {

        ArrayList<Player> playerList = new ArrayList<>(List.of(
//                new Player("Colm"),
                new CascadiaBot("CascadiaBot")
//                new Player("Dom"),
//                new Player("Yasith")
        ));

        PlayerManager playerManager = new PlayerManager(playerList);
        Player activePlayer = playerManager.getFirstPlayer();

//        while (true) {


        for (int i = 0; i < 5; i++) {

//            if (!activePlayer.playTurn()) {
//                break;
//            }
            System.out.printf("TURN %d\n", i);
            activePlayer.playTurn();
//            GameUiView.printLargeSpace();
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
            GameUiView.printPageBorder();

//            break;


//            activePlayer = playerManager.cycleToNextPlayer();
        }
    }

    public static void testingEquals1() {

        WildlifeToken w1 = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);
        WildlifeToken w2 = new WildlifeToken(WildlifeToken.WildlifeTokenType.EMPTY);

        System.out.println(w1.equals(w2));
    }

    public static void testingDuplication() {
        CascadiaBot cb = new CascadiaBot("CascadiaBot");

        PlayerBoard pb1 = cb.getPlayerBoardObject();
        PlayerBoard pb2 = pb1.getDuplicate();

        Tile t1 = pb1.getPlayerBoardAs2dArray().get(1).get(2);
        Tile t2 = pb2.getPlayerBoardAs2dArray().get(1).get(2);

        HabitatTile h1 = pb1.getPlayerBoardAs2dArray().get(1).get(2).getHabitatTile();
        HabitatTile h2 = pb2.getPlayerBoardAs2dArray().get(1).get(2).getHabitatTile();

//        System.out.println(pb1.equals(pb2));

//        System.out.println(BoardView.displayTiles(pb2));
//        System.out.println(h1.equals(h2));

//        for (int i=0; i<4; i++) {
//            for (int k=0; k<4; k++) {
//                System.out.printf("%s\nAND\n%s\nIS_EQUAL: %s\n\n",
//                        pb1.getPlayerBoard().get(i).get(k),
//                        pb2.getPlayerBoard().get(i).get(k),
//                        pb1.getPlayerBoard().get(i).get(k).equals(pb2.getPlayerBoard().get(i).get(k)));
//            }
//        }

        System.out.println(pb1.getPlayerBoardAs2dArray());
        System.out.println(pb2.getPlayerBoardAs2dArray());

    }

    public static void testingSetBotPage() {
//        ArrayList<Player> playerList = new ArrayList<>(List.of(
//                new Player("Colm"),
//                new CascadiaBot("CascadiaBot"),
//                new Player("Dom"),
//                new Player("Yasith")
//        ));

//        GameSetupView.setBots(playerList);

//        System.out.println(playerList);

//        ArrayList<Player> playerList = GameSetupView.getPlayerInformationFromUser(4);
//
//        for (Player player : playerList) {
//            System.out.println(player.getClass().getSimpleName());
//        }

    }

    public static void foxWeightValueMapTest() {

        FoxWeightValueMap foxWeightValueMap = new FoxWeightValueMap();

    }

    public static void testingPlacingFoxTokens() {

        CascadiaBot p1 = new CascadiaBot("Ton");

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 3));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 2));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 3));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));

        p1.playTurn();


    }


    public static void testingPlacingBearTokens() {

        CascadiaBot p1 = new CascadiaBot("Ton");

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));

//        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
//        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,3));
//
//        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
//        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,2));
//
        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 2));
//
        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 2));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));

        p1.playTurn();


    }

    public static void testingPlacingHawksTokens() {

        CascadiaBot p1 = new CascadiaBot("Ton");

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));

//        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
//        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,3));
//
//        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
//        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,2));
//
        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 2));
//
        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 2));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));

        p1.playTurn();


    }


    public static void testingPlacingTileAlgo() {
        CascadiaBot p1 = new CascadiaBot("Ton");

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

//        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
//        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 3));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3, 2));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 3));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 2));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2, 3));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 3));

        //Colm: commented out due to changes to core code
//        CustomPair<HabitatTile, HexCoordinate> habitatTileAndPositionToPlace = p1.getOptimalHabitatTileAndPositionToPlace();
//        HabitatTile habitatTileToPlace = habitatTileAndPositionToPlace.getField1();
//        HexCoordinate habitatTilePositionToPlace = habitatTileAndPositionToPlace.getField2();
//
//        System.out.println(habitatTileToPlace);
//        System.out.println(habitatTilePositionToPlace);
    }

    public static void salmontesting() {
        CascadiaBot p1 = new CascadiaBot("Ton");

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1,0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2, 1));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(2,0));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,3));

        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(3,2));

//        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
//        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,3));
//
//        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
//        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1,2));
//
        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3, 2));
//
        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(1, 2));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(3,3));

        p1.getPlayerBoardObject().setSelectedToken(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
        p1.getPlayerBoardObject().addNewToken(new HexCoordinate(2,3));

        p1.playTurn();


    }

    private static int runGameToGetStats() {

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(new CascadiaBot("Dom"));
        playerList.add(new CascadiaBot("Colm"));

        PlayerManager playerManager = new PlayerManager(playerList);
        playerManager.shufflePlayerList();

        //remove habitat tiles depending on number of players
        int tilesToRemove = (((2-4)*-1)*20)+2;
        if (tilesToRemove > 0)
            HabitatTilesController.habitatTilesBag.subList(0, tilesToRemove).clear();

        Player activePlayer = playerManager.getFirstPlayer();
        CascadiaBot.displayBotActions = false;

        while (StartGame.tilesRemain) {

//            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
//            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
//            System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));

            // If the user wants to quit the game: playTurn() returns false which breaks the loop
            // otherwise it ends with returning true.
            if (!activePlayer.playTurn()) {
                break;
            }

            //next player
//            System.out.println("Moving to next player");
            activePlayer = playerManager.cycleToNextPlayer();
//            GameUiView.printLargeSpace();
        }

//        System.out.println("No tiles remain so play is finished, calculating player score...");

//        playerManager.tallyUpAllScores();
//        ScoreDisplayView.displayScorePage(playerManager);

        int sumOfScores = 0;
        for (Player player : playerList) {
            sumOfScores += player.getScore();
        }
        int scorePerPlayer = sumOfScores / playerList.size();

        return scorePerPlayer;

//        System.out.println(scorePerPlayer);
//        System.out.println(BoardView.displayTiles(playerManager.getPlayerList().get(0).getPlayerBoardObject()));;


    }

    private static CustomPair<CustomPair<Double, Double>, CustomPair<Double, Double>> getGameStatsAcrossMultipleGames(int numberOfGames, boolean verbose) {

        ArrayList<Long> timeTakenPerGameList = new ArrayList<>();
        ArrayList<Integer> perPlayerScorePerGameList = new ArrayList<>();

        for (int i=0; i<numberOfGames; i++) {
            DebugController.setDebugMode(true);

            long startTime = System.currentTimeMillis();

            int scorePerPlayer = runGameToGetStats();
            HabitatTilesController.habitatTilesBag = createBagOfHabitatTiles();
            StarterHabitatTilesController.starterHabitatTilesBag = createBagOfStarterHabitatTiles();
            WildlifeTokensController.wildlifeTokenBag = createBagOfWildlifeTokens();
            StartGame.selectedTiles = SelectionOptionsView.getFourHabitatTiles();
            StartGame.selectedTokens = SelectionOptionsView.getFourWildlifeTokens();
            StartGame.tilesRemain = true;

            long endTime = System.currentTimeMillis();
            long timeTaken = (endTime - startTime);
            timeTakenPerGameList.add(timeTaken);
            perPlayerScorePerGameList.add(scorePerPlayer);

//            System.out.println("Time taken: " + (endTime - startTime));
//            System.out.println("Average Score per Player = " + scorePerPlayer);
        }

        double averageScorePerPlayerAcrossNGames = perPlayerScorePerGameList
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);

        double scoreVariance = perPlayerScorePerGameList
                .stream()
                .mapToDouble(num -> Math.pow(num - averageScorePerPlayerAcrossNGames, 2))
                .average()
                .orElse(Double.NaN);

        double scoreStdDev = Math.sqrt(scoreVariance);


        double averageTimeTakenPerGame = timeTakenPerGameList
                .stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(Double.NaN);


        double timeVariance = perPlayerScorePerGameList
                .stream()
                .mapToDouble(num -> Math.pow(num - averageTimeTakenPerGame, 2))
                .average()
                .orElse(Double.NaN);

        double timeStdDev = Math.sqrt(timeVariance);

        double maxScore = perPlayerScorePerGameList
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);


        if (verbose) {
        System.out.printf("Average Score per Player across %d games is %.2f points with a standard deviation of %f\n",
                numberOfGames,
                averageScorePerPlayerAcrossNGames,
                scoreStdDev);

        System.out.printf("Average Time taken per game across %d games is %.2f milliseconds with a standard deviation of %f\n",
                numberOfGames,
                averageTimeTakenPerGame,
                timeStdDev);
        }

        System.out.println("Max score: " + maxScore);

        return new CustomPair<>(new CustomPair<>(averageScorePerPlayerAcrossNGames, scoreStdDev), new CustomPair<>(averageTimeTakenPerGame, timeStdDev));


    }

    private static void testingBotTileTokenPairing() {
        CascadiaBot p1 = new CascadiaBot("Ton");
        p1.getPlayerBoardObject().setSelectedTile(new HabitatTile(true));
        p1.getPlayerBoardObject().addNewTile(new HexCoordinate(1, 0));

        p1.playTurn();
    }

    private static void testingBotTileTokenPairingLotsOfTimes() {
        for (int i=0; i<10; i++) {
            testingBotTileTokenPairing();
            HabitatTilesController.habitatTilesBag = createBagOfHabitatTiles();
            StarterHabitatTilesController.starterHabitatTilesBag = createBagOfStarterHabitatTiles();
            WildlifeTokensController.wildlifeTokenBag = createBagOfWildlifeTokens();
            StartGame.selectedTiles = SelectionOptionsView.getFourHabitatTiles();
            StartGame.selectedTiles = SelectionOptionsView.getFourHabitatTiles();
        }
    }

    private static void testWeights(int numberOfGames) {
        List<Double> weights = new ArrayList<>();
        for (double i = 0.1; i <= 4.0; i += 0.1) {
            weights.add(i);
        }

        for (Double weight: weights) {
            WeightController.WeightConstants.nonPairBearPlacementReduction = weight;
            double score = getGameStatsAcrossMultipleGames(numberOfGames, false).getField1().getField1();
            System.out.printf(score + " ");
        }

    }

    private static int runGame() {

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(new CascadiaBot("Dom"));
        playerList.add(new CascadiaBot("Colm"));

        PlayerManager playerManager = new PlayerManager(playerList);
        playerManager.shufflePlayerList();

        //remove habitat tiles depending on number of players
        int tilesToRemove = (((2-4)*-1)*20)+2;
        if (tilesToRemove > 0)
            HabitatTilesController.habitatTilesBag.subList(0, tilesToRemove).clear();

        Player activePlayer = playerManager.getFirstPlayer();
//        CascadiaBot.displayBotActions = false;

        while (StartGame.tilesRemain) {

//            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
//            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
//            System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));

            // If the user wants to quit the game: playTurn() returns false which breaks the loop
            // otherwise it ends with returning true.
            if (!activePlayer.playTurn()) {
                break;
            }

            //next player
//            System.out.println("Moving to next player");
            activePlayer = playerManager.cycleToNextPlayer();
//            GameUiView.printLargeSpace();
        }

//        System.out.println("No tiles remain so play is finished, calculating player score...");

//        playerManager.tallyUpAllScores();
//        ScoreDisplayView.displayScorePage(playerManager);

        int sumOfScores = 0;
        for (Player player : playerList) {
            sumOfScores += player.getScore();
        }
        int scorePerPlayer = sumOfScores / playerList.size();

        return scorePerPlayer;

//        System.out.println(scorePerPlayer);
//        System.out.println(BoardView.displayTiles(playerManager.getPlayerList().get(0).getPlayerBoardObject()));;


    }

    public static void main(String[] args) {
//            testingPlacingBearTokens();
//        testingPlacingHawksTokens();

//        testingPlacingFoxTokens();
//        testingPlacingHawksTokens();
//        testingPlacingTileAlgo();
//        getGameStatsAcrossMultipleGames(1000, true);
//        testWeights(1000);
        runGame();




    }
}
