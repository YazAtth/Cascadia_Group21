/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone;

import org.grouptwentyone.models.HexCoordinate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class HexCoordinateTest {


    HexCoordinate mainHexCoordinate = new HexCoordinate(2, 1);


    @Nested
    @DisplayName("Test \"isAdjacent()\" on Adjacent Positions [6]")
    class CheckAdjacentPositions {

        @Test
        void testNorthWest() {
            HexCoordinate hexCoordinate = new HexCoordinate(1,1);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertTrue(isAdjacent);
        }

        @Test
        void testNorthEast() {
            HexCoordinate hexCoordinate = new HexCoordinate(1,2);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertTrue(isAdjacent);
        }

        @Test
        void testEast() {
            HexCoordinate hexCoordinate = new HexCoordinate(2,2);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertTrue(isAdjacent);
        }

        @Test
        void testSouthEast() {
            HexCoordinate hexCoordinate = new HexCoordinate(3,2);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertTrue(isAdjacent);
        }

        @Test
        void testSouthWest() {
            HexCoordinate hexCoordinate = new HexCoordinate(3,1);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertTrue(isAdjacent);
        }

        @Test
        void testWest() {
            HexCoordinate hexCoordinate = new HexCoordinate(2,0);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertTrue(isAdjacent);
        }
    }

    @Nested
    @DisplayName("Test \"isAdjacent()\" on Non-Adjacent Positions [12]")
    class CheckNonAdjacentPositions {

        @Test
        void testTwelveOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(0,1);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testOneOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(0,2);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testTwoOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(1,3);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testFourOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(2,3);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testFiveOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(3,3);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testSixOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(4,2);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testSevenOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(4,1);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testEightOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(4,0);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testNineOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(3,0);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testTenOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(2,-1);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testElevenOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(1,0);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

        @Test
        void testThreeOClock() {
            HexCoordinate hexCoordinate = new HexCoordinate(0,0);
            boolean isAdjacent = mainHexCoordinate.isAdjacentToHexCoordinate(hexCoordinate);
            assertFalse(isAdjacent);
        }

    }

    @Nested
    @DisplayName("Test \"equals()\" [2]")
    class TestEquals {

        HexCoordinate h1 = new HexCoordinate(1, 0);
        HexCoordinate h2 = new HexCoordinate(1, 0);
        HexCoordinate h3 = new HexCoordinate(4,0);

        @Test
        void testEqualsWithEquivalentCoords() {
            boolean isEqual = h1.equals(h2);
            assertTrue(isEqual);
        }

        @Test
        void testEqualsWithDifferentCoords() {
            boolean isEqual = h1.equals(h3);
            assertFalse(isEqual);
        }

    }
}
