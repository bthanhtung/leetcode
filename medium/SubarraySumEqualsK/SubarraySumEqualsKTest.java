package SubarraySumEqualsK;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubarraySumEqualsKTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, SubarraySumEqualsK.subarraySum(new int[]{1, 1, 1}, 2));
        assertEquals(2, SubarraySumEqualsK.subarraySum(new int[]{1, 2, 3}, 3));
        assertEquals(55, SubarraySumEqualsK.subarraySum(new int[10], 0));
        Random r = new Random(560);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), -3, 4).toArray();
            int k = r.nextInt(7) - 3;
            assertEquals(SubarraySumEqualsK.subarraySumBrute(nums, k), SubarraySumEqualsK.subarraySum(nums, k));
        }
    }
}
