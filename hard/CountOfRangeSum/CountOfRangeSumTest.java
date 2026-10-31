package CountOfRangeSum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountOfRangeSumTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, CountOfRangeSum.countRangeSum(new int[]{-2, 5, -1}, -2, 2));
        assertEquals(1, CountOfRangeSum.countRangeSum(new int[]{0}, 0, 0));
        assertEquals(3, CountOfRangeSum.countRangeSum(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0}, -1, 0));
        Random r = new Random(327);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), -6, 7).toArray();
            int lower = r.nextInt(11) - 6, upper = lower + r.nextInt(8);
            int expected = CountOfRangeSum.countRangeSumBrute(nums, lower, upper);
            assertEquals(expected, CountOfRangeSum.countRangeSum(nums, lower, upper));
            assertEquals(expected, CountOfRangeSum.countRangeSumFenwick(nums, lower, upper));
        }
    }
}
