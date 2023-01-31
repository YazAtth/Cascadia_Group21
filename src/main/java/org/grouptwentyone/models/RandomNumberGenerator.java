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
