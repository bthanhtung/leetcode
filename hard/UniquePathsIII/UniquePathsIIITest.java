package UniquePathsIII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniquePathsIIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, UniquePathsIII.uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}}));
        assertEquals(4, UniquePathsIII.uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 2}}));
        assertEquals(0, UniquePathsIII.uniquePathsIII(new int[][]{{0, 1}, {2, 0}}));
        Random r = new Random(980);
        for (int t = 0; t < 300; t++) {
            int m = 1 + r.nextInt(4), n = 1 + r.nextInt(4);
            if (m * n < 2) continue;
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(5) == 0 ? -1 : 0;
            int s = r.nextInt(m * n), e;
            do e = r.nextInt(m * n); while (e == s);
            g[s / n][s % n] = 1;
            g[e / n][e % n] = 2;
            assertEquals(UniquePathsIII.uniquePathsIII(g), UniquePathsIII.uniquePathsIIIBitmask(g));
        }
    }
}
