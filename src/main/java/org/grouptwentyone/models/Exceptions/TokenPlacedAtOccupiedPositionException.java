package org.grouptwentyone.models.Exceptions;

public class TokenPlacedAtOccupiedPositionException extends RuntimeException {
    public TokenPlacedAtOccupiedPositionException(String errorMessage) {
        super(errorMessage);
    }
}
