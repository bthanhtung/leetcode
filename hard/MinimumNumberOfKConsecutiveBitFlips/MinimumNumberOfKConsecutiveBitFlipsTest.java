package MinimumNumberOfKConsecutiveBitFlips;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumNumberOfKConsecutiveBitFlipsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, MinimumNumberOfKConsecutiveBitFlips.minKBitFlips(new int[]{0, 1, 0}, 1));
        assertEquals(-1, MinimumNumberOfKConsecutiveBitFlips.minKBitFlips(new int[]{1, 1, 0}, 2));
        assertEquals(3, MinimumNumberOfKConsecutiveBitFlips.minKBitFlips(new int[]{0, 0, 0, 1, 0, 1, 1, 0}, 3));
        Random r = new Random(995);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), 0, 2).toArray();
            int k = 1 + r.nextInt(nums.length);
            int expected = MinimumNumberOfKConsecutiveBitFlips.minKBitFlipsSimulate(nums, k);
            assertEquals(expected, MinimumNumberOfKConsecutiveBitFlips.minKBitFlips(nums, k));
            assertEquals(expected, MinimumNumberOfKConsecutiveBitFlips.minKBitFlipsInPlace(nums, k));
        }
    }
}
