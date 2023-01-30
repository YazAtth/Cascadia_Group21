package org.grouptwentyone.controllers;

public class GameController {

    // Enums prefixed with "DEV_" (developer) are for debugging
    public enum UserAction {HELP, EXIT, INVALID_COMMAND, DEV__PRINT_ACTIVE_PLAYER, DEV__NEXT_PLAYER}

    public static UserAction getUserActionFromInput(String userInput) {


        switch (userInput.toUpperCase()) {
            case "HELP":
                return UserAction.HELP;
            case "EXIT":
                return UserAction.EXIT;
            case "ACTIVE":
                return UserAction.DEV__PRINT_ACTIVE_PLAYER;
            case "NEXT":
                return UserAction.DEV__NEXT_PLAYER;
            default:
                return UserAction.INVALID_COMMAND;
        }

    }
}
