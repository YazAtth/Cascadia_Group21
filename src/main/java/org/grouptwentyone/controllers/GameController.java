package org.grouptwentyone.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class GameController {

    // Enums prefixed with "DEV_" (developer) are for debugging
    public enum UserAction {HELP, EXIT, INVALID_COMMAND, DEV__PRINT_ACTIVE_PLAYER, NEXT_PLAYER, ROTATE_TILE_CLOCKWISE, SELECT_TILE_AND_TOKEN, PLACE_TILE_AND_TOKEN}


    // Hash that takes in Strings and converts them to enums (much nicer looking than a switch statement)
    private static final Map<String, UserAction> hashmap = Map.ofEntries(
            entry("HELP", UserAction.HELP),
            entry("QUIT", UserAction.EXIT),
            entry("ACTIVE", UserAction.DEV__PRINT_ACTIVE_PLAYER),
            entry("NEXT", UserAction.NEXT_PLAYER),
            entry("ROTATE", UserAction.ROTATE_TILE_CLOCKWISE),
            entry("SELECT", UserAction.SELECT_TILE_AND_TOKEN),
            entry("PLACE", UserAction.PLACE_TILE_AND_TOKEN)
    );

    public static UserAction getUserActionFromInput(String userInputCapitalised) {
        userInputCapitalised = userInputCapitalised.toUpperCase();
        String userInputCommandWord = userInputCapitalised.split(" ")[0];

        return hashmap.getOrDefault(userInputCommandWord, UserAction.INVALID_COMMAND);
    }

    public static ArrayList<String> getUserActionCommandArguments(String userInput) {
        ArrayList<String> userCommandArguments = new ArrayList<>(List.of(userInput.split(" ")));
        userCommandArguments.remove(0);


        return userCommandArguments;

    }
}
