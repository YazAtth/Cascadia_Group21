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

        while (userInput.contains("debug")) {
//            System.out.print("Debug Action Registered\n> ");
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
