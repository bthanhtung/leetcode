package ContainerWithMostWater;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainerWithMostWaterTest {

    @Test
    void examplesAndRandom() {
        assertEquals(49, ContainerWithMostWater.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        assertEquals(1, ContainerWithMostWater.maxArea(new int[]{1, 1}));
        Random r = new Random(11);
        for (int t = 0; t < 2000; t++) {
            int[] h = r.ints(2 + r.nextInt(20), 0, 30).toArray();
            assertEquals(ContainerWithMostWater.maxAreaBruteForce(h), ContainerWithMostWater.maxArea(h));
        }
    }
}
