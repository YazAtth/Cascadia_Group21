package org.grouptwentyone.controllers;

import org.grouptwentyone.models.WildlifeToken;

import java.util.ArrayList;
import java.util.Collections;

public class WildlifeTokensController {
    public static void main(String[] args) {
        System.out.println(createBagOfWildlifeTokens());
    }

    public static ArrayList<WildlifeToken> createBagOfWildlifeTokens() {
        ArrayList<WildlifeToken> wildlifeTokenBag = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 20; j++) {
                switch (i) {
                    case 0 -> wildlifeTokenBag.add(new WildlifeToken(WildlifeToken.WildlifeTokenType.BEAR));
                    case 1 -> wildlifeTokenBag.add(new WildlifeToken(WildlifeToken.WildlifeTokenType.ELK));
                    case 2 -> wildlifeTokenBag.add(new WildlifeToken(WildlifeToken.WildlifeTokenType.SALMON));
                    case 3 -> wildlifeTokenBag.add(new WildlifeToken(WildlifeToken.WildlifeTokenType.HAWK));
                    case 4 -> wildlifeTokenBag.add(new WildlifeToken(WildlifeToken.WildlifeTokenType.FOX));
                    default -> throw new IllegalArgumentException("num generator failed");
                }
            }
        }

        Collections.shuffle(wildlifeTokenBag);

        return wildlifeTokenBag;
    }
}
