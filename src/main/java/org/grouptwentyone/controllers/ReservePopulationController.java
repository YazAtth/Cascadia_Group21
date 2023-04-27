package org.grouptwentyone.controllers;

import org.grouptwentyone.models.PlayerBoard;
import org.grouptwentyone.models.Tile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;

public class ReservePopulationController {

    public static int getNumberOfAdjacentUniquePlacedWildlifeTokensToFox(PlayerBoard playerBoard) {
        return ScoringController.scoreFoxScoringCardA(playerBoard);
    }

    public static int getNumberOfBearPairsAfterPlacingToken(PlayerBoard playerBoard) {

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

}
