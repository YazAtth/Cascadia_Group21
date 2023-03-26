/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

public class RandomNumberGenerator {

    public static int getRandomNumberInRange(int minInclusive, int maxInclusive) {

        if (minInclusive > maxInclusive) {
            int temp = minInclusive;
            minInclusive = maxInclusive;
            maxInclusive = temp;
        }

        int min = minInclusive;
        int max = maxInclusive+1;

        return (int) ((Math.random() * (max-min)) + min);
    }
}
