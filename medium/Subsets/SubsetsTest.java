package Subsets;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubsetsTest {

    private static Set<Set<Integer>> asSets(List<List<Integer>> lists) {
        return lists.stream().map(HashSet::new).collect(Collectors.toSet());
    }

    @Test
    void allApproachesGiveThePowerSet() {
        for (int n = 0; n <= 10; n++) {
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = i * 7 - 20;
            List<List<Integer>> a = Subsets.subsets(nums), b = Subsets.subsetsIterative(nums), c = Subsets.subsetsBitmask(nums);
            assertEquals(1 << n, a.size());
            assertEquals(1 << n, asSets(a).size(), "no duplicates");
            assertEquals(asSets(a), asSets(b));
            assertEquals(asSets(a), asSets(c));
        }
        assertEquals(Set.of(Set.of(), Set.of(1), Set.of(2), Set.of(3), Set.of(1, 2), Set.of(1, 3), Set.of(2, 3), Set.of(1, 2, 3)),
                asSets(Subsets.subsets(new int[]{1, 2, 3})));
    }
}
