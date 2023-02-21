package org.grouptwentyone.controllers;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.models.WildlifeToken;
import org.grouptwentyone.views.SelectionOptionsView;

import java.util.Scanner;

public class CullingController {
    public static void checkForCull() {
        WildlifeToken.WildlifeTokenType tokenType1 = StartGame.selectedTokens.get(0).getWildlifeTokenType();
        int occurrence1 = 1;
        WildlifeToken.WildlifeTokenType tokenType2 = WildlifeToken.WildlifeTokenType.EMPTY;
        int occurrence2 = 0;

        for (int i = 1; i < StartGame.selectedTokens.size(); i++) {
            if (StartGame.selectedTokens.get(i).getWildlifeTokenType() == tokenType1) occurrence1++;
            else if(occurrence2 == 0) {
                tokenType2 = StartGame.selectedTokens.get(i).getWildlifeTokenType();
                occurrence2++;
            } else if (StartGame.selectedTokens.get(i).getWildlifeTokenType() == tokenType2) occurrence2++;

            if (occurrence2 > occurrence1) {
                tokenType1 = tokenType2;
                occurrence1 = occurrence2;
                tokenType2 = WildlifeToken.WildlifeTokenType.EMPTY;
                occurrence2 = 0;
            }
        }

        if (occurrence1 == 4) {
            System.out.println("There are 4 tokens of type '" + tokenType1 + "' available, proceeding to cull...\n");
            cullType(tokenType1);
        }
        else if (occurrence1 == 3) { //give user option to cull
            boolean inputComplete = false;
            System.out.print("3 tokens of type '" + tokenType1 + "'are available in selection.\nWould you like to cull these tokens? (y/n)\n>");
            while (!inputComplete) {
                Scanner sc = new Scanner(System.in);
                String userInput = sc.nextLine();

                if (userInput.equalsIgnoreCase("y")) {
                    cullType(tokenType1);
                    inputComplete = true;
                } else if (userInput.equalsIgnoreCase("n")) {
                    System.out.println("No culling performed, continue with selection.");
                    inputComplete = true;
                } else {
                    System.out.print("Invalid input, please re-enter either y or n for yes or no respectively:\n>");
                }
            }
        }
    }

    private static void cullType(WildlifeToken.WildlifeTokenType tokenType) {
        for (int i = 0; i < StartGame.selectedTokens.size(); i++) {
            if (StartGame.selectedTokens.get(i).getWildlifeTokenType() == tokenType) {
                //returns the current token to wildlifeTokenBag and replaces it with another wildlife token
                WildlifeTokensController.wildlifeTokenBag.add(StartGame.selectedTokens.set(i, WildlifeTokensController.wildlifeTokenBag.remove(0)));
            }
        }

        System.out.println("New selection of Wildlife Tokens along with associated Habitat Tiles shown below:");
        System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
        System.out.println(SelectionOptionsView.displaySelectedWiildlifeTokens(StartGame.selectedTokens));
        System.out.println("      (1)            (2)            (3)            (4)      \n");
        checkForCull();

        System.out.println("Culling complete, please continue with selection");
    }
}
