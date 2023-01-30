package org.grouptwentyone.views;

import org.grouptwentyone.controllers.UserTerminationController;
import org.grouptwentyone.models.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class GameSetupView {

    public static int getNumberOfPlayersFromUser() {
        Scanner sc = new Scanner(System.in);

        System.out.println(GameUiView.PAGE_BORDER_TOP);
        System.out.println("Enter Number of Players");

        String userInput;
        int numberOfPlayers = -1;

        // Keep asking until user enters a valid int or exit program (returns -1).
        while (true) {
            userInput = sc.nextLine();

            UserTerminationController.checkUserInputForProgramTermination(userInput);

            try {
                numberOfPlayers = Integer.parseInt(userInput);
                return numberOfPlayers;

            } catch (NumberFormatException ex) {

                System.out.println(GameUiView.LARGE_SPACE);
                System.out.println(GameUiView.PAGE_BORDER_TOP);

                System.out.println("Invalid Input: Please enter a number between 2-4 OR type 'exit' to exit program");
            }
        }

    }

    public static ArrayList<Player> getPlayerNamesFromUser(int numberOfPlayers) {
        ArrayList<Player> playerList = new ArrayList<>();
        String playerName;

        Scanner sc = new Scanner(System.in);

        System.out.println(GameUiView.LARGE_SPACE);
        System.out.println(GameUiView.PAGE_BORDER_TOP);
        System.out.println("Player Names");

        for (int i=0; i<numberOfPlayers; i++) {
            System.out.printf("Enter \"Player %d\" name: ", i + 1);
            playerName = sc.nextLine();
            playerList.add(new Player(playerName));
        }

        System.out.println(GameUiView.LARGE_SPACE);

        return playerList;
    }
}
