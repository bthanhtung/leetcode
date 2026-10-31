package ThreeSum;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeSumTest {

    private static void check(int[] nums) {
        Set<List<Integer>> expected = new HashSet<>(ThreeSum.threeSumBruteForce(nums));
        for (List<List<Integer>> got : List.of(ThreeSum.threeSum(nums), ThreeSum.threeSumHashSet(nums))) {
            assertEquals(expected.size(), got.size(), "duplicates in result");
            assertEquals(expected, new HashSet<>(got));
        }
    }

    @Test
    void examples() {
        assertEquals(Set.of(List.of(-1, -1, 2), List.of(-1, 0, 1)), new HashSet<>(ThreeSum.threeSum(new int[]{-1, 0, 1, 2, -1, -4})));
        assertEquals(List.of(), ThreeSum.threeSum(new int[]{0, 1, 1}));
        assertEquals(List.of(List.of(0, 0, 0)), ThreeSum.threeSum(new int[]{0, 0, 0, 0}));
    }

    @Test
    void randomAgainstBruteForce() {
        Random r = new Random(15);
        for (int t = 0; t < 500; t++) {
            check(r.ints(r.nextInt(16), -6, 7).toArray());
        }
    }
}
