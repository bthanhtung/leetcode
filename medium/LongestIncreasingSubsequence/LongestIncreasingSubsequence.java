package LongestIncreasingSubsequence;

import java.util.Arrays;

/*
 * 300. Longest Increasing Subsequence — https://leetcode.com/problems/longest-increasing-subsequence/
 * Return the length of the longest strictly increasing subsequence. Follow up: O(n log n).
 * Input: [10,9,2,5,3,7,101,18] -> 4 ([2,3,7,101]) · [0,1,0,3,2,3] -> 4 · [7,7,7,7] -> 1
 * Explanation: README.md
 */
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));     // 4
        System.out.println(lengthOfLISDp(new int[]{0, 1, 0, 3, 2, 3}));            // 4
    }

    /* Approach 1 - dp[i] = longest increasing subsequence ending at i = 1 + max dp[j] over j < i with nums[j] < nums[i]. */
    public static int lengthOfLISDp(int[] nums) {
        int[] dp = new int[nums.length];
        int best = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    /*
     * Approach 2 - tails[len] = the smallest possible tail of an increasing subsequence of length len + 1. tails stays
     * sorted, so each number binary-searches the first tail >= it and replaces it (or extends). Time O(n log n).
     */
    public static int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int lo = 0, hi = size;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (tails[mid] < x) lo = mid + 1;
                else hi = mid;
            }
            tails[lo] = x;
            if (lo == size) size++;
        }
        return size;
    }

    /* Approach 3 - recursion choose/skip with memo on (index, previous index). Time O(n^2), space O(n^2). */
    public static int lengthOfLISMemo(int[] nums) {
        int[][] memo = new int[nums.length][nums.length + 1];
        for (int[] row : memo) Arrays.fill(row, -1);
        return lis(nums, 0, -1, memo);
    }

    private static int lis(int[] nums, int i, int prev, int[][] memo) {
        if (i == nums.length) return 0;
        if (memo[i][prev + 1] >= 0) return memo[i][prev + 1];
        int best = lis(nums, i + 1, prev, memo);                       // skip nums[i]
        if (prev < 0 || nums[i] > nums[prev]) best = Math.max(best, 1 + lis(nums, i + 1, i, memo));
        return memo[i][prev + 1] = best;
    }
}
