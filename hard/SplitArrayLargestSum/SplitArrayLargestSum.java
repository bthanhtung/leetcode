package SplitArrayLargestSum;

/*
 * 410. Split Array Largest Sum — https://leetcode.com/problems/split-array-largest-sum/
 * Split nums into k non-empty contiguous subarrays so that the largest subarray sum is as small as possible.
 * Return that minimized largest sum.
 * Input: [7,2,5,10,8], k = 2 -> 18 ([7,2,5] and [10,8]) · [1,2,3,4,5], k = 2 -> 9
 * Explanation: README.md
 */
public class SplitArrayLargestSum {
    public static void main(String[] args) {
        System.out.println(splitArray(new int[]{7, 2, 5, 10, 8}, 2));        // 18
        System.out.println(splitArrayDp(new int[]{1, 2, 3, 4, 5}, 2));       // 9
    }

    /*
     * Approach 1 - binary search the answer X between max(nums) and sum(nums). "Can we split into <= k parts with every
     * part <= X?" is monotone in X and checked greedily (close a part only when the next number would overflow).
     * Time O(n log sum).
     */
    public static int splitArray(int[] nums, int k) {
        long lo = 0, hi = 0;
        for (int x : nums) {
            lo = Math.max(lo, x);
            hi += x;
        }
        while (lo < hi) {
            long mid = (lo + hi) >>> 1;
            if (partsNeeded(nums, mid) <= k) hi = mid;
            else lo = mid + 1;
        }
        return (int) lo;
    }

    private static int partsNeeded(int[] nums, long limit) {
        int parts = 1;
        long sum = 0;
        for (int x : nums) {
            if (sum + x > limit) {
                parts++;
                sum = 0;
            }
            sum += x;
        }
        return parts;
    }

    /* Approach 2 - dp[j][i] = best largest sum splitting the first i numbers into j parts. Time O(k * n^2). */
    public static int splitArrayDp(int[] nums, int k) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];
        long[][] dp = new long[k + 1][n + 1];
        for (long[] row : dp) java.util.Arrays.fill(row, Long.MAX_VALUE);
        dp[0][0] = 0;
        for (int j = 1; j <= k; j++) {
            for (int i = j; i <= n; i++) {
                for (int p = j - 1; p < i; p++) {                 // last part is nums[p..i-1]
                    if (dp[j - 1][p] == Long.MAX_VALUE) continue;
                    dp[j][i] = Math.min(dp[j][i], Math.max(dp[j - 1][p], prefix[i] - prefix[p]));
                }
            }
        }
        return (int) dp[k][n];
    }
}
