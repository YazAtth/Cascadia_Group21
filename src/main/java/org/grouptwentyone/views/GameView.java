package org.grouptwentyone.views;

import java.util.Scanner;

public class GameView {

    static boolean isPreviousInputInvalid = false;
    static String previousInvalidInput = "EMPTY";
    static String previousInputDisallowedMessage = "";

    public static String askUserForInput() {

        if (!previousInputDisallowedMessage.equals("")) {
            System.out.println(previousInputDisallowedMessage);
            setPreviousInputDisallowedMessage("");
        }

        if (!isPreviousInputInvalid()) {
            System.out.println("What action do you wish to take? (type 'help' if you're unsure)");
        } else {
            System.out.printf("\"%s\" is not a valid command, please try again or type \"help\" to see the list of commands\n", getPreviousInvalidInput());
            setIsPreviousInputInvalid(false);
            setPreviousInvalidInput("EMPTY");
        }
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();

        return userInput;

    }

    public static void showHelpPage() {

        System.out.println(GameUiView.GREEN_BOLD);

        System.out.println(GameUiView.LARGE_SPACE);
        System.out.printf("""
                %s
                %s⏺ HELP ⏺%s
              
                %s"help"%s\t\t\topens up this page
                %s"quit"%s\t\t\texits program
                %s"next"%s\t\t\tgoes to the next player's turn
                %s"rotate"%s\t\trotates the tile clockwise
                %s"select"%s\t\tselects a habitat tile and wildlife token pair
                
                %s⏺ DEVELOPER COMMANDS (for debugging) ⏺%s
                
                %s"active"%s\t\tsee details of the player who's turn it is to make an action.
                
                %s%s
                """, GameUiView.PAGE_BORDER_NO_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD,
                GameUiView.PAGE_BORDER_NO_COLOUR);

        System.out.println(GameUiView.RESET_COLOUR);

        System.out.println("Press \"ENTER\" on your keyboard to go back to the game");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();


        GameUiView.printLargeSpace();


    }


    public static boolean isPreviousInputInvalid() {
        return isPreviousInputInvalid;
    }

    public static void setIsPreviousInputInvalid(boolean previousInputInvalid) {
        isPreviousInputInvalid = previousInputInvalid;
    }

    public static String getPreviousInvalidInput() {
        return previousInvalidInput;
    }

    public static void setPreviousInvalidInput(String previousInvalidInput) {
        GameView.previousInvalidInput = previousInvalidInput;
    }

    public static void setPreviousInputDisallowedMessage(String message) {
        previousInputDisallowedMessage = message;
    }
}
