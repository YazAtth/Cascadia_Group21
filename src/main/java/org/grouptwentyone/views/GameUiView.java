/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.views;

import org.grouptwentyone.models.Player;
import org.grouptwentyone.models.ScoringCards;

public class GameUiView {

    // Colours
    public static String RESET_COLOUR = "\u001B[0m";

    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";   // GREEN

    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String SILVER_BOLD = "\u001B[1m\033[38;5;245m";
    public static final String BRONZE_BOLD = "\u001B[1m\033[38;5;130m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";

    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW


    // Page Items
    public  static String PAGE_BORDER_THIN_NO_COLOUR = "------------------------------------------------------------------------------------------";
    public static String PAGE_BORDER_NO_COLOUR = "==========================================================================================";
    public static String PAGE_BORDER = "\033[1;90m" + PAGE_BORDER_NO_COLOUR + RESET_COLOUR;
    public static String LARGE_SPACE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    public static String LINE_PAGE_BORDER_NO_COLOUR = "------------------------------------------------------------------------------------------";

    public static void printLargeSpace() {
        System.out.println(LARGE_SPACE);
    }
    public static void printThinBorder() {
        System.out.println(PAGE_BORDER_THIN_NO_COLOUR);
    }

    public static void printPageBorder() {
        System.out.println(PAGE_BORDER);
    }

    public static void printLinePageBorder() {
        System.out.println(LINE_PAGE_BORDER_NO_COLOUR);
    }

    public static void printPlayerHeader(Player player) {
        System.out.printf("%s⏺ Player Name: %s ⏺ Nature Tokens: %s ⏺\n%s", GameUiView.WHITE_BOLD_BRIGHT,
                player.getUserName(), player.getNumOfNatureTokens(), GameUiView.RESET_COLOUR);
        //print scorecard rules below player header for player convenience
        printLinePageBorder();
        //commented out to reduce bloat on computer screen when testing bots
//        printScoreCardRules();
//        printLinePageBorder();
    }

    public static void printScoreCardRules() {
        System.out.println("Scoring card rules:");
        for (ScoringCards.ScoreCard card : ScoringCards.getScoreCardsList()) {
            System.out.println(card.getCardInfo());
        }
    }
}
