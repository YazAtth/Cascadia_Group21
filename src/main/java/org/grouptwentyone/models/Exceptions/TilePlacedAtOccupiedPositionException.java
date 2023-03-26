/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models.Exceptions;

public class TilePlacedAtOccupiedPositionException extends RuntimeException {
    public TilePlacedAtOccupiedPositionException(String errorMessage) {
        super(errorMessage);
    }
}
