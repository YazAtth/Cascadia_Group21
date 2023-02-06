package org.grouptwentyone.views;

import java.util.Scanner;

public class GameView {

    static boolean isPreviousInputInvalid = false;
    static String previousInvalidInput = "EMPTY";

    public static String askUserForInput() {


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
        System.out.println(GameUiView.LARGE_SPACE);
        System.out.printf("""
                %s
                --- HELP ---
              
                "help"\t\t\topens up this page
                "exit"\t\t\texits program
                
                --- DEVELOPER COMMANDS (for debugging) ---
                "active"\t\tsee details of the player who's turn it is to make an action.
                "next"\t\t\tmake the current player forfeit their turn and move to the next active player
                
                %s
                """, GameUiView.PAGE_BORDER, GameUiView.PAGE_BORDER);

        System.out.println("Press \"ENTER\" on your keyboard to go back to the game");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();


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
}
