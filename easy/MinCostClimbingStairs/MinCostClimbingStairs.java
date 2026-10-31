package MinCostClimbingStairs;

/*
 * 746. Min Cost Climbing Stairs — https://leetcode.com/problems/min-cost-climbing-stairs/
 * cost[i] is the price of step i. After paying it you can climb one or two steps. You may start at step 0 or 1.
 * Return the minimum cost to reach the top (just past the last step).
 * Input: [10,15,20] -> 15 · [1,100,1,1,1,100,1,1,100,1] -> 6
 * Explanation: README.md
 */
public class MinCostClimbingStairs {
    public static void main(String[] args) {
        System.out.println(minCostClimbingStairs(new int[]{10, 15, 20}));                           // 15
        System.out.println(minCostClimbingStairsDp(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));  // 6
    }

    /* Approach 1 - top-down: reach(i) = min(reach(i-1) + cost[i-1], reach(i-2) + cost[i-2]), memoized. Time O(n). */
    public static int minCostClimbingStairsMemo(int[] cost) {
        return reach(cost, cost.length, new Integer[cost.length + 1]);
    }

    private static int reach(int[] cost, int i, Integer[] memo) {
        if (i <= 1) return 0;                      // standing on step 0 or 1 is free
        if (memo[i] == null) {
            memo[i] = Math.min(reach(cost, i - 1, memo) + cost[i - 1], reach(cost, i - 2, memo) + cost[i - 2]);
        }
        return memo[i];
    }

    /* Approach 2 - bottom-up array dp[i] = cheapest way to stand on step i. Time O(n), space O(n). */
    public static int minCostClimbingStairsDp(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[n];
    }

    /* Approach 3 - only the last two dp values are needed. Time O(n), space O(1). */
    public static int minCostClimbingStairs(int[] cost) {
        int twoBack = 0, oneBack = 0;
        for (int i = 2; i <= cost.length; i++) {
            int here = Math.min(oneBack + cost[i - 1], twoBack + cost[i - 2]);
            twoBack = oneBack;
            oneBack = here;
        }
        return oneBack;
    }
}
