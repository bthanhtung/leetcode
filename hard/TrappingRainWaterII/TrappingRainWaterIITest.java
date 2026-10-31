package TrappingRainWaterII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrappingRainWaterIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, TrappingRainWaterII.trapRainWater(new int[][]{{1, 4, 3, 1, 3, 2}, {3, 2, 1, 3, 2, 4}, {2, 3, 3, 2, 3, 1}}));
        assertEquals(10, TrappingRainWaterII.trapRainWater(new int[][]{{3, 3, 3, 3, 3}, {3, 2, 2, 2, 3}, {3, 2, 1, 2, 3}, {3, 2, 2, 2, 3}, {3, 3, 3, 3, 3}}));
        Random r = new Random(407);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(7), n = 1 + r.nextInt(7);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(8);
            assertEquals(TrappingRainWaterII.trapRainWaterRelax(g), TrappingRainWaterII.trapRainWater(g));
        }
    }
}
