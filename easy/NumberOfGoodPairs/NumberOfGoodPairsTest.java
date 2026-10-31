package NumberOfGoodPairs;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfGoodPairsTest {

    private static void all(int expected, int... nums) {
        assertEquals(expected, NumberOfGoodPairs.numIdenticalPairsBruteForce(nums));
        assertEquals(expected, NumberOfGoodPairs.numIdenticalPairs(nums));
        assertEquals(expected, NumberOfGoodPairs.numIdenticalPairsFormula(nums));
    }

    @Test
    void examplesAndRandom() {
        all(4, 1, 2, 3, 1, 1, 3);
        all(6, 1, 1, 1, 1);
        all(0, 1, 2, 3);
        Random r = new Random(1512);
        for (int t = 0; t < 500; t++) {
            int[] nums = r.ints(1 + r.nextInt(30), 1, 6).toArray();
            all(NumberOfGoodPairs.numIdenticalPairsBruteForce(nums), nums);
        }
    }
}
