/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.controllers;

public class UserTerminationController {

    public static void checkUserInputForProgramTermination(String userInput) {
        if (userInput.equalsIgnoreCase("exit")) {
            UserTerminationController.endProgram();
        }
    }

    public static void endProgram() {

       System.out.println("Program Terminating...");
       System.exit(0);
    }
}
