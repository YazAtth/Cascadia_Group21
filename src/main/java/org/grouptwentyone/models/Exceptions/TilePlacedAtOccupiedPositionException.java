package org.grouptwentyone.models.Exceptions;

public class TilePlacedAtOccupiedPositionException extends RuntimeException {
    public TilePlacedAtOccupiedPositionException(String errorMessage) {
        super(errorMessage);
    }
}
