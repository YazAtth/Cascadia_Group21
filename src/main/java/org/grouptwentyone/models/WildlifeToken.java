package org.grouptwentyone.models;

public class WildlifeToken {
    enum WildlifeTokenType {BEAR, ELK, SALMON, HAWK, FOX, EMPTY} //EMPTY is used for initialising habitat tiles
    private WildlifeTokenType wildlifeTokenType;

    public WildlifeToken(WildlifeTokenType wildlifeTokenType) {
        this.wildlifeTokenType = wildlifeTokenType;
    }

    public WildlifeTokenType getWildlifeTokenType() {
        return wildlifeTokenType;
    }
}
