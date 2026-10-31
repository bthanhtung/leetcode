package LargestComponentSizeByCommonFactor;

import java.util.HashMap;
import java.util.Map;

/*
 * 952. Largest Component Size by Common Factor — https://leetcode.com/problems/largest-component-size-by-common-factor/
 * Nodes are the distinct numbers in nums; two nodes are connected if they share a common factor > 1.
 * Return the size of the largest connected component.
 * Input: [4,6,15,35] -> 4 · [20,50,9,63] -> 2 · [2,3,6,7,4,12,21,39] -> 8
 * Explanation: README.md
 */
public class LargestComponentSizeByCommonFactor {
    public static void main(String[] args) {
        System.out.println(largestComponentSize(new int[]{4, 6, 15, 35}));             // 4
        System.out.println(largestComponentSizeFactors(new int[]{2, 3, 6, 7, 4, 12, 21, 39}));   // 8
    }

    private static int find(int[] p, int x) {
        while (p[x] != x) {
            p[x] = p[p[x]];
            x = p[x];
        }
        return x;
    }

    private static void union(int[] p, int a, int b) {
        p[find(p, a)] = find(p, b);
    }

    /*
     * Approach 1 - union each number with its prime factors (nodes are values 0..max). Numbers sharing a prime end up
     * in the same set. Factoring by trial division: O(n sqrt(max)).
     */
    public static int largestComponentSize(int[] nums) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);
        int[] parent = new int[max + 1];
        for (int i = 0; i <= max; i++) parent[i] = i;
        for (int x : nums) {
            int v = x;
            for (int f = 2; f * f <= v; f++) {
                if (v % f != 0) continue;
                union(parent, x, f);
                while (v % f == 0) v /= f;
            }
            if (v > 1) union(parent, x, v);                       // the remaining prime factor
        }
        Map<Integer, Integer> size = new HashMap<>();
        int best = 0;
        for (int x : nums) best = Math.max(best, size.merge(find(parent, x), 1, Integer::sum));
        return best;
    }

    /* Approach 2 - union each number with every divisor pair (f, x / f), not only primes. Same idea, slightly simpler. */
    public static int largestComponentSizeFactors(int[] nums) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);
        int[] parent = new int[max + 1];
        for (int i = 0; i <= max; i++) parent[i] = i;
        for (int x : nums) {
            for (int f = 2; f * f <= x; f++) {
                if (x % f == 0) {
                    union(parent, x, f);
                    union(parent, x, x / f);
                }
            }
        }
        Map<Integer, Integer> size = new HashMap<>();
        int best = 0;
        for (int x : nums) best = Math.max(best, size.merge(find(parent, x), 1, Integer::sum));
        return best;
    }
}
