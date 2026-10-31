package NumberOfSubmatricesThatSumToTarget;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfSubmatricesThatSumToTargetTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, NumberOfSubmatricesThatSumToTarget.numSubmatrixSumTarget(new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}, 0));
        assertEquals(5, NumberOfSubmatricesThatSumToTarget.numSubmatrixSumTarget(new int[][]{{1, -1}, {-1, 1}}, 0));
        assertEquals(0, NumberOfSubmatricesThatSumToTarget.numSubmatrixSumTarget(new int[][]{{904}}, 0));
        Random r = new Random(1074);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(6), n = 1 + r.nextInt(6);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(5) - 2;
            int target = r.nextInt(5) - 2;
            assertEquals(NumberOfSubmatricesThatSumToTarget.numSubmatrixSumTargetBrute(g, target), NumberOfSubmatricesThatSumToTarget.numSubmatrixSumTarget(g, target));
        }
    }
}
