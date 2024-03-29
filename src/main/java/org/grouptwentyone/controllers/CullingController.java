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
import org.grouptwentyone.models.CascadiaBot;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.models.WildlifeToken;
import org.grouptwentyone.views.UserInputView;
import org.grouptwentyone.views.SelectionOptionsView;

public class CullingController {
    private static boolean haveCulled = false;
    public static void checkForCull(Player player) {
        WildlifeToken.WildlifeTokenType tokenType1 = StartGame.selectedTokens.get(0).getWildlifeTokenType();
        int occurrence1 = 1;
        WildlifeToken.WildlifeTokenType tokenType2 = WildlifeToken.WildlifeTokenType.EMPTY;
        int occurrence2 = 0;

//        gets the occurrence of the most common token
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
            if (player.getPlayerBoardObject().isVerbose()) {
                System.out.println("There are 4 tokens of type '" + tokenType1 + "' available, proceeding to cull...\n");
            }
            cullType(tokenType1, player);
        }
        else if (occurrence1 == 3 && !haveCulled && !(player instanceof CascadiaBot)) { //give user option to cull
            System.out.println("3 tokens of type '" + tokenType1 + "' are available in selection.");
            boolean cullTheseTokens = UserInputView.getUserConfirmation("cull these tokens");
            if (cullTheseTokens) {
                haveCulled = true;
                    cullType(tokenType1, player);
            }
        } else if (occurrence1 == 3 && !haveCulled) { //bot automatically culls options for wider selection of tokens
            cullType(tokenType1, player);
        } else {
            if (player.getPlayerBoardObject().isVerbose())
                System.out.println("No culling performed, continue with selection.");
        }
    }

    private static void cullType(WildlifeToken.WildlifeTokenType tokenType, Player player) {
        for (int i = 0; i < StartGame.selectedTokens.size(); i++) {
            if (StartGame.selectedTokens.get(i).getWildlifeTokenType() == tokenType) {
                //returns the current token to wildlifeTokenBag and replaces it with another wildlife token
                WildlifeTokensController.wildlifeTokenBag.add(StartGame.selectedTokens.set(i, WildlifeTokensController.wildlifeTokenBag.remove(0)));
            }
        }

        if (player.getPlayerBoardObject().isVerbose()) {
            System.out.println("New selection of Wildlife Tokens along with associated Habitat Tiles shown below:");
            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
            System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));
            System.out.println("      (1)            (2)            (3)            (4)      \n");
        }

        checkForCull(player);

        //resets variable for next time its called
        haveCulled = false;

        if (player.getPlayerBoardObject().isVerbose())
            System.out.println("Culling complete, please continue with selection");
    }
}
