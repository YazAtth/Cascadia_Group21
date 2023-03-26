/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models.Exceptions;

public class TokenPlacedAtOccupiedPositionException extends RuntimeException {
    public TokenPlacedAtOccupiedPositionException(String errorMessage) {
        super(errorMessage);
    }
}
