package KokoEatingBananas;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KokoEatingBananasTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(4, KokoEatingBananas.minEatingSpeed(new int[]{3, 6, 7, 11}, 8));
        assertEquals(30, KokoEatingBananas.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5));
        assertEquals(23, KokoEatingBananas.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 6));
        assertEquals(3, KokoEatingBananas.minEatingSpeed(new int[]{805306368, 805306368, 805306368}, 1000000000));
        assertEquals(1000000000, KokoEatingBananas.minEatingSpeed(new int[]{1000000000}, 1));
        Random r = new Random(875);
        for (int t = 0; t < 2000; t++) {
            int[] piles = r.ints(1 + r.nextInt(6), 1, 40).toArray();
            int h = piles.length + r.nextInt(30);
            int k = 1;
            while (true) {
                long hrs = 0;
                for (int p : piles) hrs += (p + k - 1) / k;
                if (hrs <= h) break;
                k++;
            }
            assertEquals(k, KokoEatingBananas.minEatingSpeed(piles, h));
        }
    }
}
