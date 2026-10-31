package FourSum;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FourSumTest {

    @Test
    void examples() {
        assertEquals(Set.of(List.of(-2, -1, 1, 2), List.of(-2, 0, 0, 2), List.of(-1, 0, 0, 1)),
                new HashSet<>(FourSum.fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0)));
        assertEquals(List.of(List.of(2, 2, 2, 2)), FourSum.fourSum(new int[]{2, 2, 2, 2, 2}, 8));
        assertEquals(List.of(), FourSum.fourSum(new int[]{1_000_000_000, 1_000_000_000, 1_000_000_000, 1_000_000_000}, -294967296));
    }

    @Test
    void randomAgainstBruteForce() {
        Random r = new Random(18);
        for (int t = 0; t < 400; t++) {
            int[] nums = r.ints(r.nextInt(12), -5, 6).toArray();
            int target = r.nextInt(11) - 5;
            Set<List<Integer>> expected = new HashSet<>(FourSum.fourSumBruteForce(nums, target));
            List<List<Integer>> a = FourSum.fourSum(nums, target), b = FourSum.fourSumKSum(nums, target);
            assertEquals(expected.size(), a.size());
            assertEquals(expected, new HashSet<>(a));
            assertEquals(expected.size(), b.size());
            assertEquals(expected, new HashSet<>(b));
        }
    }
}
