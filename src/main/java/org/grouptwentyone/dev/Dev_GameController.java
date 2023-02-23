package org.grouptwentyone.dev;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Dev_GameController {

    // Enums prefixed with "DEV_" (developer) are for debugging
    public enum UserAction {HELP, EXIT, INVALID_COMMAND, DEV__PRINT_ACTIVE_PLAYER, NEXT_PLAYER,
        ROTATE_TILE_CLOCKWISE, SELECT_TILE_AND_TOKEN, PLACE_TILE, PLACE_TOKEN, RETURN_TOKEN}


    // Hash that takes in Strings and converts them to enums (much nicer looking than a switch statement)
    private static final Map<String, org.grouptwentyone.dev.Dev_GameController.UserAction> hashmap = Map.ofEntries(
            entry("HELP", org.grouptwentyone.dev.Dev_GameController.UserAction.HELP),
            entry("QUIT", org.grouptwentyone.dev.Dev_GameController.UserAction.EXIT),
            entry("ACTIVE", org.grouptwentyone.dev.Dev_GameController.UserAction.DEV__PRINT_ACTIVE_PLAYER),
            entry("NEXT", org.grouptwentyone.dev.Dev_GameController.UserAction.NEXT_PLAYER),
            entry("ROTATE", org.grouptwentyone.dev.Dev_GameController.UserAction.ROTATE_TILE_CLOCKWISE),
            entry("SELECT", org.grouptwentyone.dev.Dev_GameController.UserAction.SELECT_TILE_AND_TOKEN),
            entry("PLACE_TILE", org.grouptwentyone.dev.Dev_GameController.UserAction.PLACE_TILE),
            entry("PLACE_TOKEN", org.grouptwentyone.dev.Dev_GameController.UserAction.PLACE_TOKEN),
            entry("RETURN_TOKEN", org.grouptwentyone.dev.Dev_GameController.UserAction.RETURN_TOKEN)
    );

    public static org.grouptwentyone.dev.Dev_GameController.UserAction getUserActionFromInput(String userInputCapitalised) {
        userInputCapitalised = userInputCapitalised.toUpperCase();
        String userInputCommandWord = userInputCapitalised.split(" ")[0];

        return hashmap.getOrDefault(userInputCommandWord, org.grouptwentyone.dev.Dev_GameController.UserAction.INVALID_COMMAND);
    }

    public static ArrayList<String> getUserActionCommandArguments(String userInput) {
        ArrayList<String> userCommandArguments = new ArrayList<>(List.of(userInput.split(" ")));
        userCommandArguments.remove(0);


        return userCommandArguments;

    }
}
