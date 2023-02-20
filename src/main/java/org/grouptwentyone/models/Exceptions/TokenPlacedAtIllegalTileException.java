package org.grouptwentyone.models.Exceptions;

public class TokenPlacedAtIllegalTileException extends RuntimeException {
    public TokenPlacedAtIllegalTileException(String errorMessage) {
        super(errorMessage);
    }
}
