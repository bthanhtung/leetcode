package HouseRobber;

/*
 * 198. House Robber — https://leetcode.com/problems/house-robber/
 * Houses on a street hold nums[i] money; robbing two adjacent houses alerts the police. Return the maximum amount.
 * Input: [1,2,3,1] -> 4 (1 + 3) · [2,7,9,3,1] -> 12 (2 + 9 + 1)
 * Explanation: README.md
 */
public class HouseRobber {
    public static void main(String[] args) {
        System.out.println(rob(new int[]{1, 2, 3, 1}));           // 4
        System.out.println(robTable(new int[]{2, 7, 9, 3, 1}));   // 12
    }

    /* Approach 1 - best(i) = max(rob i + best(i - 2), skip i: best(i - 1)), memoized. Time O(n). */
    public static int robMemo(int[] nums) {
        return best(nums, nums.length - 1, new Integer[nums.length]);
    }

    private static int best(int[] nums, int i, Integer[] memo) {
        if (i < 0) return 0;
        if (memo[i] == null) memo[i] = Math.max(nums[i] + best(nums, i - 2, memo), best(nums, i - 1, memo));
        return memo[i];
    }

    /* Approach 2 - bottom-up array; dp[i] = best using the first i houses. Time O(n), space O(n). */
    public static int robTable(int[] nums) {
        int[] dp = new int[nums.length + 1];
        dp[1] = nums[0];
        for (int i = 2; i <= nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return dp[nums.length];
    }

    /* Approach 3 - only the last two values: prev2 = best two houses back, prev1 = best one house back. Space O(1). */
    public static int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int x : nums) {
            int cur = Math.max(prev1, prev2 + x);
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }
}
