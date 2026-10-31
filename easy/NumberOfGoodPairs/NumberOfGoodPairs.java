package NumberOfGoodPairs;

import java.util.HashMap;
import java.util.Map;

/*
 * 1512. Number of Good Pairs — https://leetcode.com/problems/number-of-good-pairs/
 * A pair (i, j) is good if nums[i] == nums[j] and i < j. Return the number of good pairs.
 * Input: [1,2,3,1,1,3] -> 4 · [1,1,1,1] -> 6 · [1,2,3] -> 0
 * Explanation: README.md
 */
public class NumberOfGoodPairs {
    public static void main(String[] args) {
        System.out.println(numIdenticalPairs(new int[]{1, 2, 3, 1, 1, 3}));        // 4
        System.out.println(numIdenticalPairsFormula(new int[]{1, 1, 1, 1}));       // 6
    }

    /* Approach 1 - every pair. Time O(n^2). */
    public static int numIdenticalPairsBruteForce(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++)
            for (int j = i + 1; j < nums.length; j++)
                if (nums[i] == nums[j]) count++;
        return count;
    }

    /* Approach 2 - each new x forms a pair with every earlier x: add seen[x], then increment. Time O(n), space O(k). */
    public static int numIdenticalPairs(int[] nums) {
        int[] seen = new int[101];          // 1 <= nums[i] <= 100
        int count = 0;
        for (int x : nums) {
            count += seen[x]++;
        }
        return count;
    }

    /* Approach 3 - a value seen c times gives C(c, 2) = c * (c - 1) / 2 pairs. Time O(n). */
    public static int numIdenticalPairsFormula(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.merge(x, 1, Integer::sum);
        }
        int count = 0;
        for (int c : freq.values()) {
            count += c * (c - 1) / 2;
        }
        return count;
    }
}
