package org.grouptwentyone.views;

public class PlayerBoardInstructionsView {
    String r = GameUiView.RED;
    String g = GameUiView.GREEN_BOLD;
    String y = GameUiView.YELLOW_BOLD_BRIGHT;
    public static void displayBoardInstructions() {

        GameUiView.printPageBorder();

        System.out.println("- Each tile will have the possibility of being connected to at most 6 other tiles");

        System.out.println("- A tile is adjacent to another tile if and only their corners touch\n\n" +
                            "- Below is sample board which marks all the adjacent tiles of the yellow tile in position (1, 2) in green\n" +
                "and non-adjacent tiles in red.\n");

        System.out.println(  GameUiView.RED +   "           *  *  *    "  + GameUiView.GREEN +  "   *  *  *        *  *  *                   \n" +
                             GameUiView.RED +   "         *         *  "  + GameUiView.GREEN+   " *         *    *         *                 \n" +
                             GameUiView.RED +   "        *           * "  + GameUiView.GREEN+   "*           *  *           *                \n" +
                             GameUiView.RED +   "        *   (0,0)   * "  + GameUiView.GREEN+   "*   (0,1)   *  *   (0,2)   *                \n" +
                             GameUiView.RED +   "         *         *  "  + GameUiView.GREEN+   " *         *    *         *                 \n" +
                             GameUiView.RED +   "           *  *  *    "  + GameUiView.GREEN+   "   *  *  *        *  *  *                   \n" +
                                                "                   * * * *    "  + GameUiView.YELLOW_BOLD_BRIGHT+ "    * * * *    "   + GameUiView.GREEN+ "     *  *  *    \n" +
                                                "                 *         *  "  + GameUiView.YELLOW_BOLD_BRIGHT+ "  *         *  " + GameUiView.GREEN+   "   *         *  \n" +
                                                "                *           * "  + GameUiView.YELLOW_BOLD_BRIGHT+ " *           * " + GameUiView.GREEN+   "  *           * \n" +
                                                "                *   (1,1)   * "  + GameUiView.YELLOW_BOLD_BRIGHT+ " *   (1,2)   * " + GameUiView.GREEN+   "  *   (1,3)   * \n" +
                                                "                 *         *  "  + GameUiView.YELLOW_BOLD_BRIGHT+ "  *         *  " + GameUiView.GREEN+   "   *         *  \n" +
                                                "                   * * * *    "  + GameUiView.YELLOW_BOLD_BRIGHT+ "    * * * *    " + GameUiView.GREEN+   "     *  *  *    \n" +
                            GameUiView.RED +    "           *  *  *    "  + GameUiView.GREEN +  "    *  *  *                   \n" +
                            GameUiView.RED +    "         *         *  "  + GameUiView.GREEN +  "  *         *                 \n" +
                            GameUiView.RED +    "        *           * "  + GameUiView.GREEN +  " *           *                \n" +
                            GameUiView.RED +    "        *   (2,0)   * "  + GameUiView.GREEN +  " *   (2,1)   *                \n" +
                            GameUiView.RED +    "         *         *  "  + GameUiView.GREEN +  "  *         *                 \n" +
                            GameUiView.RED +    "           *  *  *    "  + GameUiView.GREEN +  "    *  *  *                   \n" +
                            GameUiView.RED +    "                   *  *  *        *  *  *                   \n" +
                                                "                 *         *    *         *                 \n" +
                                                "                *           *  *           *                \n" +
                                                "                *   (3,1)   *  *   (3,2)   *                \n" +
                                                "                 *         *    *         *                 \n" +
                                                "                   *  *  *        *  *  *         ");

        System.out.println();
    }
}
