package SuperEggDrop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuperEggDropTest {

    @Test
    void examplesAndCrossCheck() {
        assertEquals(2, SuperEggDrop.superEggDrop(1, 2));
        assertEquals(3, SuperEggDrop.superEggDrop(2, 6));
        assertEquals(4, SuperEggDrop.superEggDrop(3, 14));
        assertEquals(14, SuperEggDrop.superEggDrop(2, 100));
        assertEquals(10000, SuperEggDrop.superEggDrop(1, 10000));
        int[][] naive = new int[5][61];
        for (int f = 1; f <= 60; f++) naive[1][f] = f;
        for (int e = 2; e <= 4; e++)
            for (int f = 1; f <= 60; f++) {
                int best = Integer.MAX_VALUE;
                for (int x = 1; x <= f; x++) best = Math.min(best, 1 + Math.max(naive[e - 1][x - 1], naive[e][f - x]));
                naive[e][f] = best;
            }
        for (int e = 1; e <= 4; e++)
            for (int f = 1; f <= 60; f++) {
                assertEquals(naive[e][f], SuperEggDrop.superEggDrop(e, f), e + " eggs, " + f + " floors");
                assertEquals(naive[e][f], SuperEggDrop.superEggDropClassic(e, f));
            }
        assertEquals(SuperEggDrop.superEggDropClassic(4, 2000), SuperEggDrop.superEggDrop(4, 2000));
    }
}
