package org.grouptwentyone.models.Exceptions;

public class TokenPlacedAtEmptyPositionException extends RuntimeException {
    public TokenPlacedAtEmptyPositionException(String errorMessage) {
        super(errorMessage);
    }
}
