/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.views;

import org.grouptwentyone.controllers.UserTerminationController;
import org.grouptwentyone.models.CascadiaBot;
import org.grouptwentyone.models.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GameSetupView {

    public static int getNumberOfPlayersFromUser() {
        Scanner sc = new Scanner(System.in);

        GameUiView.printPageBorder();
        System.out.println("Enter Number of Players");

        String userInput;
        int numberOfPlayers;

        // Keep asking until user enters a valid int or exit program (returns -1).
        while (true) {
            userInput = sc.nextLine().trim();

            UserTerminationController.checkUserInputForProgramTermination(userInput);

            if (Objects.equals(userInput.toLowerCase(), "quit")) {
                UserTerminationController.endProgram();
            }

            try {
                numberOfPlayers = Integer.parseInt(userInput);

                if (numberOfPlayers > 4 || numberOfPlayers < 2) {
                    GameUiView.printLargeSpace();
                    GameUiView.printPageBorder();

                    System.out.printf("%sInvalid Input:%s Please enter a number between 2-4 OR type \"quit\" to exit the program%s\n",
                            GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
                } else {
                    return numberOfPlayers;
                }

            } catch (NumberFormatException ex) {

                GameUiView.printLargeSpace();
                GameUiView.printPageBorder();

                System.out.printf("%sInvalid Input:%s Please enter a number between 2-4 OR type \"quit\" to exit the program%s\n",
                        GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
            }
        }

    }

    public static ArrayList<Player> getPlayerInformationFromUser(int numberOfPlayers) {
        ArrayList<String> playerNameList = new ArrayList<>();
        ArrayList<String> playerTypeList = new ArrayList<>();
        String playerName;

        Scanner sc = new Scanner(System.in);

        GameUiView.printLargeSpace();
        GameUiView.printPageBorder();
        System.out.println("Player Names");

        for (int i=0; i<numberOfPlayers; i++) {
            System.out.printf("Enter \"Player %d\" name: ", i + 1);
            playerName = sc.nextLine().trim();

            boolean invalidPlayerName = false;

            if (Objects.equals(playerName, "")) {
                System.out.printf("\n%sInvalid Input:%s A player cannot have an empty name!%s\n", GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
                invalidPlayerName = true;
            }

            // Ensure two players can't have the same name
            for (String existingPlayer: playerNameList) {
                if (Objects.equals(playerName, existingPlayer)) {
                    System.out.printf("\n%sInvalid Input:%s Player name already exists!%s\n", GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
                    invalidPlayerName = true;
                }
            }

            if (invalidPlayerName) {
                i--;
                continue;
            }

            playerNameList.add(playerName);
            playerTypeList.add("PLAYER");
        }

        GameUiView.printLargeSpace();
        ArrayList<Player> playerList = createPlayers(playerNameList, playerTypeList);

        return playerList;
    }

    private static void printUsers(ArrayList<String> playerNameList, ArrayList<String> playerTypeList) {
        // Print display
        int longestNameLength = playerNameList.stream()
                .reduce((x, y) -> x.length() > y.length() ? x: y)
                .get()
                .length();

        int spacingLength = longestNameLength+4;
        for (int i=0; i<playerNameList.size(); i++) {

            String playerType = playerTypeList.get(i).equals("BOT") ? "[BOT]":"[PLAYER]";

            int playerName = playerNameList.get(i).length();
            StringBuilder spacing = new StringBuilder();
            spacing.append(" ".repeat(Math.max(0, spacingLength - playerName)));

            System.out.printf("%d. %s%s%s", i+1, playerNameList.get(i), spacing, playerType);
            System.out.println();
        }
    }

    public static ArrayList<Player> createPlayers(ArrayList<String> playerNameList, ArrayList<String> playerTypeList) {

        String errorMessage = "";
        while (true) {
            GameUiView.printPageBorder();
            printUsers(playerNameList, playerTypeList);
            GameUiView.printPageBorder();

            System.out.println("Type the number of any player you wish to turn into a bot or back into a player.\nOtherwise press \"ENTER\" to continue.");
            System.out.printf("%s", errorMessage);
            errorMessage = "";
            System.out.print("> ");
            String userInput = UserInputView.askUserForInput();
//            System.out.printf("User input was '%s'\n", userInput);

            // User wants to continue;
            if (userInput.trim().equals("")) {
//                System.out.println("ran");
                break;
            }

            int userInputInt;
            // Guards for invalid input
            try {
                userInputInt = Integer.parseInt(userInput.trim());
                if (userInputInt < 1 || userInputInt > playerNameList.size()) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException ex){
                errorMessage = String.format("%sIllegal argument: You must enter a number between 1 and %d OR press \"ENTER\".%s\n", GameUiView.RED_BOLD, playerNameList.size(), GameUiView.RESET_COLOUR);
                GameUiView.printLargeSpace();
                continue;
            }

            String oldPlayerType = playerTypeList.get(userInputInt-1);
            if (oldPlayerType.equals("PLAYER")) {
                playerTypeList.set(userInputInt-1, "BOT");
            } else if (oldPlayerType.equals("BOT")) {
                playerTypeList.set(userInputInt-1, "PLAYER");
            } else {
                System.out.println("This shouldn't run");
            }

            GameUiView.printLargeSpace();
        }


        ArrayList<Player> playerList = new ArrayList<>();
        for (int i=0; i<playerNameList.size(); i++) {
            String username = playerNameList.get(i);
            String playerType = playerTypeList.get(i);

            if (playerType.equals("PLAYER")) {
                playerList.add(new Player(username));
            } else if (playerType.equals("BOT")) {
                playerList.add(new CascadiaBot(username));
            } else {
                throw new IllegalArgumentException("Illegal Player Type");
            }
        }

        return playerList;

    }

    public static void displayPlayerOrder(ArrayList<Player> playerList) {

        StringBuilder output = new StringBuilder();
        GameUiView.printPageBorder();

        System.out.print("Players will play in this order:");

        for (int i=0; i<playerList.size(); i++) {
            output.append(String.format("\n%d. %s", i+1, playerList.get(i).getUserName()));
        }

        System.out.println(output);

        GameUiView.printPageBorder();
        System.out.println("Press \"ENTER\" on your keyboard to continue");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        GameUiView.printLargeSpace();

    }
}
