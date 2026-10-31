package LastStoneWeight;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LastStoneWeightTest {

    @Test
    void examplesAndRandom() {
        assertEquals(1, LastStoneWeight.lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1}));
        assertEquals(1, LastStoneWeight.lastStoneWeight(new int[]{1}));
        assertEquals(0, LastStoneWeight.lastStoneWeightCounting(new int[]{3, 3}));
        Random r = new Random(1046);
        for (int t = 0; t < 2000; t++) {
            int[] stones = r.ints(1 + r.nextInt(30), 1, r.nextBoolean() ? 10 : 1001).toArray();
            int expected = LastStoneWeight.lastStoneWeightSort(stones);
            assertEquals(expected, LastStoneWeight.lastStoneWeight(stones));
            assertEquals(expected, LastStoneWeight.lastStoneWeightCounting(stones));
        }
    }
}
