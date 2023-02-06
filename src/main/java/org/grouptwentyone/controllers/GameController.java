package org.grouptwentyone.controllers;

import java.util.Hashtable;
import java.util.Map;

import static java.util.Map.entry;

public class GameController {

    // Enums prefixed with "DEV_" (developer) are for debugging
    public enum UserAction {HELP, EXIT, INVALID_COMMAND, DEV__PRINT_ACTIVE_PLAYER, DEV__NEXT_PLAYER}


    // Hash that takes in Strings and converts them to enums (much nicer looking than a switch statement)
    private static final Map<String, UserAction> hashmap = Map.ofEntries(
            entry("HELP", UserAction.HELP),
            entry("QUIT", UserAction.EXIT),
            entry("ACTIVE", UserAction.DEV__PRINT_ACTIVE_PLAYER),
            entry("NEXT", UserAction.DEV__NEXT_PLAYER)
    );

    public static UserAction getUserActionFromInput(String userInputCapitalised) {
        userInputCapitalised = userInputCapitalised.toUpperCase();
        return hashmap.getOrDefault(userInputCapitalised, UserAction.INVALID_COMMAND);
    }
}
