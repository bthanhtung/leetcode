package ArrayPartition;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayPartitionTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, ArrayPartition.arrayPairSum(new int[]{1, 4, 3, 2}));
        assertEquals(9, ArrayPartition.arrayPairSum(new int[]{6, 2, 6, 5, 1, 2}));
        Random r = new Random(561);
        for (int t = 0; t < 500; t++) {
            int[] nums = r.ints(2 * (1 + r.nextInt(10)), -10_000, 10_001).toArray();
            assertEquals(ArrayPartition.arrayPairSum(nums), ArrayPartition.arrayPairSumCounting(nums));
        }
    }
}
