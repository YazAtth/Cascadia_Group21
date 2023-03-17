package org.grouptwentyone.views;

import org.grouptwentyone.models.Player;

public class GameUiView {

    // Colours
    public static String RESET_COLOUR = "\u001B[0m";

    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";   // GREEN

    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";


    // Page Items
    public static String PAGE_BORDER_NO_COLOUR = "==========================================================================================";
    public static String PAGE_BORDER = "\033[1;90m" + PAGE_BORDER_NO_COLOUR + RESET_COLOUR;
    public static String PAGE_BORDER_BOTTOM = "\n====================================================\n\n";
    public static String LARGE_SPACE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    public static String SMALL_SPACE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    public static String LINE_PAGE_BORDER_NO_COLOUR = "------------------------------------------------------------------------------------------";







    public static void printLargeSpace() {
        System.out.println(LARGE_SPACE);
    }

    public static void printPageBorder() {
        System.out.println(PAGE_BORDER);
    }

    public static void printLinePageBorder() {
        System.out.println(LINE_PAGE_BORDER_NO_COLOUR);
    }

    public static void printPlayerHeader(Player player) {
        System.out.printf("%s⏺ %s ⏺ Nature Tokens: %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT,
                player.getUserName(), player.getNumOfNatureTokens(), GameUiView.RESET_COLOUR);
    }

}
