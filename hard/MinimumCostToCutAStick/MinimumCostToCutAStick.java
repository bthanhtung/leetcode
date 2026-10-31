package MinimumCostToCutAStick;

import java.util.Arrays;

/*
 * 1547. Minimum Cost to Cut a Stick — https://leetcode.com/problems/minimum-cost-to-cut-a-stick/
 * A stick of length n must be cut at the given positions, in any order. Each cut costs the length of the piece being
 * cut. Return the minimum total cost.
 * Input: n = 7, cuts = [1,3,4,5] -> 16 · n = 9, cuts = [5,6,1,4,2] -> 22
 * Explanation: README.md
 */
public class MinimumCostToCutAStick {
    public static void main(String[] args) {
        System.out.println(minCost(7, new int[]{1, 3, 4, 5}));            // 16
        System.out.println(minCostMemo(9, new int[]{5, 6, 1, 4, 2}));     // 22
    }

    private static int[] points(int n, int[] cuts) {
        int[] p = new int[cuts.length + 2];
        System.arraycopy(cuts, 0, p, 1, cuts.length);
        p[p.length - 1] = n;
        Arrays.sort(p);                                       // 0, sorted cuts, n
        return p;
    }

    /*
     * Approach 1 - dp[i][j] = cheapest way to make every cut strictly between points i and j. The FIRST cut k in that
     * piece costs p[j] - p[i] and splits it into two independent pieces. Time O(m^3) for m cuts.
     */
    public static int minCost(int n, int[] cuts) {
        int[] p = points(n, cuts);
        int m = p.length;
        int[][] dp = new int[m][m];
        for (int len = 2; len < m; len++) {
            for (int i = 0; i + len < m; i++) {
                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
                dp[i][j] += p[j] - p[i];
            }
        }
        return dp[0][m - 1];
    }

    /* Approach 2 - the same recurrence top-down. */
    public static int minCostMemo(int n, int[] cuts) {
        int[] p = points(n, cuts);
        return cost(p, 0, p.length - 1, new Integer[p.length][p.length]);
    }

    private static int cost(int[] p, int i, int j, Integer[][] memo) {
        if (j - i < 2) return 0;
        if (memo[i][j] == null) {
            int best = Integer.MAX_VALUE;
            for (int k = i + 1; k < j; k++) best = Math.min(best, cost(p, i, k, memo) + cost(p, k, j, memo));
            memo[i][j] = best + p[j] - p[i];
        }
        return memo[i][j];
    }
}
