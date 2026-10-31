package RangeSumQueryImmutable;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeSumQueryImmutableTest {

    @Test
    void examplesAndRandom() {
        RangeSumQueryImmutable.NumArray a = new RangeSumQueryImmutable.NumArray(new int[]{-2, 0, 3, -5, 2, -1});
        assertEquals(1, a.sumRange(0, 2));
        assertEquals(-1, a.sumRange(2, 5));
        assertEquals(-3, a.sumRange(0, 5));
        Random r = new Random(303);
        int[] nums = r.ints(50, -100, 100).toArray();
        RangeSumQueryImmutable.NumArray fast = new RangeSumQueryImmutable.NumArray(nums);
        RangeSumQueryImmutable.NumArrayLoop slow = new RangeSumQueryImmutable.NumArrayLoop(nums);
        for (int t = 0; t < 2000; t++) {
            int left = r.nextInt(50), right = left + r.nextInt(50 - left);
            assertEquals(slow.sumRange(left, right), fast.sumRange(left, right));
        }
    }
}
