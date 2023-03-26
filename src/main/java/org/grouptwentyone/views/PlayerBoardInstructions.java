package org.grouptwentyone.views;

public class PlayerBoardInstructions {
    String r = GameUiView.RED;
    String g = GameUiView.GREEN_BOLD;
    String y = GameUiView.YELLOW_BOLD_BRIGHT;
    public static void displayBoardInstructions() {
        System.out.println("Each tile will have the possibility of being connect to at most 6 other tiles");
        System.out.println("A tile is adjacent to another tile if and only their corners touch\n" +
                            "Below is sample board which marks all the adjacent tiles of position (2, 1) in green ");
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
                            GameUiView.RED +    "        *   (2,0)   * "  + GameUiView.GREEN +  " *   (2,2)   *                \n" +
                            GameUiView.RED +    "         *         *  "  + GameUiView.GREEN +  "  *         *                 \n" +
                            GameUiView.RED +    "           *  *  *    "  + GameUiView.GREEN +  "    *  *  *                   \n" +
                            GameUiView.RED +    "                   *  *  *        *  *  *                   \n" +
                                                "                 *         *    *         *                 \n" +
                                                "                *           *  *           *                \n" +
                                                "                *   (3,1)   *  *   (3,2)   *                \n" +
                                                "                 *         *    *         *                 \n" +
                                                "                   *  *  *        *  *  *         ");
    }
}
