package Candy;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CandyTest {

    @Test
    void examplesAndRandom() {
        assertEquals(5, Candy.candy(new int[]{1, 0, 2}));
        assertEquals(4, Candy.candy(new int[]{1, 2, 2}));
        assertEquals(11, Candy.candy(new int[]{1, 3, 4, 5, 2}));      // 1, 2, 3, 4, 1
        Random r = new Random(135);
        for (int t = 0; t < 3000; t++) {
            int[] ratings = r.ints(1 + r.nextInt(15), 0, 5).toArray();
            assertEquals(Candy.candy(ratings), Candy.candySlopes(ratings));
        }
    }
}
