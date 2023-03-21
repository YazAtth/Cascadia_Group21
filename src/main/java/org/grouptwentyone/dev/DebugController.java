package org.grouptwentyone.dev;

import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.controllers.WildlifeTokensController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DebugController {

    public static void registerDebugCommand(String inputString) {

        ArrayList<String> debugCommandTokens;
        try {
            debugCommandTokens = parseDebugAction(inputString);
        } catch (IllegalArgumentException ex) {
            System.out.print("FAIL: Debug Action Arguments not passed in\n> ");
            return;
        }

        String command = debugCommandTokens.get(0);
        switch(command) {
            case "endgame":
                removeAllTilesAndTokens();
                break;
            default:
                System.out.print("FAIL: Debug Action Not Recognised\n> ");
                return;
        }

        System.out.print("Debug Action Registered\n> ");
    }

    private static ArrayList<String> parseDebugAction(String inputString) {
        ArrayList<String> debugCommandTokens = Arrays.stream(inputString.split(" ")).collect(Collectors.toCollection(ArrayList::new));

        if (debugCommandTokens.size() < 2) {
            throw new IllegalArgumentException();
        }
        debugCommandTokens.remove(0); // Removes keyword "debug" from list

        return debugCommandTokens;
    }

    private static void removeAllTilesAndTokens() {
        HabitatTilesController.habitatTilesBag = new ArrayList<>();
        WildlifeTokensController.wildlifeTokenBag = new ArrayList<>();
    }
}
