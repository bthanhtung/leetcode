package MonotonicArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonotonicArrayTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, MonotonicArray.isMonotonic(new int[]{1, 2, 2, 3}));
        assertEquals(true, MonotonicArray.isMonotonic(new int[]{6, 5, 4, 4}));
        assertEquals(false, MonotonicArray.isMonotonic(new int[]{1, 3, 2}));
        Random r = new Random(896);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(6), 0, 3).toArray();
            assertEquals(MonotonicArray.isMonotonic(nums), MonotonicArray.isMonotonicDirection(nums));
        }
    }
}
