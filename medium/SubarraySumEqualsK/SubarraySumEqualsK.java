package SubarraySumEqualsK;

import java.util.HashMap;
import java.util.Map;

/*
 * 560. Subarray Sum Equals K — https://leetcode.com/problems/subarray-sum-equals-k/
 * Count the contiguous non-empty subarrays whose sum equals k (values may be negative).
 * Input: [1,1,1], k = 2 -> 2 · [1,2,3], k = 3 -> 2
 * Explanation: README.md
 */
public class SubarraySumEqualsK {
    public static void main(String[] args) {
        System.out.println(subarraySum(new int[]{1, 2, 3}, 3));     // 2
    }

    /*
     * Approach 1 - sum(i..j) = prefix[j+1] - prefix[i]. For each prefix p, count earlier prefixes equal to p - k.
     * Time O(n), space O(n).
     */
    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> seen = new HashMap<>();
        seen.put(0, 1);                                  // the empty prefix
        int prefix = 0, count = 0;
        for (int x : nums) {
            prefix += x;
            count += seen.getOrDefault(prefix - k, 0);
            seen.merge(prefix, 1, Integer::sum);
        }
        return count;
    }

    /* Approach 2 - every start, extend the end. O(n^2). */
    public static int subarraySumBrute(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) if ((sum += nums[j]) == k) count++;
        }
        return count;
    }
}
