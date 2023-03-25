package org.grouptwentyone.controllers;

import org.grouptwentyone.models.*;

import javax.crypto.AEADBadTagException;
import java.lang.reflect.Array;
import java.security.spec.ECField;
import java.util.*;
import java.util.stream.Collectors;

public class ScoringController {


    /**
     * Scans the board for each tile with a fox token. A point is added for each unique wildlife token adjacent to the fox token.
     * <br><br>Adjacent fox tokens are included in scoring.
     *
     * @param playerBoard of current Player
     * @return local score as integer
     */
    public static int scoreFoxScoringCardA(PlayerBoard playerBoard) {

        int localScore = 0;

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean tileHasFoxToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.FOX;
                if (tileHasFoxToken) {
//                    System.out.println("FOUND FOX");
                    ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentTileList(tile);

                    ArrayList<WildlifeToken.WildlifeTokenType> adjacentWildlifeTokenList = new ArrayList<>();

                    for (Tile adjacentTile : adjacentTileList) {
                        boolean containsNewAdjacentWildlifeToken = !adjacentWildlifeTokenList.contains(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());
                        boolean tileHasWildlifeToken = adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY;

//                        System.out.printf("Tile %s, containsNewAdjacentWildlifeToken: %s, tileHasWildlifeToken: %s\n", adjacentTile, containsNewAdjacentWildlifeToken, tileHasWildlifeToken);

                        if (containsNewAdjacentWildlifeToken && tileHasWildlifeToken) {
                            adjacentWildlifeTokenList.add(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());
                        }
                    }
                    int numberOfAdjacentUniqueAnimalTokens = adjacentWildlifeTokenList.size();
                    localScore += numberOfAdjacentUniqueAnimalTokens;
                }
            }
        }

        return localScore;
    }

    /**
     * Scans the board for each tile with a fox token. Points are added for each unique wildlife token pair adjacent to the fox token.
     * The pair does not have to be adjacent to each other.
     * <br><br>Adjacent fox tokens are <b>not</b> included in scoring.
     * <br><br>
     * <u>Scoring Table</u>
     * <br> 1 pair -> 3 points
     * <br> 2 pairs -> 5 points
     * <br> 3 pairs -> 7 points
     *
     *
     *
     * @param playerBoard of current Player
     * @return local score as integer
     */
    public static int scoreFoxScoringCardB(PlayerBoard playerBoard) {

        int localScore = 0;
        HashMap<Integer, Integer> scoringCard = new HashMap<>();
        scoringCard.put(0, 0);
        scoringCard.put(1, 3);
        scoringCard.put(2, 5);
        scoringCard.put(3, 7);

        ArrayList<WildlifeToken.WildlifeTokenType> adjacentWildlifeTokensWithOneOccurrence = new ArrayList<>();
        ArrayList<WildlifeToken.WildlifeTokenType> adjacentWildlifeTokensWithPairOccurrence = new ArrayList<>();

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {

                boolean tileHasFoxToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.FOX;
                if (tileHasFoxToken) {

                    for (Tile adjacentTile : playerBoard.getAdjacentNonEmptyTileList(tile)) {
                        boolean tokenPairExists = adjacentWildlifeTokensWithPairOccurrence.contains(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());
                        boolean tokenHasAppearedOnce = adjacentWildlifeTokensWithOneOccurrence.contains(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());

                        // Condition 0: Adjacent tile is a fox token so we must ignore.
                        boolean adjacentTileHasFoxToken = adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.FOX;
                        if (adjacentTileHasFoxToken) {
                            continue;
                        }

                        // Condition 1 : Pair already exists so ignore as a pair is only scored once. (No code needed)

                        // Condition 2: One occurrence of the wildlife token already exists so the second occurrence makes a pair.
                        if (tokenHasAppearedOnce && !tokenPairExists) {
                            adjacentWildlifeTokensWithPairOccurrence.add(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());
                            adjacentWildlifeTokensWithOneOccurrence.remove(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());

                        }
                        // Condition 3: Wildlife token has not appeared before: so this is the first occurrence
                        else if (!tokenHasAppearedOnce) {
                            adjacentWildlifeTokensWithOneOccurrence.add(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());
                        }
//                        else {
//                            System.out.printf("tile: %s, tokenPairExists: %s, tokenHasAppearedOnce: %s\n", adjacentTile, tokenPairExists, tokenHasAppearedOnce);
//                        }

                    }
                    int numberOfUniquePairs = adjacentWildlifeTokensWithPairOccurrence.size();
                    localScore += scoringCard.get(numberOfUniquePairs);
                }
            }
        }

        return localScore;
    }


    /**
     * Scans the board for each tile with a fox token. Identify the most common adjacent wildlife token.
     * 1 point is added for every adjacent wildlife token which was identified as the most common.
     * <br><br>Adjacent fox tokens are <b>not</b> included in scoring.
     * <br><br>
     *
     *
     * @param playerBoard of current Player
     * @return local score as integer
     */
    public static int scoreFoxScoringCardC(PlayerBoard playerBoard) {

        int localScore = 0;

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {

                boolean tileHasFoxToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.FOX;
                if (tileHasFoxToken) {

                    HashMap<WildlifeToken.WildlifeTokenType, Integer> wildlifeTokenScores = new HashMap<>();
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.BEAR, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.FOX, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.ELK, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.HAWK, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.SALMON, 0);

                    for (Tile adjacentTile : playerBoard.getAdjacentNonEmptyTileList(tile)) {
                        WildlifeToken.WildlifeTokenType adjacentTileWildlifeTokenType = adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType();
                        int tokenScore = wildlifeTokenScores.get(adjacentTileWildlifeTokenType);
                        wildlifeTokenScores.put(adjacentTileWildlifeTokenType, tokenScore + 1);
                    }

                    int maxTokenScore = 0;
                    WildlifeToken.WildlifeTokenType maxTokenType = WildlifeToken.WildlifeTokenType.EMPTY;

                    for (HashMap.Entry<WildlifeToken.WildlifeTokenType, Integer> entry : wildlifeTokenScores.entrySet()) {
                        WildlifeToken.WildlifeTokenType tokenType = entry.getKey();
                        int tokenScore = entry.getValue();

                        if (tokenScore > maxTokenScore) {
                            maxTokenScore = tokenScore;
                            maxTokenType = tokenType;
                        }
                    }

                    localScore += maxTokenScore;
//                    System.out.println(maxTokenType);

                }


            }
        }

        return localScore;
    }

    /**
     * Scans the board for each tile with a bear token. 1 point is added for every pair of bears.
     * Once a pair of bears has been scored: they must be removed from further scoring.
     * <br><br>
     *
     * @param playerBoard - of current Player
     * @return local score as integer
     */
    public static int scoreBearScoringCardA(PlayerBoard playerBoard) {

        double numberOfPairs = 0;
        ArrayList<Tile> tilesExcludedFromScoring = new ArrayList<>();

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean tileHasBearToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.BEAR;

                if (tileHasBearToken) {
                    ArrayList<Tile> tileGroupList = ScoringController.getTileGroupFromTile(playerBoard, tile.getHexCoordinate());

                    // Ignore tiles that are excluded from scoring.
                    ArrayList<Tile> tilesToRemove = new ArrayList<>();
                    for (Tile groupTile : tileGroupList) {
                        if (tilesExcludedFromScoring.contains(groupTile)) tilesToRemove.add(groupTile);
                    }
                    tileGroupList.removeAll(tilesToRemove);

                    // If the conditional passes: there must be at least two tiles not yet recorded or adjacent to recorded pairs.
                    boolean isGroupSizeTwo = tileGroupList.size() >= 2;
                    if (isGroupSizeTwo) {

                        numberOfPairs++; // Increment number of pairs

                        ArrayList<Tile> tilePairList = new ArrayList<>(tileGroupList.subList(0, 2));
                        tilesExcludedFromScoring.addAll(tilePairList); // We exclude the pair from future scoring.
                        // And then remove them pair from the group list
                        tileGroupList.remove(0);
                        tileGroupList.remove(0);

                        // Now check all of the remaining items in the group to see if they're adjacent to the pair.
                        // If they are: they should be excluded from scoring
                        for (Tile groupTile : tileGroupList) {
                            for (Tile pairTile : tilePairList) {
                                if (groupTile.isAdjacentToTile(pairTile)) {
                                    tilesExcludedFromScoring.add(groupTile);
                                }
                            }
                        }

                    }

                }
            }
        }


        // More than 4 pairs are treated the same as 4 pairs.
        if (numberOfPairs > 4) numberOfPairs = 4;

        HashMap<Integer, Integer> scoringTable = new HashMap<>();
        scoringTable.put(0, 0);
        scoringTable.put(1, 4);
        scoringTable.put(2, 11);
        scoringTable.put(3, 19);
        scoringTable.put(4, 27);

        int localScore = scoringTable.get((int) numberOfPairs);
        return localScore;
    }

    public static void traverseTileGroup(PlayerBoard playerBoard, ArrayList<Tile> listOfTilesInGroup, HexCoordinate hexCoordinate) {

        Tile inputTile = playerBoard.getPlayerBoardAs2dArray().get(hexCoordinate.getX()).get(hexCoordinate.getY());
        WildlifeToken.WildlifeTokenType requiredWildlifeTokenType = inputTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType();

        ArrayList<Tile> adjacentTileListWithSameWildlifeTokenList = playerBoard.getAdjacentNonEmptyTileList(inputTile)
                .stream().filter(adjacentTile -> adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == requiredWildlifeTokenType)
                .collect(Collectors.toCollection(ArrayList::new));

        // Make list of new unrecorded adjacent tiles with same animal token
        ArrayList<Tile> newAdjacentTileList = new ArrayList<>();
        for (Tile adjacentTile : adjacentTileListWithSameWildlifeTokenList) {
            boolean tileNotInTileGroup = !listOfTilesInGroup.contains(adjacentTile);
            if (tileNotInTileGroup) {
                newAdjacentTileList.add(adjacentTile);
            }
        }

        // Base Case: If there are no more new unrecorded adjacent tiles
        if (newAdjacentTileList.size() == 0) return;

        listOfTilesInGroup.addAll(newAdjacentTileList);

        for (Tile adjacentTile : newAdjacentTileList) {
            traverseTileGroup(playerBoard, listOfTilesInGroup, adjacentTile.getHexCoordinate());
        }



    }

    public static ArrayList<Tile> getTileGroupFromTile(PlayerBoard playerBoard, HexCoordinate hexCoordinate) {

        Tile inputTile = playerBoard.getPlayerBoardAs2dArray().get(hexCoordinate.getX()).get(hexCoordinate.getY());
        ArrayList<Tile> outputList = new ArrayList<>();
        WildlifeToken.WildlifeTokenType requiredWildlifeTokenType = inputTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType();

        ArrayList<Tile> adjacentTileListWithSameWildlifeTokenList = playerBoard.getAdjacentNonEmptyTileList(inputTile)
                .stream().filter(adjacentTile -> adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == requiredWildlifeTokenType)
                .collect(Collectors.toCollection(ArrayList::new));

        outputList.add(inputTile);
        outputList.addAll(adjacentTileListWithSameWildlifeTokenList);

        for (Tile adjacentTile : adjacentTileListWithSameWildlifeTokenList) {
            traverseTileGroup(playerBoard, outputList, adjacentTile.getHexCoordinate());
        }

        return outputList;
    }



    /**
     * Scans the board for each tile with a bear token. 10 points are added for every group of three bears.
     * Every time a triple of bears is scored: it must be removed from further scoring.
     * <br><br>
     *
     * @param playerBoard of current Player
     * @return local score as integer
     */
    public static int scoreBearScoringCardB(PlayerBoard playerBoard) {
        int numberOfTriples = 0;

        ArrayList<Tile> tilesRemovedFromScoring = new ArrayList<>();


        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean tileHasBearToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.BEAR;
                if (tileHasBearToken) {

                    // Add local tile group to records if not already added
                    ArrayList<Tile> localTileGroup = getTileGroupFromTile(playerBoard, tile.getHexCoordinate());

                    // Ignore cases where there is not enough in the group for a triple
                    if (localTileGroup.size() < 3) {
                        continue;
                    }

                    // List containing three-group collection of bears
                    ArrayList<Tile> localTileGroupTriple = new ArrayList<>(localTileGroup.subList(0, 3));

                    // List without the aforementioned three-group collection of bears
                    ArrayList<Tile> localTileGroupWithoutTriple = new ArrayList<>();
                    try {
                        localTileGroupWithoutTriple.addAll(localTileGroup.subList(4, localTileGroup.size()));
                    } catch (IllegalArgumentException ex) {
                        // Nothing, just ignore if it doesn't work
                    }

                    // List of the triple and the adjacent tiles (we remove any tiles not adjacent to the triple).
                    ArrayList<Tile> localTileGroupWithAdjacentTiles = new ArrayList<>(localTileGroupTriple);
                    for (Tile localTile : localTileGroupWithoutTriple) {
                        for (Tile localTripleTile : localTileGroupTriple) {
                            if (localTile.isAdjacentToTile(localTripleTile)) {
                                localTileGroupWithAdjacentTiles.add(localTile);
                            }
                        }
                    }

                    boolean isLocalTileGroupRecorded = false;
                    for (Tile tileInGroup : localTileGroupWithAdjacentTiles) {
                        boolean tileNotYetRecorded = !tilesRemovedFromScoring.contains(tileInGroup);
                        if (tileNotYetRecorded) {
                            tilesRemovedFromScoring.add(tileInGroup);
                        } else {
                            isLocalTileGroupRecorded = true;
                        }
                    }
//
                    if (!isLocalTileGroupRecorded) {
//                        System.out.println("INCREMENTED SCORE");
                        numberOfTriples++;
                    }

                }
            }
        }

        int localScore = numberOfTriples * 10;
        return localScore;
    }


    /**
     * Scans the board for each tile with a bear token.
     * <br><br> 1. Find a group of three bears. If one is found: add 8 points and exclude the group from further scoring.
     * <br> 2. Find a group of two bears. If one is found: add 5 points and exclude the group from further scoring.
     * <br> 3. Find a single tile with a bear token: If one is found: add 2 points.
     * <br> 4. If all three types have been found: add 3 points.
     *
     * @param playerBoard of current Player
     * @return local score as integer
     */
    public static int scoreBearScoringCardC(PlayerBoard playerBoard) {
        HashMap<Integer, Integer> groupSizeFrequency = new HashMap<>();
        groupSizeFrequency.put(3, 0);
        groupSizeFrequency.put(2, 0);
        groupSizeFrequency.put(1, 0);

        ArrayList<Tile> excludedTilesFromScoring = new ArrayList<>();

        // Score singe group of size 3
        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            boolean foundTriple = false;

            for (Tile tile : row) {
                boolean tileHasBearToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.BEAR;
                if (tileHasBearToken) {

                    ArrayList<Tile> tileGroupList = getTileGroupFromTile(playerBoard, tile.getHexCoordinate());

                    // Remove excluded tiles from scoring from the group list.
                    tileGroupList = tileGroupList.stream()
                            .filter(groupTile -> !excludedTilesFromScoring.contains(groupTile))
                            .collect(Collectors.toCollection(ArrayList::new));


                    // Ensure the group size is exactly 3
                    if (tileGroupList.size() != 3) {
                        continue;
                    }

                    groupSizeFrequency.put(3, groupSizeFrequency.get(3) + 1);
                    foundTriple = true;

                    ArrayList<Tile> tripleGroupList = new ArrayList<>(tileGroupList.subList(0, 3));
                    excludedTilesFromScoring.addAll(tripleGroupList);

                    // Remove the triple from the group list
                    tileGroupList.remove(0);
                    tileGroupList.remove(0);
                    tileGroupList.remove(0);

                    // Identify and place adjacent tiles to the triple into the excluded from scoring list
                    for (Tile groupTile : tileGroupList) {
                        for (Tile tripleTile : tripleGroupList) {
                            boolean isTileAdjacentToTriple = groupTile.isAdjacentToTile(tripleTile);
                            if (isTileAdjacentToTriple) {
                                excludedTilesFromScoring.add(groupTile);
                            }
                        }
                    }

                    break;
                }
            }

            if (foundTriple) break;
        }

        // Score a single group of size 2
        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            boolean foundDouble = false;

            for (Tile tile : row) {
                boolean tileHasBearToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.BEAR;
                if (tileHasBearToken) {
                    ArrayList<Tile> tileGroupList = getTileGroupFromTile(playerBoard, tile.getHexCoordinate());

                    // Remove excluded tiles from scoring from the group list.
                    tileGroupList = tileGroupList.stream()
                            .filter(groupTile -> !excludedTilesFromScoring.contains(groupTile))
                            .collect(Collectors.toCollection(ArrayList::new));


                    // Ensure the group size is exactly 2
                    if (tileGroupList.size() != 2) {
                        continue;
                    }

                    groupSizeFrequency.put(2, groupSizeFrequency.get(2) + 1);
                    foundDouble = true;

                    ArrayList<Tile> tripleGroupList = new ArrayList<>(tileGroupList.subList(0, 2));
                    excludedTilesFromScoring.addAll(tripleGroupList);

                    // Remove the double from the group list
                    tileGroupList.remove(0);
                    tileGroupList.remove(0);

                    // Identify and place adjacent tiles to the triple into the excluded from scoring list
                    for (Tile groupTile : tileGroupList) {
                        for (Tile tripleTile : tripleGroupList) {
                            boolean isTileAdjacentToTriple = groupTile.isAdjacentToTile(tripleTile);
                            if (isTileAdjacentToTriple) {
                                excludedTilesFromScoring.add(groupTile);
                            }
                        }
                    }

                    break;

                }
            }
            if (foundDouble) break;
        }

        // Score a single group of size 1
        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            boolean foundSingle = false;

            for (Tile tile : row) {
                boolean tileHasBearToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.BEAR;
                if (tileHasBearToken) {

                    ArrayList<Tile> tileGroupList = getTileGroupFromTile(playerBoard, tile.getHexCoordinate());
                    // Remove excluded tiles from scoring from the group list.
                    tileGroupList = tileGroupList.stream()
                            .filter(groupTile -> !excludedTilesFromScoring.contains(groupTile))
                            .collect(Collectors.toCollection(ArrayList::new));

                    // Ensure the group size is exactly 1
                    if (tileGroupList.size() != 1) {
                        continue;
                    }

                    groupSizeFrequency.put(1, groupSizeFrequency.get(1) + 1);
                    foundSingle = true;

                    ArrayList<Tile> tripleGroupList = new ArrayList<>(tileGroupList.subList(0, 1));
                    excludedTilesFromScoring.addAll(tripleGroupList);

                    // Remove the single from the group list
                    tileGroupList.remove(0);

                    // Identify and place adjacent tiles to the triple into the excluded from scoring list
                    for (Tile groupTile : tileGroupList) {
                        for (Tile tripleTile : tripleGroupList) {
                            boolean isTileAdjacentToTriple = groupTile.isAdjacentToTile(tripleTile);
                            if (isTileAdjacentToTriple) {
                                excludedTilesFromScoring.add(groupTile);
                            }
                        }
                    }

                    break;
                }
            }
            if (foundSingle) break;
        }

        int localScore = 0;

        if (groupSizeFrequency.get(3) > 0) {
            localScore += 8;
        }
        if (groupSizeFrequency.get(2) > 0) {
            localScore += 5;
        }
        if (groupSizeFrequency.get(1) > 0) {
            localScore += 2;
        }

        if (groupSizeFrequency.get(3) > 0 && groupSizeFrequency.get(2) > 0 && groupSizeFrequency.get(1) > 0) {
            localScore += 3;
        }


        return localScore;
    }

    /**
     *  1) Stores a local score variable starting at 0.
     *  2) assigns local score to the getLargestGroup
     * @param playerBoard
     * @return local store as integer
     */
    public static int scoreElkScoringCardA(PlayerBoard playerBoard) {
     int localScore = 0;

     Set<Tile> usedElkTiles = new HashSet<>();

     // call recursive function to get score
     localScore = getLargestGroupScores(playerBoard, localScore, usedElkTiles);

     return localScore;
    }

    /**
     * Recursive helper function for Elk Scoring Card A
     *
     *  1) for each elk in the board find its largest line of elk in the east, south-east, and south-west direction
     *  2) if the size of any line is greater than the maximum size, that line becomes the new maximum
     *  3) once all elk have been scanned through, yield the score for the maximum line found
     *  4) remove this line from the playerBoard and recursively scan through again carrying on the score in each call
     *
     * @param playerBoard of current Player
     * @param totalScore to keep track of the final score
     * @param usedElkTiles to keep track of elks already scored
     * @return total score as integer
     */
    public static int getLargestGroupScores(PlayerBoard playerBoard, int totalScore, Set<Tile> usedElkTiles) {
        ArrayList<Tile> largestLine = new ArrayList<>();

        //traverse through the playBoard
        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasElkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.ELK;

                if (hasElkToken && !usedElkTiles.contains(tile)) {
                    //get lines of elk (if any)
                    ArrayList<Tile> tilesEast = playerBoard.getConnectedSameTilesEast(tile, playerBoard);
                    ArrayList<Tile> tilesSouthEast = playerBoard.getConnectedSameTilesSouthEast(tile, playerBoard);
                    ArrayList<Tile> tilesSouthWest = playerBoard.getConnectedSameTilesSouthWest(tile, playerBoard);

                    //find largest line of elk in the board
                    if (tilesEast.size() >= largestLine.size()) {
                        largestLine = tilesEast;
                    }
                    if (tilesSouthEast.size() >= largestLine.size()) {
                        largestLine = tilesSouthEast;
                    }
                    if (tilesSouthWest.size() >= largestLine.size()) {
                        largestLine = tilesSouthWest;
                    }
                }
            }
        }
        //base case : there are no more lines of elk
        if (largestLine.size() == 0) {
            return 0;
        }

        //recursive case : there is at least one line of elk of size 1 or more
        else {
            //add elk in largest line to remove them in the next recursive call
            usedElkTiles.addAll(largestLine);

            //set score
            if (largestLine.size() == 1) {
                totalScore = 2;
            } else if (largestLine.size() == 2) {
                totalScore = 5;
            } else if (largestLine.size() == 3) {
                totalScore = 9;
            } else if (largestLine.size() >= 4) {
                totalScore = 13;
            }
            return totalScore + getLargestGroupScores(playerBoard, totalScore, usedElkTiles);
        }
    }

    /**
     * 1) we traverse through the playerBoard. When an elk is found, we get it's associated group using getTileGroupFromTile
     * 2) we add all the elk from this group in usedElk so that they are not double counted in scoring
     * 3) appropriate scores are added and we terminate the loop until there are no more elk groups
     * @param playerBoard
     * @return local store as integer
     */
    public static int scoreElkScoringCardB(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> usedElkTiles = new HashSet<>();

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasElkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.ELK;

                if (hasElkToken && !usedElkTiles.contains(tile)) {
                    usedElkTiles.add(tile);

                    //get all elk tiles that are in the same group as the tile in iteration
                    ArrayList<Tile> group = getTileGroupFromTile(playerBoard, tile.getHexCoordinate());

                    //add this group in usedElkTiles so the next iteration skips over these tiles
                    usedElkTiles.addAll(group);

                    int groupSize = group.size();

                    //increment score
                    if (groupSize > 4) {
                        groupSize = 4;
                    }

                    if (groupSize == 1) {
                        localScore += 2;
                    } else if (groupSize == 2) {
                        localScore += 4;
                    } else if (groupSize == 3) {
                        localScore += 7;
                    } else if (groupSize == 4) {
                        localScore += 10;
                    } else if (groupSize == 5) {
                        localScore += 14;
                    } else if (groupSize == 6) {
                        localScore += 18;
                    } else if (groupSize == 7) {
                        localScore += 23;
                    } else if (groupSize >= 8) {
                        localScore += 28;
                    }
                }
            }
        }

        return localScore;
    }

    /**
     *  1) traverse through the playerBoard. When an elk is found, record its associated group size
     *     in an arrayList and proceed in this manner
     *  2) At the end, score based on whether the right sizes are contained in the list, starting with
     *     4, then 3 etc.
     * @param playerBoard
     * @return local score as integer
     */
    public static int scoreElkScoringCardC(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> usedElkTiles = new HashSet<>();

        //list to store all sizes we found in the board
        ArrayList<Integer> sizes = new ArrayList<>();
        int groupSizeToFind = 4;

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasElkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.ELK;

                if (hasElkToken && !usedElkTiles.contains(tile)) {
                    //get group of elk in board and add the size of that group to the sizes list
                    ArrayList<Tile> group = getTileGroupFromTile(playerBoard, tile.getHexCoordinate());
                    sizes.add(group.size());

                    usedElkTiles.addAll(group);
                }
            }
        }

        //remove sizes greater than 4, since they will yield no points
        // function assumes 'group' means an independent cluster of animals, i.e, a cluster of 5 elk
        // is not a group of 1 elk and a group of 4 elk
        ArrayList<Integer> validSizes = new ArrayList<>();
        for (int size: sizes) {
            if (size < 5) {
                validSizes.add(size);
            }
        }

        //increment points
        if (validSizes.contains(4)) {
            localScore += 13;
            if (validSizes.contains(3)) {
                localScore += 9;
                if (validSizes.contains(2)) {
                    localScore += 5;
                    if (validSizes.contains(1)) {
                        localScore += 2;
                    }
                }
            }
        }

        return localScore;
    }

    /**
     * 1) traverse through the playBoard. When a salmon tile is found, call the getRunOfSalmon function
     *    to get its associated run
     * 2) increment the score accordingly based on the size of the run
     * @param playerBoard
     * @return local score as integer
     */
    public static int scoreSalmonScoringCardA(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> usedSalmonTiles = new HashSet<>();

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasSalmonToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.SALMON;

                if (hasSalmonToken && !usedSalmonTiles.contains(tile)) {

                    ArrayList<Tile> runOfSalmon = new ArrayList<>();

                    //get run of salmon
                    getRunOfSalmon(tile, playerBoard, runOfSalmon, usedSalmonTiles);

                    int runSize = runOfSalmon.size();

                    //increment size
                    if (runSize == 1) {
                        localScore += 2;
                    } else if (runSize == 2) {
                        localScore += 4;
                    } else if (runSize == 3) {
                        localScore += 7;
                    } else if (runSize == 4) {
                        localScore += 11;
                    } else if (runSize == 5) {
                        localScore += 15;
                    } else if (runSize == 6) {
                        localScore += 20;
                    } else if (runSize >= 7) {
                        localScore += 26;
                    }

                }
            }
        }
        return localScore;
    }

    /**
     * 1) traverse through the playBoard. When a salmon tile is found, call the getRunOfSalmon function
     *    to get its associated run
     * 2) increment the score accordingly based on the size of the run
     * @param playerBoard
     * @return local score as integer
     */
    public static int scoreSalmonScoringCardB(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> usedSalmonTiles = new HashSet<>();

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasSalmonToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.SALMON;

                if (hasSalmonToken && !usedSalmonTiles.contains(tile)) {

                    ArrayList<Tile> runOfSalmon = new ArrayList<>();
                    //get run of salmon
                    getRunOfSalmon(tile, playerBoard, runOfSalmon, usedSalmonTiles);

                    int runSize = runOfSalmon.size();

                    //increment score
                    if (runSize == 1) {
                        localScore += 2;
                    } else if (runSize == 2) {
                        localScore += 4;
                    } else if (runSize == 3) {
                        localScore += 8;
                    } else if (runSize >= 4) {
                        localScore += 12;
                    }
                }
            }
        }
        return localScore;
    }


    /**
     * 1) traverse through the playBoard. When a salmon tile is found, call the getRunOfSalmon function
     *    to get its associated run
     * 2) increment the score accordingly based on the size of the run
     * @param playerBoard
     * @return local score as integer
     */
    public static int scoreSalmonScoringCardC(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> usedSalmonTiles = new HashSet<>();

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasSalmonToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.SALMON;

                if (hasSalmonToken && !usedSalmonTiles.contains(tile)) {

                    ArrayList<Tile> runOfSalmon = new ArrayList<>();
                    //get run of salmon
                    getRunOfSalmon(tile, playerBoard, runOfSalmon, usedSalmonTiles);

                    int runSize = runOfSalmon.size();

                    //increment score
                    if (runSize == 1) {
                        localScore += 2;
                    } else if (runSize == 2) {
                        localScore += 4;
                    } else if (runSize == 3) {
                        localScore += 9;
                    } else if (runSize == 4) {
                        localScore += 11;
                    } else if (runSize >= 5) {
                        localScore += 17;
                    }
                }
            }
        }
        return localScore;
    }

    /**
     * traverses through the playBoard until a hawk card is found. We remove any adjacent hawks
     * with the help of the getAdjacentTile
     * @param playerBoard
     * @return
     */

    public static int scoreHawkScoringCardA(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> discardedHawks = new HashSet<>(); //set of removed hawks
        Set<Tile> validHawks = new HashSet<>();  //set of valid hawks

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasHawkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.HAWK;

                if (hasHawkToken && !discardedHawks.contains(tile)) {

                    //get all adjacent tiles to the hawk in iteration
                    ArrayList<Tile> adjacentTiles = playerBoard.getAdjacentTileList(tile);

                    //if hawk is adjacent to another hawk, add that hawk to discardedHawks so that it is not counted in scoring
                    for (Tile adjacentTile : adjacentTiles) {
                        if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.HAWK) {
                            discardedHawks.add(adjacentTile);
                        }
                    }

                    //add hawk to validHawks for scoring
                    validHawks.add(tile);

                }
            }
        }

        //set score
        if (validHawks.size() == 1) {
            localScore = 2;
        } else if (validHawks.size() == 2) {
            localScore = 5;
        } else if (validHawks.size() == 3) {
            localScore = 8;
        } else if (validHawks.size() == 4) {
            localScore = 11;
        } else if (validHawks.size() == 5) {
            localScore = 14;
        } else if (validHawks.size() == 6) {
            localScore = 18;
        } else if (validHawks.size() == 7) {
            localScore = 22;
        } else if (validHawks.size() >= 8) {
            localScore = 26;
        }

        return localScore;
    }

    public static int scoreHawkScoringCardB(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> discardedHawkTiles = new HashSet<>();
        int adjacentHawkPairCounter = 0;

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasHawkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK;

                if (hasHawkToken && !discardedHawkTiles.contains(tile)) {

                    //shortlist hawks that are adjacent to other hawks so that they are ignored for scoring
                    ArrayList<Tile> adjacentTiles = playerBoard.getAdjacentTileList(tile);
                    for (Tile adjacentTile : adjacentTiles) {
                        if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.HAWK) {
                            discardedHawkTiles.add(adjacentTile);
                        }
                    }

                    int xCoord = tile.getHexCoordinate().getX();
                    int yCoord = tile.getHexCoordinate().getY();

                    //verify if a direct line of sight exists
                    if (hasDirectLineOfSight(xCoord, yCoord, playerBoard)) {
                        adjacentHawkPairCounter++;
                    }
                }
            }
        }

        //set score
        if (adjacentHawkPairCounter == 2) {
            localScore = 5;
        } else if (adjacentHawkPairCounter == 3) {
            localScore = 9;
        } else if (adjacentHawkPairCounter == 4) {
            localScore = 12;
        } else if (adjacentHawkPairCounter == 5) {
            localScore = 16;
        } else if (adjacentHawkPairCounter == 6) {
            localScore = 20;
        } else if (adjacentHawkPairCounter == 7) {
            localScore = 24;
        } else if (adjacentHawkPairCounter >= 8) {
            localScore = 28;
        }

        return localScore;
    }

    /**
     * helper function for hawk scoring cards to verify if there exists a line of sight.
     * there are six try-catch statements, one for each direction of a tile - north-east, east, south-east, south-west, west and north-west
     * each try-catch looks for a hawk two tiles forward in that direction and an empty tile one tile in that direction
     * for north-east, north-west, south-east and south-west, we also need to check if the row index is odd or even, since the column index
     * will depend on this parity

     * @param row to let us know what row the hawk is on
     * @param col to let us know what column the hawk is on
     * @param playerBoard
     * @return true if there is a line of sight and false otherwise
     */
    public static boolean hasDirectLineOfSight(int row, int col, PlayerBoard playerBoard) {

        //there are six try-catch statements, one for each direction of a tile - north-east, east, south-east, south-west, west and north-west
        //each try-catch looks for a hawk two tiles forward in that direction and an empty tile one tile in that direction
        //for north-east, north-west, south-east and south-west, we also need to check if the row index is odd or even, since the column index
        //will depend on this parity

        //east
        try {
            if (playerBoard.getTileByCoordinate(row, col + 2).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                    == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row, col + 1).getHabitatTile().
                    getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                    return true;
            }
        } catch (Exception e) {;}

        //south-east
        try {
            if (row % 2 == 0) {

                if (playerBoard.getTileByCoordinate(row + 2, col + 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row + 1, col + 1).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                        return true;
                }

            } else {
                if (playerBoard.getTileByCoordinate(row + 2, col + 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row + 1, col).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                        return true;
                }
            }
        } catch (Exception e) {;}

        //south-west
        try {
            if (row % 2 == 0) {

                if (playerBoard.getTileByCoordinate(row + 2, col - 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row + 1, col).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                       return true;
                }

            } else {
                if (playerBoard.getTileByCoordinate(row + 2, col - 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row + 1, col - 1).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                        return true;
                }
            }
        } catch (Exception e) {;}

        //north-east
        try {
            if (row % 2 == 0) {

                if (playerBoard.getTileByCoordinate(row - 2, col + 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row - 1, col + 1).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                    return true;
                }

            } else {
                if (playerBoard.getTileByCoordinate(row - 2, col + 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row - 2, col + 1).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                    return true;
                }
            }
        } catch (Exception e) {;}

        //north-west
        try {
            if (row % 2 == 0) {

                if (playerBoard.getTileByCoordinate(row - 2, col - 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row - 1, col).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                    return true;
                }

            } else {
                if (playerBoard.getTileByCoordinate(row - 2, col - 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row - 2, col - 1).getHabitatTile().
                        getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                    return true;
                }
            }
        } catch (Exception e) {;}

        //west
        try {
            if (playerBoard.getTileByCoordinate(row, col - 2).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                    == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(row, col - 1).getHabitatTile().
                    getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                return true;
            }
        } catch (Exception e) {;}

        return false;
    }

    public static int scoreHawkScoringCardC(PlayerBoard playerBoard) {
        int localScore = 0;
        Set<Tile> discardedHawkTiles = new HashSet<>();
        int adjacentHawkPairCounter = 0;
//        String scoreTable = " ____________________________________________________________________\n" +
//                            "| \033[1;36mHAWK SCORING CARD C    \033[0m                                            |\n" +
//                            "| - score 3 points for each direct line of sight between two hawks   |\n" +
//                            "| - tile in between the hawks must have no wildlife                  |\n" +
//                            "| \033[4;33mYOUR SCORE\033[0m                                                         |\n";
                ;
        //System.out.println("\u2502" + "   \u2514   \u2510"  +  "  \u250C\u2518" + "│"); // └ ┐ ┌ ┘ ─

        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                boolean hasHawkToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                        == WildlifeToken.WildlifeTokenType.HAWK;

                if (hasHawkToken && !discardedHawkTiles.contains(tile)) {
                    ArrayList<Tile> adjacentTiles = playerBoard.getAdjacentTileList(tile);

                    //short-list hawks that are adjacent to other hawks
                    for (Tile adjacentTile : adjacentTiles) {
                        if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.HAWK) {
                            discardedHawkTiles.add(adjacentTile);
                        }
                    }

                    int xCoord = tile.getHexCoordinate().getX();
                    int yCoord = tile.getHexCoordinate().getY();


                    //check if line of sight exists in east direction
                    try {
                        if (playerBoard.getTileByCoordinate(xCoord, yCoord + 2).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                                == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(xCoord, yCoord + 1).getHabitatTile().
                                getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                            adjacentHawkPairCounter++;
                            //update score table
//                            scoreTable += "|\033[0;37m LINE OF SIGHT " + adjacentHawkPairCounter + "\033[0m :    (" + xCoord + ", " + yCoord + ")"
//                        + "   =>   " + "(" + xCoord + ", " + (yCoord + 2) + ")" +  "         \033[1;93m+3\033[0m               |\n";
                        }
                    } catch (Exception e) {;}

                    //check if line of sight exists in south-west direction
                    try {
                        if (xCoord % 2 == 0) {

                            if (playerBoard.getTileByCoordinate(xCoord + 2, yCoord + 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                                    == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(xCoord + 1, yCoord + 1).getHabitatTile().
                                    getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                                adjacentHawkPairCounter++;
//                                scoreTable += "|\033[0;37m LINE OF SIGHT " + adjacentHawkPairCounter + "\033[0m :    (" + xCoord + ", " + yCoord + ")"
//                                        + "   =>   " + "(" + (xCoord + 2) + ", " + (yCoord + 1) + ")" +  "         \033[1;93m+3\033[0m               |\n";
                            }

                        } else {
                            if (playerBoard.getTileByCoordinate(xCoord + 2, yCoord + 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                                    == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(xCoord + 1, yCoord).getHabitatTile().
                                    getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                                adjacentHawkPairCounter++;
//                                scoreTable += "|\033[0;37m LINE OF SIGHT " + adjacentHawkPairCounter + "\033[0m :    (" + xCoord + ", " + yCoord + ")"
//                                        + "   =>   " + "(" + (xCoord + 2) + ", " + (yCoord + 1) + ")" +  "         \033[1;93m+3\033[0m               |\n";
                            }
                        }
                    } catch (Exception e) {;}

                    //check if line of sight exists in south-east direction
                    try {
                        if (xCoord % 2 == 0) {

                            if (playerBoard.getTileByCoordinate(xCoord + 2, yCoord - 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                                    == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(xCoord + 1, yCoord).getHabitatTile().
                                    getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                                adjacentHawkPairCounter++;
//                                scoreTable += "|\033[0;37m LINE OF SIGHT " + adjacentHawkPairCounter + "\033[0m :    (" + xCoord + ", " + yCoord + ")"
//           + "   =>   " + "(" + (xCoord + 2) + ", " + (yCoord - 1) + ")" +  "         \033[1;93m+3\033[0m               |\n";
                                                         }

                        } else {
                            if (playerBoard.getTileByCoordinate(xCoord + 2, yCoord - 1).getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                                    == WildlifeToken.WildlifeTokenType.HAWK && playerBoard.getTileByCoordinate(xCoord + 1, yCoord - 1).getHabitatTile().
                                    getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.EMPTY) {
                                adjacentHawkPairCounter++;
//                                scoreTable += "|\033[0;37m LINE OF SIGHT " + adjacentHawkPairCounter + "\033[0m :    (" + xCoord + ", " + yCoord + ")"
//                                        + "   =>   " + "(" + (xCoord + 2) + ", " + (yCoord - 1) + ")" +  "         \033[1;93m+3\033[0m               |\n";
                            }
                        }
                    } catch (Exception e) {;}

                }
            }
        }

//        System.out.println(scoreTable);
        //each line of sight yields 3 points
        return adjacentHawkPairCounter * 3;
    }

    /**
     * Recursive function to obtain the run of salmon based on a root salmon tile
     *
     *  1) first we check if the root tile is none, indicating that the run has ended or that there was
     *     no run in the first place
     *  2) Otherwise, we obtain the adjacentList of tiles around the root. We check if there are any salmon
     *     in that list.
     *  3) We initialise a new Tile called nextSalmon to null, assuming the run has reached an end
     *  4) If there is at least one salmon in the adjacentTiles list, we set that tile to nextSalmon
     *  5) If the adjacent tile list contains more than 3 salmon, we do not have a valid run anymore, so
     *     we return the run that existed before, if any.
     *  6) If there is a next salmon (not null) and we still have a valid run, we call the recursive function with
     *     this next salmon as the new root.
     *
     *
     * @param root to store the start of the run
     * @param playerBoard
     * @param run to store all the tiles in the run
     * @param usedTiles to store already counted tiles in the run
     * @return ArrayList of tiles which stores the run of tiles
     */
    public static ArrayList<Tile> getRunOfSalmon (Tile root, PlayerBoard playerBoard, ArrayList < Tile > run, Set < Tile > usedTiles) {

        //base case : there are no more salmon in the run, i.e, we have reached a null tile
        if (root == null) {
            return run;
        }

        //recursive case

        ArrayList<Tile> adjacentSalmonTiles = new ArrayList<>();

        //get adjacent tiles to the salmon in the recursive call to see if there are any other salmon in the existing run
        ArrayList<Tile> adjacentTiles = playerBoard.getAdjacentTileList(root);
        usedTiles.add(root);

        for (Tile adjacentTile : adjacentTiles) {
            //add all adjacent salmon to the adjacentSalmonTile list
            if ((adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.SALMON)) {
                adjacentSalmonTiles.add(adjacentTile);
            }
        }
        //assume we have reached the end of our run by setting the next salmon to null
        Tile nextSalmon = null;

        //if there is at salmon still in the run, assign it to the next salmon
        if (adjacentSalmonTiles.size() > 0) {
            for (Tile tile : adjacentSalmonTiles) {
                if (!run.contains(tile)) {
                    nextSalmon = tile;
                }
            }
        }

        //if there are more than 2 adjacent salmon, that is not a run, so return the valid run up until this point, if any
        if (adjacentSalmonTiles.size() > 2) {
            return run;
        } else {
            //add current salmon to run and call the recursive function
            run.add(root);
            getRunOfSalmon(nextSalmon, playerBoard, run, usedTiles);
        }

        return run;
    }
}



