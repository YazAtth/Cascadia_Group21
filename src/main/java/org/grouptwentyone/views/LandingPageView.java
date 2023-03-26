/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.views;

public class LandingPageView {


    final private static String asciiTitleText = """
             ________  ________  ________  ________  ________  ________  ___  ________    \s
            |\\   ____\\|\\   __  \\|\\   ____\\|\\   ____\\|\\   __  \\|\\   ___ \\|\\  \\|\\   __  \\   \s
            \\ \\  \\___|\\ \\  \\|\\  \\ \\  \\___|\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\_|\\ \\ \\  \\ \\  \\|\\  \\  \s
             \\ \\  \\    \\ \\   __  \\ \\_____  \\ \\  \\    \\ \\   __  \\ \\  \\ \\\\ \\ \\  \\ \\   __  \\ \s
              \\ \\  \\____\\ \\  \\ \\  \\|____|\\  \\ \\  \\____\\ \\  \\ \\  \\ \\  \\_\\\\ \\ \\  \\ \\  \\ \\  \\\s
               \\ \\_______\\ \\__\\ \\__\\____\\_\\  \\ \\_______\\ \\__\\ \\__\\ \\_______\\ \\__\\ \\__\\ \\__\\
                \\|_______|\\|__|\\|__|\\_________\\|_______|\\|__|\\|__|\\|_______|\\|__|\\|__|\\|__|
                                   \\|_________|                                           \s
            """;

    final private static String asciiInitials = """   
   __________  __  _______ ___   ____  ____  __________ 
  / ____/ __ \\/  |/  / __ \\__ \\ / __ \\/ __ \\/ ____/ __ \\    DOMINYKAS JAKUBAUSKAS
 / /   / / / / /|_/ / /_/ /_/ // / / / / / /___ \\/ / / /    YASITH ATTHANAYAKE
/ /___/ /_/ / /  / / ____/ __// /_/ / /_/ /___/ / /_/ /     COLM Ó HAONGHUSA
\\____/\\____/_/  /_/_/   /____/\\____/\\____/_____/\\____/  
                                                        
            """;
    public static void show() {
        System.out.println(asciiTitleText + asciiInitials);
        UserInputView.showPressEnterToContinuePrompt();
    }
}
