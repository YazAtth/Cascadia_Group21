package org.grouptwentyone.models.WeightValueMaps;

import java.util.HashMap;

/*  FOR HABITAT TILES
    We want to place a habitat tile that will allow the possibility to get a large line of elk.
    For each ghost tile, we analyse its adjacent non-ghost tile to see if placing a tile will allow
    us to make a line of elk.

    FOR TOKENS
    For any given non-ghost habitat tile that contains an elk icon, we want to see what
    would be the length of the line of elk we get as a result of placing an elk token on that
    tile.
    For example, if placing an elk token on a tile gets us a length of line (i.e, there are no
    other elks around the token to make a line) we return 0.9.
    example 2: if placing an elk token on a tile gets us a run of two (i.e, there was already one
    elk to connect to, and now we could make a line of two. In this case, we would return a weight of
    2.65

    SPECIAL CASES
    If you look at elk scoring A on brightSpace, if two lines intersect, we count the points for the
    bigger one, remove those tiles then do the same. For example let "x" denote elk token place on habitat
    tiles:
             x
           x x x x
             x
     We have a line of 4 and a line 3, but they intersect. We count the points for the line of four, which
     gives us 13 points, and now we remove those tiles to give us this:

            x

            x
      Now we are left with two lines of one elk, which yields 2 points each.
      If the line of 4 and the line of 3 did not intersect, we would yield 13 + 9 = 22.
      But now we only get 13 + 2 + 2 = 17.

      Obviously we need some sort of penalty if placing an elk token or habitat tiles intersects an
      existing line, so we include the doesIntersectLine() function, and if returned true, we take
      away 0.5 from the weight associated with the potential length of the line.

 */

public class ElkWeightValueMap extends AbstractWeightValueMap {

    HashMap<Integer, Double> elkWeightTable = new HashMap<>();

    public ElkWeightValueMap() {
        super();

        elkWeightTable.put(0, 0.0);
        elkWeightTable.put(1, 0.9);
        elkWeightTable.put(2, 2.65);
        elkWeightTable.put(3, 4.5);
        elkWeightTable.put(4, 5.5);
    }


    @Override
    double getWeightValue(int lengthOfPotentialLine) {

        //error handling
        if (!elkWeightTable.containsKey(lengthOfPotentialLine)) {
            throw new IllegalArgumentException(String.format("Key \"%s\" does not exist", lengthOfPotentialLine));
        }

        double elkWeight = elkWeightTable.get(lengthOfPotentialLine);

        if (doesIntersectLine()) {
            elkWeight -= 0.5;
        }

        return elkWeight;
    }

    // checks to see if placing an elk on that tile will intersect an existing line of elk
    boolean doesIntersectLine() {

        return true; //temporary
    }
}
