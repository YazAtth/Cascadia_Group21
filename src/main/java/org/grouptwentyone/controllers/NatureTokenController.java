/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.controllers;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.views.GameUiView;
import org.grouptwentyone.views.UserInputView;
import org.grouptwentyone.views.SelectionOptionsView;

import java.util.Arrays;
import java.util.HashSet;

import static org.grouptwentyone.controllers.HabitatTilesController.habitatTilesBag;
import static org.grouptwentyone.controllers.WildlifeTokensController.wildlifeTokenBag;

public class NatureTokenController {
    public static boolean natureTokenSelectOption(Player activePlayer){
        int userSelect = -1;
        do {
            try {
                System.out.print("""
                        Please enter
                        1: to select any combo of habitat tile and Wildlife token.
                        2: to wipe any number of wildlife tokens
                        > \s""");
                userSelect = Integer.parseInt(UserInputView.askUserForInput());

            } catch (NumberFormatException ex) {
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter number 1 or 2:%s\n> ",
                        GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
            }

            if (userSelect < 1 || userSelect > 2) System.out.printf("%sInvalid input, please enter number 1 or 2:%s\n> ",
                    GameUiView.RED_BOLD, GameUiView.RESET_COLOUR);
        } while(userSelect < 1 || userSelect > 2);

        if (userSelect == 1) {
            selectSpecificTileAndToken(activePlayer);
            return true;
        } else {
            wipeAnyWildlifeTokens();
            return false;
        }
    }

    private static void selectSpecificTileAndToken(Player activePlayer) {
        //display then select habitat tiles
        System.out.print(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
        System.out.println("      (1)            (2)            (3)            (4)      \n");

        System.out.print("Please select one of the above habitat tiles by entering the associated number: \n> ");
        int userNum = -1;

        while (userNum < 1 || userNum > 4) {
            try {
                userNum = Integer.parseInt(UserInputView.askUserForInput());
            } catch (NumberFormatException ex) {
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                    "between 1 and 4 to select an above tile:%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
            }
            if (userNum < 1 || userNum > 4)
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                    "between 1 and 4 to select an above tile:%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
        }
        userNum--;

        activePlayer.getPlayerBoardObject().setSelectedTile(org.grouptwentyone.StartGame.selectedTiles.remove(userNum));

        //checks that tiles still remain in bag, else starts end of game process
        if (habitatTilesBag.size() > 0) {
            org.grouptwentyone.StartGame.selectedTiles.add(userNum, habitatTilesBag.remove(0));
            System.out.printf("Habitat tile number %s has been selected\n", userNum+1);
        } else {
            StartGame.tilesRemain = false;
        }

        //display then select wildlife tokens
        System.out.print(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));
        System.out.println("      (1)            (2)            (3)            (4)      \n");

        System.out.print("Please select one of the above wildlife tokens by entering the associated number: \n> ");
        userNum = -1;

        while (userNum < 1 || userNum > 4) {
            try {
                userNum = Integer.parseInt(UserInputView.askUserForInput());
            } catch (NumberFormatException ex) {
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                    "between 1 and 4 to select an above token:%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
            }
            if (userNum < 1 || userNum > 4)
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                    "between 1 and 4 to select an above token:%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
        }
        userNum--;

        activePlayer.getPlayerBoardObject().setSelectedToken(org.grouptwentyone.StartGame.selectedTokens.remove(userNum));
        System.out.printf("Wildlife token number %s has been selected\n", userNum+1);

        //replaces selected token
        org.grouptwentyone.StartGame.selectedTokens.add(userNum, wildlifeTokenBag.remove(0));
    }

    //still need to check for duplicate numbers
    private static void wipeAnyWildlifeTokens() {
        System.out.print(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));
        System.out.println("      (1)            (2)            (3)            (4)      \n");
        System.out.print("Please enter the number(s) of the Wildlife tokens you wish to replace, " +
                "separated by a comma please\n> ");

        int[] selectedNums;
        boolean inputInvalid = true;
        while (inputInvalid) {
            try { //creates array of integers from user input
                selectedNums = Arrays.stream(UserInputView.askUserForInput().split(", |,"))
                        .mapToInt(Integer::parseInt).toArray();
                inputInvalid = false;

                //error checking to check for correct input
                if (selectedNums.length > 4) {
                    UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid amount of numbers," +
                            " please enter a max of 4 numbers%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    inputInvalid = true;
                    continue;
                }
                HashSet<Integer> numSet = new HashSet<>();
                for (int selectedNum : selectedNums) {
                    numSet.add(selectedNum);
                }
                if (numSet.size() > 4) {
                    UserInputView.setPreviousInputDisallowedMessage(String.format("%sA duplicate number has been entered, " +
                            "please only enter unique numbers between 1 and 4%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    inputInvalid = true;
                    continue;
                }
                for (int num : selectedNums) {
                    if (num < 1 || num > 4){
                        UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid number %s, please enter number(s) " +
                                "between 1 and 4 separated by a comma%s\n> ", GameUiView.RED_BOLD, num, GameUiView.RESET_COLOUR));
                        inputInvalid = true;
                        break;
                    }
                }
                if(inputInvalid) continue;

                for (int num : selectedNums) {
                    //returns the selected token to wildlifeTokenBag and replaces it with another wildlife token
                    WildlifeTokensController.wildlifeTokenBag.add(StartGame.selectedTokens
                            .set(num-1, WildlifeTokensController.wildlifeTokenBag.remove(0)));
                }

                System.out.println("Selected tokens replaced, proceeding to selection now...");
                System.out.println("\n");
                GameUiView.printPageBorder();
                System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
                System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));
                System.out.println("      (1)            (2)            (3)            (4)      \n");

            } catch (NumberFormatException ex) {
                UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter number(s) " +
                        "between 1 and 4 separated by a comma%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                inputInvalid = true;
            }
        }
    }
}
