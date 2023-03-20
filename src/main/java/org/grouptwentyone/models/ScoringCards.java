package org.grouptwentyone.models;

import java.util.ArrayList;
import java.util.Random;

public class ScoringCards {
    final static ArrayList<ScoreCard> scoreCardsList = assignScoringCards();

    private static ArrayList<ScoreCard> assignScoringCards() {
        ArrayList<ScoreCard> scoreCardsList = new ArrayList<>(5);
        scoreCardsList.add(new ScoreCard(WildlifeToken.WildlifeTokenType.BEAR));
        scoreCardsList.add(new ScoreCard(WildlifeToken.WildlifeTokenType.FOX));
        scoreCardsList.add(new ScoreCard(WildlifeToken.WildlifeTokenType.ELK));
        scoreCardsList.add(new ScoreCard(WildlifeToken.WildlifeTokenType.HAWK));
        scoreCardsList.add(new ScoreCard(WildlifeToken.WildlifeTokenType.SALMON));
        return scoreCardsList;
    }

    public static ArrayList<ScoreCard> getScoreCardsList() {
        return scoreCardsList;
    }

    public static class ScoreCard {
        public enum ScoreType {A, B, C}
        ScoreType scoreType;
        WildlifeToken.WildlifeTokenType tokenType;
        String cardInfo;

        public ScoreCard(WildlifeToken.WildlifeTokenType tokenType) {
            this.tokenType = tokenType;
            this.scoreType = randomScoreType();
            this.cardInfo = assignCardInfo(tokenType, this.scoreType);
        }

        private ScoreCard.ScoreType randomScoreType() {
            int rndNum = new Random().nextInt() % 3;
            switch (rndNum) {
                case 0:
                    return ScoreCard.ScoreType.A;
                case 1:
                    return ScoreCard.ScoreType.B;
                case 2:
                    return ScoreCard.ScoreType.C;
                default:
                    throw new ArithmeticException("Random returned unexpected number: " + rndNum);
            }
        }

        private String assignCardInfo(WildlifeToken.WildlifeTokenType tokenType, ScoreCard.ScoreType scoreType) {
            switch (tokenType) {
                case BEAR:
                    switch (scoreType) {
                        case A: return "Bear (A): Score points on the number of pairs of bears " +
                                       "(not adjacent to any other pairs of bears)";
                        case B: return "Bear (B): Score 10 points per group of 3 bears " +
                                       "(not adjacent to any other groups of bears)";
                        case C: return "Bear (C): info";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                case FOX:
                    switch (scoreType) {
                        case A: return "Fox (A): info";
                        case B: return "Fox (B): info";
                        case C: return "Fox (C): info";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                case ELK:
                    switch (scoreType) {
                        case A: return "Elk (A): info";
                        case B: return "Elk (B): info";
                        case C: return "Elk (C): info";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                case HAWK:
                    switch (scoreType) {
                        case A: return "Hawk (A): info";
                        case B: return "Hawk (B): info";
                        case C: return "Hawk (C): info";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                case SALMON:
                    switch (scoreType) {
                        case A: return "Salmon (A): info";
                        case B: return "Salmon (B): info";
                        case C: return "Salmon (C): info";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                default:
                    throw new IllegalArgumentException("Invalid tokenType passed to method");
            }
        }

        public String getCardInfo() {
            return cardInfo;
        }

        public ScoreType getScoreType() {
            return scoreType;
        }

        public WildlifeToken.WildlifeTokenType getTokenType() {
            return tokenType;
        }
    }
}
