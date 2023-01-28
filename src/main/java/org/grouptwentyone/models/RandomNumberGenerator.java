package org.grouptwentyone.models;

public class RandomNumberGenerator {

    public static int getRandomNumberInRange(int minInclusive, int maxInclusive) {

        if (minInclusive > maxInclusive) {
            throw new IllegalArgumentException(String.format("Your min number: %d is greater than your max number: %d", minInclusive, maxInclusive));
        }

        int min = minInclusive;
        int max = maxInclusive+1;

        return (int) ((Math.random() * (max-min)) + min);
    }
}
