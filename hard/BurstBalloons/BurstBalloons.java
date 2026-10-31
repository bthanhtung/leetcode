package BurstBalloons;

/*
 * 312. Burst Balloons — https://leetcode.com/problems/burst-balloons/
 * Bursting balloon i earns nums[i-1] * nums[i] * nums[i+1] (out-of-range neighbours count as 1); then its
 * neighbours become adjacent. Return the maximum coins from bursting all balloons.
 * Input: [3,1,5,8] -> 167 (3*1*5 + 3*5*8 + 1*3*8 + 1*8*1) · [1,5] -> 10
 * Explanation: README.md
 */
public class BurstBalloons {
    public static void main(String[] args) {
        System.out.println(maxCoins(new int[]{3, 1, 5, 8}));        // 167
        System.out.println(maxCoinsMemo(new int[]{1, 5}));          // 10
    }

    private static int[] padded(int[] nums) {
        int[] a = new int[nums.length + 2];
        a[0] = a[a.length - 1] = 1;
        System.arraycopy(nums, 0, a, 1, nums.length);
        return a;
    }

    /*
     * Approach 1 - think about the LAST balloon k burst inside the open interval (l, r): at that moment its neighbours
     * are exactly a[l] and a[r], and the two sides were solved independently.
     * dp[l][r] = max over l < k < r of dp[l][k] + a[l] * a[k] * a[r] + dp[k][r]. Time O(n^3).
     */
    public static int maxCoins(int[] nums) {
        int[] a = padded(nums);
        int n = a.length;
        int[][] dp = new int[n][n];
        for (int len = 2; len < n; len++) {
            for (int l = 0; l + len < n; l++) {
                int r = l + len;
                for (int k = l + 1; k < r; k++) {
                    dp[l][r] = Math.max(dp[l][r], dp[l][k] + a[l] * a[k] * a[r] + dp[k][r]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /* Approach 2 - the same recurrence top-down with memoization. */
    public static int maxCoinsMemo(int[] nums) {
        int[] a = padded(nums);
        return best(a, 0, a.length - 1, new Integer[a.length][a.length]);
    }

    private static int best(int[] a, int l, int r, Integer[][] memo) {
        if (l + 1 >= r) return 0;
        if (memo[l][r] == null) {
            int result = 0;
            for (int k = l + 1; k < r; k++) {
                result = Math.max(result, best(a, l, k, memo) + a[l] * a[k] * a[r] + best(a, k, r, memo));
            }
            memo[l][r] = result;
        }
        return memo[l][r];
    }
}
