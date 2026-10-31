package LargestComponentSizeByCommonFactor;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LargestComponentSizeByCommonFactorTest {

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /** Graph on the numbers with an edge when gcd > 1, components by DFS. */
    private static int brute(int[] nums) {
        int n = nums.length, best = 0;
        boolean[] seen = new boolean[n];
        for (int s = 0; s < n; s++) {
            if (seen[s]) continue;
            int size = 0;
            java.util.ArrayDeque<Integer> stack = new java.util.ArrayDeque<>();
            stack.push(s);
            seen[s] = true;
            while (!stack.isEmpty()) {
                int u = stack.pop();
                size++;
                for (int v = 0; v < n; v++) {
                    if (!seen[v] && gcd(nums[u], nums[v]) > 1) {
                        seen[v] = true;
                        stack.push(v);
                    }
                }
            }
            best = Math.max(best, size);
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(4, LargestComponentSizeByCommonFactor.largestComponentSize(new int[]{4, 6, 15, 35}));
        assertEquals(2, LargestComponentSizeByCommonFactor.largestComponentSize(new int[]{20, 50, 9, 63}));
        assertEquals(8, LargestComponentSizeByCommonFactor.largestComponentSize(new int[]{2, 3, 6, 7, 4, 12, 21, 39}));
        Random r = new Random(952);
        for (int t = 0; t < 500; t++) {
            int[] nums = r.ints(1, 200).distinct().limit(1 + r.nextInt(12)).toArray();
            int expected = brute(nums);
            assertEquals(expected, LargestComponentSizeByCommonFactor.largestComponentSize(nums));
            assertEquals(expected, LargestComponentSizeByCommonFactor.largestComponentSizeFactors(nums));
        }
    }
}
