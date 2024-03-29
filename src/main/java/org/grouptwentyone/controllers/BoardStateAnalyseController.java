/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.controllers;

import org.grouptwentyone.models.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BoardStateAnalyseController {

    public static int getNumberOfAdjacentUniquePlacedWildlifeTokensToFox(PlayerBoard playerBoard, Tile focusedTile) {

        int foxScore = 0;
        boolean tileHasFoxToken = focusedTile.getHabitatTile().getWildlifeTokenTypeList().contains(WildlifeToken.WildlifeTokenType.FOX);

        if (tileHasFoxToken) {
            ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentTileList(focusedTile);

            ArrayList<WildlifeToken.WildlifeTokenType> adjacentWildlifeTokenList = new ArrayList<>();

            for (Tile adjacentTile : adjacentTileList) {
                boolean containsNewAdjacentWildlifeToken = !adjacentWildlifeTokenList.contains(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());
                boolean tileHasWildlifeToken = adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() != WildlifeToken.WildlifeTokenType.EMPTY;

                if (containsNewAdjacentWildlifeToken && tileHasWildlifeToken) {
                    adjacentWildlifeTokenList.add(adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType());
                }
            }
            int numberOfAdjacentUniqueAnimalTokens = adjacentWildlifeTokenList.size();
            foxScore += numberOfAdjacentUniqueAnimalTokens;
        }

        return foxScore;
    }

    public static boolean doesBearPlacementRuinPair(PlayerBoard playerBoard, HexCoordinate tileCord) {
        Tile tile = playerBoard.getTileByCoordinate(tileCord.getX(), tileCord.getY());

        ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentTileList(tile);
        int adjacentBears = 0;

        for (Tile adjacentTile : adjacentTileList) {

            if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                    == WildlifeToken.WildlifeTokenType.BEAR) {

                adjacentBears++;
                if (adjacentBears > 1) return true;

                //check to see if adjacent tile with a bear on it is already in a pair
                ArrayList<Tile> adjacentTilesOfAdjacentTileList = playerBoard.getAdjacentTileList(adjacentTile);

                for (Tile adjacentTileOfAdjacentTile : adjacentTilesOfAdjacentTileList) {

                    if (adjacentTileOfAdjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                            == WildlifeToken.WildlifeTokenType.BEAR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getNumberOfBearPairsBeforePlacingToken(PlayerBoard playerBoard) {

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

        return (int) numberOfPairs;
    }

    public static boolean doesBearPlacementMakePair(PlayerBoard playerBoard, HexCoordinate tileCord) {
        Tile tile = playerBoard.getTileByCoordinate(tileCord.getX(), tileCord.getY());
        int adjacentBears = 0;
        int adjacentBearsToAdjacentBear = 0;

        ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentTileList(tile);

        for (Tile adjacentTile : adjacentTileList) {

            if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                    == WildlifeToken.WildlifeTokenType.BEAR) {

                adjacentBears++;
                if (adjacentBears > 1) return false; //two bears are adjacent : not a pair

                //check to see if adjacent tile with a bear on it is already in a pair
                ArrayList<Tile> adjacentTilesOfAdjacentTileList = playerBoard.getAdjacentTileList(adjacentTile);

                for (Tile adjacentTileOfAdjacentTile : adjacentTilesOfAdjacentTileList) {

                    if (adjacentTileOfAdjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType()
                            == WildlifeToken.WildlifeTokenType.BEAR) {
                        adjacentBearsToAdjacentBear++;
                        if (adjacentBearsToAdjacentBear >= 1) {
                            return false;
                        }
                    }
                }
            }
        }
        if (adjacentBears == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int getNumberOfScorableHawksBeforePlacingToken(PlayerBoard playerBoard) {
        ArrayList<Tile> listOfAllHawks = new ArrayList<>();

        //get a list of every hawk on the board
        for (ArrayList<Tile> row : playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {
                if (tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() ==
                        WildlifeToken.WildlifeTokenType.HAWK) {
                    listOfAllHawks.add(tile);
                }
            }
        }

        //remove non-scoring hawks
        ArrayList<Tile> tilesToRemove = new ArrayList<>();
        for (Tile tileInList : listOfAllHawks) {
            ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentNonEmptyTileList(tileInList);
            for (Tile adjacentTile : adjacentTileList) {
                if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() ==
                        WildlifeToken.WildlifeTokenType.HAWK) {
                    tilesToRemove.add(adjacentTile);
                }
            }
        }
        //remove non-scoring hawks from the list of all hawks
        tilesToRemove.removeAll(tilesToRemove);

        return listOfAllHawks.size();
    }

    public static boolean doesHawkPlacementMakeAdjacentHawks(PlayerBoard playerBoard, HexCoordinate tileCord) {
        Tile tile = playerBoard.getTileByCoordinate(tileCord.getX(), tileCord.getY());

        ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentNonEmptyTileList(tile);

        for (Tile adjacentTile : adjacentTileList) {
            //if at least one adjacent hawk is found
            if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() ==
                    WildlifeToken.WildlifeTokenType.HAWK) return true;
        }

        return false;
    }

    public static int getLengthOfRunTileIsIn(Tile root, PlayerBoard playerBoard, ArrayList<Tile> run) {

        run.add(root);

        //base case, is the root adjacent to more than 2 salmon
        if (doesSalmonPlacementRuinRun(playerBoard, root.getHexCoordinate())) {
            return 0;
        }

        ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentTileList(root);
        ArrayList<Tile> adjacentSalmonTiles = new ArrayList<>();

        for (Tile tile : adjacentTileList) {
            if (!run.contains(tile) &&
                    tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.SALMON) {
                adjacentSalmonTiles.add(tile);
            }
        }

        //if salmon tile is at the end of a run
        if (adjacentSalmonTiles.size() == 1) {
            return 1 + getLengthOfRunTileIsIn(adjacentSalmonTiles.get(0), playerBoard, run);
        }
        //if salmon tile is in between a run
        if (adjacentSalmonTiles.size() == 2) {
            return 1 + getLengthOfRunTileIsIn(adjacentSalmonTiles.get(0), playerBoard, run) +
                    getLengthOfRunTileIsIn(adjacentSalmonTiles.get(1), playerBoard, run);
        }

        //end of run
        return 1;
    }


    public static boolean doesSalmonPlacementRuinRun(PlayerBoard playerBoard, HexCoordinate tileCord) {
        Tile tile = playerBoard.getTileByCoordinate(tileCord.getX(), tileCord.getY());
        ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentTileList(tile);
        int adjacentSalmonCount = 0;

        for (Tile adjacentTile : adjacentTileList) {
            if (adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() ==
                    WildlifeToken.WildlifeTokenType.SALMON) {
                adjacentSalmonCount++;
            }
            if (adjacentSalmonCount > 2) {
                return true;
            }
        }
        return false;
     }

    public static PriorityQueue<ArrayList<Tile>> getLinesOfElkFromPosition(PlayerBoard playerBoard, HexCoordinate tileCoord) {
        Tile tile = new Tile(new HabitatTile(WildlifeToken.WildlifeTokenType.ELK), tileCoord);
        ArrayList<Tile> tilesEast = playerBoard.getConnectedSameTilesEastWeight(tile, playerBoard);
        ArrayList<Tile> tilesSouthEast = playerBoard.getConnectedSameTilesSouthEastWeight(tile, playerBoard);
        ArrayList<Tile> tilesSouthWest = playerBoard.getConnectedSameTilesSouthWestWeight(tile, playerBoard);
        ArrayList<Tile> tilesWest = playerBoard.getConnectedSameTilesWestWeight(tile, playerBoard);
        ArrayList<Tile> tilesNorthWest = playerBoard.getConnectedSameTilesNorthWestWeight(tile, playerBoard);
        ArrayList<Tile> tilesNorthEast = playerBoard.getConnectedSameTilesNorthEastWeight(tile, playerBoard);


        PriorityQueue<ArrayList<Tile>> lines = new PriorityQueue<>((o1, o2) -> o2.size() - o1.size());
        lines.add(tilesEast);
        lines.add(tilesSouthEast);
        lines.add(tilesSouthWest);
        lines.add(tilesWest);
        lines.add(tilesNorthEast);
        lines.add(tilesNorthWest);

        return lines;
    }
}
