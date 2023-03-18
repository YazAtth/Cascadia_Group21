package org.grouptwentyone.models;

import java.util.Hashtable;
import java.util.Random;

public class ScoringCards {
    final Hashtable<WildlifeToken.WildlifeTokenType, ScoreCardInfo> scoreCardTable;

    public ScoringCards() {
        this.scoreCardTable = new Hashtable<>(5, 0);
        this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.BEAR, new ScoreCardInfo());
        this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.FOX, new ScoreCardInfo());
        this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.ELK, new ScoreCardInfo());
        this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.HAWK, new ScoreCardInfo());
        this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.SALMON, new ScoreCardInfo());
    }

    private class ScoreCardInfo {
        String cardInfo;
        enum ScoreType {A, B, C}

        private ScoringCardTable.ScoreType randomScoreType() {
            int rndNum = new Random().nextInt() % 3;
            switch (rndNum) {
                case 0:
                    return ScoringCardTable.ScoreType.A;
                case 1:
                    return ScoringCardTable.ScoreType.B;
                case 2:
                    return ScoringCardTable.ScoreType.C;
                default:
                    throw new ArithmeticException("Random returned unexpected number: " + rndNum);
            }
        }
    }

    private class ScoringCardTable {
        enum ScoreType {A, B, C}
        Hashtable<WildlifeToken.WildlifeTokenType, ScoreType> scoreCardTable;

        //creates new object with randomly chosen scorecards
        public ScoringCardTable(Hashtable<WildlifeToken.WildlifeTokenType, ScoreType> scoreCardTable) {
            this.scoreCardTable = new Hashtable<>(5, 0);
            this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.BEAR, randomScoreType());
            this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.FOX, randomScoreType());
            this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.ELK, randomScoreType());
            this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.HAWK, randomScoreType());
            this.scoreCardTable.put(WildlifeToken.WildlifeTokenType.SALMON, randomScoreType());
        }

        private ScoreType randomScoreType() {
            int rndNum = new Random().nextInt() % 3;
            switch (rndNum) {
                case 0:
                    return ScoreType.A;
                case 1:
                    return ScoreType.B;
                case 2:
                    return ScoreType.C;
                default:
                    throw new ArithmeticException("Random returned unexpected number");
            }
        }

        private ScoreType getTokenScoreCardType(WildlifeToken.WildlifeTokenType tokenType) {
            return scoreCardTable.get(tokenType);
        }
    }
}
