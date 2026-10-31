package MinimumNumberOfOperationsToMakeArrayContinuous;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumNumberOfOperationsToMakeArrayContinuousTest {

    /** Try every window start that keeps at least one value (or any start) and count values to change. */
    private static int brute(int[] nums) {
        int n = nums.length, best = n;
        for (int x : nums) {
            Set<Integer> kept = new HashSet<>();
            for (int v : nums) if (v >= x && v <= x + n - 1) kept.add(v);
            best = Math.min(best, n - kept.size());
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(0, MinimumNumberOfOperationsToMakeArrayContinuous.minOperations(new int[]{4, 2, 5, 3}));
        assertEquals(1, MinimumNumberOfOperationsToMakeArrayContinuous.minOperations(new int[]{1, 2, 3, 5, 6}));
        assertEquals(3, MinimumNumberOfOperationsToMakeArrayContinuous.minOperations(new int[]{1, 10, 100, 1000}));
        Random r = new Random(2009);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(10), 1, 15).toArray();
            int expected = brute(nums);
            assertEquals(expected, MinimumNumberOfOperationsToMakeArrayContinuous.minOperations(nums));
            assertEquals(expected, MinimumNumberOfOperationsToMakeArrayContinuous.minOperationsBinarySearch(nums));
        }
    }
}
