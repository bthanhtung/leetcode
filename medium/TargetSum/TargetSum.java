package TargetSum;

import java.util.HashMap;
import java.util.Map;

/*
 * 494. Target Sum — https://leetcode.com/problems/target-sum/
 * Put '+' or '-' before every number in nums; count the expressions that evaluate to target.
 * Input: nums = [1,1,1,1,1], target = 3 -> 5 · nums = [1], target = 1 -> 1
 * Explanation: README.md
 */
public class TargetSum {
    public static void main(String[] args) {
        System.out.println(findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3));      // 5
    }

    /*
     * Approach 1 - P = numbers with '+', N = with '-': P - N = target and P + N = total, so P = (total + target) / 2.
     * Count subsets summing to P (0/1 knapsack, iterate capacity downwards). Time O(n * P), space O(P).
     */
    public static int findTargetSumWays(int[] nums, int target) {
        int total = 0;
        for (int x : nums) total += x;
        if (Math.abs(target) > total || (total + target) % 2 != 0) return 0;
        int p = (total + target) / 2;
        int[] ways = new int[p + 1];
        ways[0] = 1;
        for (int x : nums) {
            for (int s = p; s >= x; s--) ways[s] += ways[s - x];
        }
        return ways[p];
    }

    /* Approach 2 - DFS over (index, running sum) with memo. Time O(n * total). */
    public static int findTargetSumWaysMemo(int[] nums, int target) {
        return dfs(nums, 0, target, new HashMap<>());
    }

    private static int dfs(int[] nums, int i, int remaining, Map<Long, Integer> memo) {
        if (i == nums.length) return remaining == 0 ? 1 : 0;
        long key = ((long) i << 32) | (remaining & 0xffffffffL);
        Integer cached = memo.get(key);
        if (cached != null) return cached;
        int ways = dfs(nums, i + 1, remaining - nums[i], memo) + dfs(nums, i + 1, remaining + nums[i], memo);
        memo.put(key, ways);
        return ways;
    }
}
