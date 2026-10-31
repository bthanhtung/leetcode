package SubsetsII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubsetsIITest {

    private static Set<List<Integer>> bruteForce(int[] nums) {
        Set<List<Integer>> out = new HashSet<>();
        for (int mask = 0; mask < 1 << nums.length; mask++) {
            List<Integer> s = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) if ((mask >> i & 1) == 1) s.add(nums[i]);
            Collections.sort(s);
            out.add(s);
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(List.of(), List.of(1), List.of(1, 2), List.of(1, 2, 2), List.of(2), List.of(2, 2)), SubsetsII.subsetsWithDup(new int[]{1, 2, 2}));
        Random r = new Random(90);
        for (int t = 0; t < 500; t++) {
            int[] nums = r.ints(r.nextInt(8), 0, 3).toArray();
            Set<List<Integer>> expected = bruteForce(nums);
            List<List<Integer>> a = SubsetsII.subsetsWithDup(nums), b = SubsetsII.subsetsWithDupIterative(nums);
            assertEquals(expected.size(), a.size());
            assertEquals(expected, new HashSet<>(a));
            assertEquals(expected.size(), b.size());
            assertEquals(expected, new HashSet<>(b));
        }
    }
}
