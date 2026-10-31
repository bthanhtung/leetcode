package LongestIncreasingSubsequence;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestIncreasingSubsequenceTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, LongestIncreasingSubsequence.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        assertEquals(4, LongestIncreasingSubsequence.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
        assertEquals(1, LongestIncreasingSubsequence.lengthOfLIS(new int[]{7, 7, 7, 7}));
        Random r = new Random(300);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(20), -10, 10).toArray();
            int expected = LongestIncreasingSubsequence.lengthOfLISDp(nums);
            assertEquals(expected, LongestIncreasingSubsequence.lengthOfLIS(nums));
            assertEquals(expected, LongestIncreasingSubsequence.lengthOfLISMemo(nums));
        }
    }
}
