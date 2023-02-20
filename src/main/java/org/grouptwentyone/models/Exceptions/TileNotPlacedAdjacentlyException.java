package org.grouptwentyone.models.Exceptions;

public class TileNotPlacedAdjacentlyException extends RuntimeException{
    public TileNotPlacedAdjacentlyException(String errorMessage) {
        super(errorMessage);
    }
}
