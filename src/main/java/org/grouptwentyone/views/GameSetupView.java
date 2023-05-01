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
import org.grouptwentyone.models.CustomPair;
import org.grouptwentyone.models.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GameSetupView {

    public static CustomPair<Integer, Integer> getNumberOfPlayersFromUser() {
        System.out.printf("<> First you will be asked to enter the %snumber of players%s. \n" +
                "<> Then you will be asked to enter the %snumber of bots%s.\n" +
                "<> %sEnter 0%s if you dont want any of that type.\n",
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR,
                GameUiView.GREEN_BOLD, GameUiView.RESET_COLOUR
                );
        Scanner sc = new Scanner(System.in);

        GameUiView.printPageBorder();

        String userInput;
        int numberOfPlayers, numberOfBots;

        // Keep asking until user enters a valid int or exit program (returns -1).
        while (true) {
            System.out.print("Please enter the number of Players\n> ");
            userInput = sc.nextLine().trim();

            UserTerminationController.checkUserInputForProgramTermination(userInput);

            try {
                numberOfPlayers = Integer.parseInt(userInput);

                System.out.print("Please enter the number of Bots\n> ");

                userInput = sc.nextLine().trim();

                UserTerminationController.checkUserInputForProgramTermination(userInput);

                numberOfBots = Integer.parseInt(userInput);


                if (numberOfPlayers + numberOfBots > 4 || numberOfPlayers + numberOfBots < 2) {
                    GameUiView.printLargeSpace();
                    GameUiView.printPageBorder();

                    System.out.printf("%sInvalid Input:%s Please enter a total number between 2-4 OR type \"quit\" to exit the program%s\n",
                            GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
                } else {
                    return new CustomPair<Integer, Integer>(numberOfPlayers, numberOfBots);
                }

            } catch (NumberFormatException ex) {

                GameUiView.printLargeSpace();
                GameUiView.printPageBorder();

                System.out.printf("%sInvalid Input:%s Please enter a number between 2-4 OR type \"quit\" to exit the program%s\n",
                        GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
            }
        }
    }

    public static ArrayList<Player> getPlayerInformationFromUser(CustomPair<Integer, Integer> numPlayersAndBots) {
        int numberOfPlayers = numPlayersAndBots.getField1();
        int numberOfBots = numPlayersAndBots.getField2();

        ArrayList<String> playerNameList = new ArrayList<>();
        ArrayList<String> playerTypeList = new ArrayList<>();
        String playerName;

        Scanner sc = new Scanner(System.in);

        GameUiView.printLargeSpace();
        System.out.println("Player Names");
        GameUiView.printPageBorder();

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

        for (int i=0; i<numberOfBots; i++) {
            System.out.printf("Enter \"Bot %d\" name: ", i + 1);
            playerName = sc.nextLine().trim();

            boolean invalidPlayerName = false;

            if (Objects.equals(playerName, "")) {
                System.out.printf("\n%sInvalid Input:%s A bot cannot have an empty name!%s\n", GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
                invalidPlayerName = true;
            }

            // Ensure two players can't have the same name
            for (String existingPlayer: playerNameList) {
                if (Objects.equals(playerName, existingPlayer)) {
                    System.out.printf("\n%sInvalid Input:%s Player/Bot name already exists!%s\n", GameUiView.RED_BOLD, GameUiView.RED, GameUiView.RESET_COLOUR);
                    invalidPlayerName = true;
                }
            }

            if (invalidPlayerName) {
                i--;
                continue;
            }

            playerNameList.add(playerName);
            playerTypeList.add("BOT");
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

    public static void displayPlayerOrder(ArrayList<Player> playerList) {

        StringBuilder output = new StringBuilder();
        GameUiView.printPageBorder();

        System.out.print("Players will play in this order:");

        for (int i=0; i<playerList.size(); i++) {
            String playerType = playerList.get(i) instanceof CascadiaBot ? "[BOT]":"[PLAYER]";
            output.append(String.format("\n%d. %s %s", i+1, playerType, playerList.get(i).getUserName()));
        }

        System.out.println(output);

        GameUiView.printPageBorder();
        System.out.println("Press \"ENTER\" on your keyboard to continue");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        GameUiView.printLargeSpace();

    }
}
