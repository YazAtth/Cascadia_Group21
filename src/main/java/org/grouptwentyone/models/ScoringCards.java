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
            int rndNum = new Random().nextInt(3);
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


//                .-------.
//                | BEAR  |
//                |   ^   |
//                |  ---  |
//                | /   \ |
//                `-------'
//                .-------.
//                | BEAR  |
//                |  __   |
//                | |__ ) |
//                | |__ ) |
//                `-------'
//                .-------.
//                | BEAR  |
//                |  ___  |
//                | |     |
//                | |___  |
//                `-------'



        private String assignCardInfo(WildlifeToken.WildlifeTokenType tokenType, ScoreCard.ScoreType scoreType) {
            switch (tokenType) {
                case BEAR:
                    switch (scoreType) {
                        case A: return ".-------.\n"    +
                                       "| BEAR  |   Bear (A): Score points on the number of pairs of bears\n" +
                                       "|   ^   |   (not adjacent to any other pairs of bears)\n" +
                                       "|  ---  |   Points per number of pairs: 4, 11, 19, 27\n"  +
                                       "| /   \\ |\n" +
                                       "`-------'"
                                ;


                        case B: return ".-------.\n"    +
                                       "| BEAR  |   Bear (B): Score 10 points per group of 3 bears\n" +
                                       "|  __   |   (not adjacent to any other bears)\n"
                                     + "| |__ ) |\n" +
                                       "| |__ ) |\n" +
                                       "`-------'";
                        case C: return ".-------.\n"   +
                                       "| BEAR  |   Bear (C): First get 8 points for group of 3 bears, then 5 points for a group of\n" +
                                       "|  ___  |   2 bears, then get 2 points for a singular bear. If you have each type of group of bears,\n" +
                                       "| |     |   get an extra 2 points. After finding each group, exclude any bears adjacent to that group\n"
                                     + "| |___  |\n" +  "`-------'";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                case FOX:

                    switch (scoreType) {
                        case A: return
                                ".-------.\n"    +
                                "| FOX   |   Fox (A): Score one point for each unique wildlife type adjacent to the fox,\n" +
                                "|   ^   |   including another fox.\n" +
                                "|  ---  |\n"  +
                                "| /   \\ |\n" +
                                "`-------'"
                                ;
                        case B: return
                                ".-------.\n"    +
                                "| FOX   |   Fox (B): Score points for each unique pair of wildlife types adjacent to the fox,\n" +
                                "|  __   |   excluding other foxes. The pairs themselves don't need to be adjacent to each other.\n"
                              + "| |__ ) |   Points are: 3 for 1 pair, 5 for 2 pairs, 7 for 3.\n" +
                                "| |__ ) |\n" +
                                "`-------'";
                        case C: return
                                ".-------.\n"   +
                                "| FOX   |   Fox (C): Score a point for each token of the most abundant wildlife type\n" +
                                "|  ___  |   adjacent to the fox. Other adjacent foxes are excluded from scoring.\n" +
                                "| |     |\n"
                                + "| |___  |\n" +  "`-------'";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }

                case ELK:
                    switch (scoreType) {
                        case A: return
                                ".-------.\n"    +
                                "| ELK   |   Elk (A): Score points for each straight line (doesn't need to be horizontal),\n" +
                                "|   ^   |   of elk. Lines may be adjacent to each other, but each elk may only be part of 1 line.\n" +
                                "|  ---  |   Points earned per elk: 2, 5, 9, 13.\n"  +
                                "| /   \\ |\n" +
                                "`-------'"
                                ;

                        case B: return
                                ".-------.\n"    +
                                "| ELK   |   Elk (B): Score points for each contiguous group of adjacent elk in any shape.\n" +
                                "|  __   |   Points for each elk in group: 2, 4, 7, 10, 14, 18, 23, 28.\n"
                                + "| |__ ) |\n" +
                                "| |__ ) |\n" +
                                "`-------'";
//                        "Elk (C): Score points per group of elk in following shapes (adjacent groups are " +
//                                "allowed but each elk can only be part of 1 group.\n\t13 points for 4 elk in a diamond shape." +
//                                "9 points for a group of 3 elk (not in a line). 5 points for a group of 2 elk. 2 points " +
//                                "for 1 elk.";
                        case C: return
                                ".-------.\n"   +
                                "| ELK   |   Elk (C): Score points per group of elk in following shapes (adjacent groups are\n" +
                                "|  ___  |   allowed but each elk can only be part of 1 group.\\n\\t13 points for 4 elk in a\n" +
                                "| |     |   diamond shape. 9 points for a group of 3 elk (not in a line). 5 points for a\n"
                                + "| |___  | group of 2 elk. 2 points for 1 elk.\n"
                                        +  "`-------'";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                case HAWK:

                    switch (scoreType) {
                        case A: return
                                        ".-------.\n"    +
                                        "| HAWK  |   Hawk (A): Score points for each hawk (hawks adjacent to other hawks do not count\n" +
                                        "|   ^   |   towards score).\\n\\tPoints: 2, 5, 8, 11, 14, 18, 22, 26.\n" +
                                        "|  ---  |\n"  +
                                        "| /   \\ |\n" +
                                        "`-------'"
                                ;
                        case B: return "Hawk (B): Get points for each pair of hawks with direct line of sight. The hawks " +
                                "must be separated by exactly 1 habitat tile that has no placed wildlife token.\n\t" +
                                "Points per pair: 2, 5, 9, 12, 16, 20, 24, 28.";
                        case C: return "Hawk (C): Score 3 points for each pair of hawks in direct line of sight. Hawks " +
                                "can be members of multiple pairs. Habitat tiles in between must be empty.";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                case SALMON:
                    switch (scoreType) {
                        case A: return "Salmon (A): For each run of salmon, score points for each salmon that's a part of " +
                                "it. 7 or more salmon fetch the same score score.\n\tPoints: 2, 4, 7, 11, 15, 20, 26.";
                        case B: return "Salmon (B): For each run of salmon, score points for each salmon that's a part of " +
                                "it. 4 or more salmon fetch the same score score.\n\tPoints: 2, 4, 8, 12.";
                        case C: return "Salmon (C): For each run of salmon, score points for each salmon that's a part of " +
                                "it. 5 or more salmon fetch the same score score.\n\tPoints: 2, 4, 9, 11, 17.";
                        default: throw new IllegalArgumentException("Invalid ScoreType passed to method");
                    }
                default:
                    throw new IllegalArgumentException("Invalid tokenType passed to method");
            }
        }

        public String getCardInfo() {
            return this.cardInfo;
        }

        public ScoreType getScoreType() {
            return this.scoreType;
        }

        public WildlifeToken.WildlifeTokenType getTokenType() {
            return this.tokenType;
        }
    }
}
