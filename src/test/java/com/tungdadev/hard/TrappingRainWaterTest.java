package com.tungdadev.hard;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.tungdadev.hard.TrappingRainWater.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrappingRainWaterTest {

    private static void all(int expected, int[] height) {
        assertEquals(expected, trapBruteForce(height));
        assertEquals(expected, trapPrefixMax(height));
        assertEquals(expected, trapStack(height));
        assertEquals(expected, trapTwoPointers(height));
    }

    @Test
    void examples() {
        all(6, new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        all(9, new int[]{4, 2, 0, 3, 2, 5});
    }

    @Test
    void edgeCases() {
        all(0, new int[]{5});              // one bar
        all(0, new int[]{1, 2, 3, 4});     // only rising: nothing holds water
        all(0, new int[]{4, 3, 2, 1});     // only falling
        all(4, new int[]{2, 0, 0, 2});     // flat basin
        all(1, new int[]{5, 0, 1});        // the lower wall decides
    }

    @Test
    void allApproachesAgreeOnRandomInputs() {
        Random r = new Random(3);
        for (int t = 0; t < 500; t++) {
            int[] height = r.ints(1 + r.nextInt(40), 0, 12).toArray();
            all(trapBruteForce(height), height);
        }
    }
}
