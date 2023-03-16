package org.grouptwentyone.controllers;

import org.grouptwentyone.models.PlayerBoard;
import org.grouptwentyone.models.Tile;
import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoringController {

    public static int scoreFoxScoringCardA(PlayerBoard playerBoard) {

        int localScore = 0;

        for (ArrayList<Tile> row: playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile: row) {
                boolean tileHasFoxToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.FOX;
                if (tileHasFoxToken) {
//                    System.out.println("FOUND FOX");
                    ArrayList<Tile> adjacentTileList = playerBoard.getAdjacentTileList(tile);

                    ArrayList<WildlifeToken.WildlifeTokenType> adjacentWildlifeTokenList = new ArrayList<>();

                    for (Tile adjacentTile: adjacentTileList) {
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
        ArrayList<WildlifeToken.WildlifeTokenType> adjacentWildlifeTokensWithOneOccurrence = new ArrayList<>();
        ArrayList<WildlifeToken.WildlifeTokenType> adjacentWildlifeTokensWithPairOccurrence = new ArrayList<>();

        for (ArrayList<Tile> row: playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile : row) {

                boolean tileHasFoxToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.FOX;
                if (tileHasFoxToken) {

                    for (Tile adjacentTile: playerBoard.getAdjacentNonEmptyTileList(tile)) {
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
                }
            }
        }

        int numberOfUniquePairs = adjacentWildlifeTokensWithPairOccurrence.size();
        localScore += numberOfUniquePairs;

        return localScore;
    }


    public static int scoreFoxScoringCardC(PlayerBoard playerBoard) {

        int localScore = 0;

        for (ArrayList<Tile> row: playerBoard.getPlayerBoardAs2dArray()) {
            for (Tile tile: row) {

                boolean tileHasFoxToken = tile.getHabitatTile().getWildlifeToken().getWildlifeTokenType() == WildlifeToken.WildlifeTokenType.FOX;
                if (tileHasFoxToken) {

                    HashMap<WildlifeToken.WildlifeTokenType, Integer> wildlifeTokenScores = new HashMap<>();
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.BEAR, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.FOX, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.ELK, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.HAWK, 0);
                    wildlifeTokenScores.put(WildlifeToken.WildlifeTokenType.SALMON, 0);

                    for (Tile adjacentTile: playerBoard.getAdjacentNonEmptyTileList(tile)) {
                        WildlifeToken.WildlifeTokenType adjacentTileWildlifeTokenType = adjacentTile.getHabitatTile().getWildlifeToken().getWildlifeTokenType();
                        int tokenScore = wildlifeTokenScores.get(adjacentTileWildlifeTokenType);
                        wildlifeTokenScores.put(adjacentTileWildlifeTokenType, tokenScore+1);
                    }

                    int maxTokenScore = 0;
                    WildlifeToken.WildlifeTokenType maxTokenType = WildlifeToken.WildlifeTokenType.EMPTY;

                    for (HashMap.Entry<WildlifeToken.WildlifeTokenType, Integer> entry: wildlifeTokenScores.entrySet()) {
                        WildlifeToken.WildlifeTokenType tokenType = entry.getKey();
                        int tokenScore = entry.getValue();

                        if (tokenScore > maxTokenScore) {
                            maxTokenScore = tokenScore;
                            maxTokenType = tokenType;
                        }
                    }

                    localScore += maxTokenScore;
                    System.out.println(maxTokenType);

                }


            }
        }

        return localScore;
    }
}
