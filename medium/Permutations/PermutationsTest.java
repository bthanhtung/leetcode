package Permutations;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PermutationsTest {

    @Test
    void allApproachesGiveAllPermutations() {
        int factorial = 1;
        for (int n = 1; n <= 7; n++) {
            factorial *= n;
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = i * 3 - 5;
            List<List<Integer>> a = Permutations.permute(nums), b = Permutations.permuteSwap(nums), c = Permutations.permuteInsert(nums);
            assertEquals(factorial, a.size());
            Set<List<Integer>> set = new HashSet<>(a);
            assertEquals(factorial, set.size());
            assertEquals(set, new HashSet<>(b));
            assertEquals(set, new HashSet<>(c));
        }
        assertEquals(List.of(List.of(1, 2, 3), List.of(1, 3, 2), List.of(2, 1, 3), List.of(2, 3, 1), List.of(3, 1, 2), List.of(3, 2, 1)),
                Permutations.permute(new int[]{1, 2, 3}));
    }
}
