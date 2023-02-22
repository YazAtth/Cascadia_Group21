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
                "Type = " + wildlifeTokenType;
    }

}
