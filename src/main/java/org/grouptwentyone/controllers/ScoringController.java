package org.grouptwentyone.controllers;

import org.grouptwentyone.models.*;

import javax.crypto.AEADBadTagException;
import java.lang.reflect.Array;
import java.security.spec.ECField;
import java.util.*;
import java.util.stream.Collectors;

public class ScoringController {
    // TODO: Forgot to account for 0 cases in my hashtables.

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
//                        System.out.println("INCREMENENTED SCORE");
                        numberOfTriples++;
                    }

                }
            }
        }

        int localScore = numberOfTriples * 10;
        return localScore;
    }


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

    public static int scoreElkScoringCardA(PlayerBoard playerBoard) {
     int localScore = 0;

     Set<Tile> usedElkTiles = new HashSet<>();

     // call recursive function to get score
     localScore = getLargestGroup(playerBoard, localScore, usedElkTiles);

     return localScore;
    }

    public static int getLargestGroup(PlayerBoard playerBoard, int totalScore, Set<Tile> usedElkTiles) {
        ArrayList<Tile> largestLine = new ArrayList<>();

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
            return totalScore + getLargestGroup(playerBoard, totalScore, usedElkTiles);
        }
    }


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
                        localScore += 5;
                    } else if (groupSize == 3) {
                        localScore += 9;
                    } else if (groupSize == 4) {
                        localScore += 13;
                    }
                }
            }
        }

        return localScore;
    }

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
                        localScore += 5;
                    } else if (runSize == 3) {
                        localScore += 8;
                    } else if (runSize == 4) {
                        localScore += 12;
                    } else if (runSize == 5) {
                        localScore += 16;
                    } else if (runSize == 6) {
                        localScore += 20;
                    } else if (runSize >= 7) {
                        localScore += 25;
                    }

                }
            }
        }
        return localScore;
    }

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
                        localScore += 9;
                    } else if (runSize >= 4) {
                        localScore += 11;
                    }
                }
            }
        }
        return localScore;
    }

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

                    //increment size
                    if (runSize == 3) {
                        localScore += 10;
                    } else if (runSize == 4) {
                        localScore += 12;
                    } else if (runSize >= 5) {
                        localScore += 15;
                    }
                }
            }
        }
        return localScore;
    }

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

    //helper function to find direct line of sight between two hawks
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

    //helper function for salmon scoring cards to get runs of salmon
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



