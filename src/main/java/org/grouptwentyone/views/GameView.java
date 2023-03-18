package org.grouptwentyone.views;

import org.grouptwentyone.models.Player;

import java.util.Scanner;

public class GameView {
    static boolean isPreviousInputInvalid = false;
    static String previousInputDisallowedMessage = "";

    public static String askUserForInput() {
        if (!previousInputDisallowedMessage.equals("")) {
            System.out.print(previousInputDisallowedMessage);
            setPreviousInputDisallowedMessage("");
            setIsPreviousInputInvalid(true);
        }

        Scanner sc = new Scanner(System.in);

        return sc.nextLine().toLowerCase().trim();
    }

    public static boolean getUserConfirmation(String option) {
        System.out.print("Would you like to " + option + "? (y/n):\n> ");
        while (true) {
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("y")) {
                return true;
            } else if (userInput.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.print("Invalid input, please re-enter either y or n for yes or no respectively:\n> ");
            }
        }
    }

    public static void displaySelectedTile(Player activePlayer) {

    }

    public static boolean isIsPreviousInputInvalid() {
        return isPreviousInputInvalid;
    }

    public static void setIsPreviousInputInvalid(boolean isPreviousInputInvalid) {
        GameView.isPreviousInputInvalid = isPreviousInputInvalid;
    }

    public static void setPreviousInputDisallowedMessage(String previousInputDisallowedMessage) {
        GameView.previousInputDisallowedMessage = previousInputDisallowedMessage;
        isPreviousInputInvalid = true;
    }
}
