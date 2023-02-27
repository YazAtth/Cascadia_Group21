package org.grouptwentyone.controllers;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.views.GameUiView;
import org.grouptwentyone.views.GameView;
import org.grouptwentyone.views.SelectionOptionsView;

import java.util.Arrays;

import static org.grouptwentyone.controllers.HabitatTilesController.habitatTilesBag;
import static org.grouptwentyone.controllers.WildlifeTokensController.wildlifeTokenBag;

public class NatureTokenController {
    public static void natureTokenSelectOption(Player activePlayer){
        int userSelect = -1;
        do {
            try {
                System.out.print("Please enter\n1: to select any combo of habitat tile and Wildlife token.\n" +
                        "2: to wipe any number of wildlife tokens\n>");
                userSelect = Integer.parseInt(GameView.askUserForInput());

            } catch (NumberFormatException ex) {
                GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter number 1 or 2:%s\n> ",
                        GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
            }

            if (userSelect < 1 || userSelect > 2) System.out.printf("%sInvalid input, please enter number 1 or 2:%s\n> ",
                    GameUiView.RED_BOLD, GameUiView.RESET_COLOUR);
        } while(userSelect < 1 || userSelect > 2);

        if (userSelect == 1) {
            selectSpecificTileAndToken(activePlayer);
        } else {
            wipeAnyWildlifeTokens();
        }
    }

    private static void selectSpecificTileAndToken(Player activePlayer) {
        //display then select habitat tiles
        System.out.print(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
        System.out.println("      (1)            (2)            (3)            (4)      \n");

        System.out.print("Please select one of the above habitat tiles by entering the associated number: \n>");
        int userNum = -1;

        while (userNum < 1 || userNum > 4) {
            try {
                userNum = Integer.parseInt(GameView.askUserForInput());
            } catch (NumberFormatException ex) {
                GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                    "between 1 and 4 to select an above tile:%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
            }
            if (userNum < 1 || userNum > 4)
                GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                    "between 1 and 4 to select an above tile:%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
        }
        userNum--;

        activePlayer.getPlayerBoardObject().setSelectedTile(org.grouptwentyone.StartGame.selectedTiles.remove(userNum));

        //checks that tiles still remain in bag, else ends the game
        if (habitatTilesBag.size() > 0) {
            org.grouptwentyone.StartGame.selectedTiles.add(userNum, habitatTilesBag.remove(0));
            System.out.printf("Habitat tile number %s has been selected\n", userNum+1);
        } else {
            StartGame.tilesRemain = false;
        }

        //display then select wildlife tokens
        System.out.print(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));
        System.out.println("      (1)            (2)            (3)            (4)      \n");

        System.out.print("Please select one of the above wildlife tokens by entering the associated number: \n>");
        userNum = -1;

        while (userNum < 1 || userNum > 4) {
            try {
                userNum = Integer.parseInt(GameView.askUserForInput());
            } catch (NumberFormatException ex) {
                GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
                    "between 1 and 4 to select an above token:%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
            }
            if (userNum < 1 || userNum > 4)
                GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter a number " +
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
                "separated by a comma please\n>");

        int[] selectedNums;
        boolean inputInvalid = true;
        while (inputInvalid) {
            try { //creates array of ints from user input
                selectedNums = Arrays.stream(GameView.askUserForInput().split(",|, "))
                        .mapToInt(Integer::parseInt).toArray();
                inputInvalid = false;
                if (selectedNums.length > 4) {
                    GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid amount of numbers," +
                            " please enter a max of 4 numbers%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    inputInvalid = true;
                }


                for (int num : selectedNums) {
                    if (num < 1 || num > 4){
                        GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid number %s, please enter number(s) " +
                                "between 1 and 4 separated by a comma%s\n> ", GameUiView.RED_BOLD, num, GameUiView.RESET_COLOUR));
                        inputInvalid = true;
                        break;
                    }
                }
                for (int num : selectedNums) {
                    //returns the selected token to wildlifeTokenBag and replaces it with another wildlife token
                    WildlifeTokensController.wildlifeTokenBag.add(StartGame.selectedTokens
                            .set(num-1, WildlifeTokensController.wildlifeTokenBag.remove(0)));
                }

                System.out.println("Selected tokens replaced, proceeding to selection now...");
            } catch (NumberFormatException ex) {
                GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid argument, please enter number(s) " +
                        "between 1 and 4 separated by a comma%s\n> ", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                inputInvalid = true;
            }
        }
    }
}
