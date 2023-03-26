/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.views;

import org.grouptwentyone.dev.DebugController;

import java.util.Scanner;

public class UserInputView {
    static boolean isPreviousInputInvalid = false;
    static String previousInputDisallowedMessage = "";

    public static String askUserForInput() {
        if (!previousInputDisallowedMessage.equals("")) {
            System.out.print(previousInputDisallowedMessage);
            setPreviousInputDisallowedMessage("");
            setIsPreviousInputInvalid(true);
        }

        Scanner sc = new Scanner(System.in);

        String userInput = sc.nextLine().toLowerCase().trim();

        //debug parameter
        while (userInput.contains("debug")) {
            DebugController.registerDebugCommand(userInput);

            userInput = sc.nextLine().toLowerCase().trim();
        }

        return userInput;
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

    public static void showPressEnterToContinuePrompt(String promptText) {
        System.out.println(promptText);
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static void showPressEnterToContinuePrompt() {
        System.out.println("Press \"ENTER\" on your keyboard to continue");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static boolean isIsPreviousInputInvalid() {
        return isPreviousInputInvalid;
    }

    public static void setIsPreviousInputInvalid(boolean isPreviousInputInvalid) {
        UserInputView.isPreviousInputInvalid = isPreviousInputInvalid;
    }

    public static void setPreviousInputDisallowedMessage(String previousInputDisallowedMessage) {
        UserInputView.previousInputDisallowedMessage = previousInputDisallowedMessage;
        isPreviousInputInvalid = true;
    }
}
