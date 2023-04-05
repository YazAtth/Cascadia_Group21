/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

public class WildlifeToken {
    public enum WildlifeTokenType {BEAR, ELK, SALMON, HAWK, FOX, EMPTY} //EMPTY is used for initialising habitat tiles

    WildlifeTokenType wildlifeTokenType;

    public WildlifeToken(WildlifeTokenType wildlifeTokenType) {
        this.wildlifeTokenType = wildlifeTokenType;
    }

    public WildlifeTokenType getWildlifeTokenType() {
        return this.wildlifeTokenType;
    }

    public void setWildlifeTokenType(WildlifeTokenType wildlifeTokenType) {
        this.wildlifeTokenType = wildlifeTokenType;
    }

    @Override
    public String toString() {
        return "WildlifeToken: " +
                "Type = " + this.wildlifeTokenType;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof WildlifeToken other)) return false;

        return this.getWildlifeTokenType() == other.getWildlifeTokenType();
    }

}
