package NonOverlappingIntervals;

import java.util.Arrays;

/*
 * 435. Non-overlapping Intervals — https://leetcode.com/problems/non-overlapping-intervals/
 * Return the minimum number of intervals to remove so the rest do not overlap. Touching ends ([1,2],[2,3]) do not overlap.
 * Input: [[1,2],[2,3],[3,4],[1,3]] -> 1 · [[1,2],[1,2],[1,2]] -> 2 · [[1,2],[2,3]] -> 0
 * Explanation: README.md
 */
public class NonOverlappingIntervals {
    public static void main(String[] args) {
        System.out.println(eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}));     // 1
        System.out.println(eraseOverlapIntervalsDp(new int[][]{{1, 2}, {1, 2}, {1, 2}}));         // 2
    }

    /*
     * Approach 1 - keep as many as possible: sort by end, always keep the interval that ends first among those
     * compatible with the last kept one. Answer = n - kept. Time O(n log n).
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        int[][] a = intervals.clone();
        Arrays.sort(a, (x, y) -> Integer.compare(x[1], y[1]));
        int kept = 0;
        long lastEnd = Long.MIN_VALUE;
        for (int[] in : a) {
            if (in[0] >= lastEnd) {
                kept++;
                lastEnd = in[1];
            }
        }
        return a.length - kept;
    }

    /* Approach 2 - sort by start; dp[i] = most non-overlapping intervals ending with interval i. Time O(n^2). */
    public static int eraseOverlapIntervalsDp(int[][] intervals) {
        int n = intervals.length;
        int[][] a = intervals.clone();
        Arrays.sort(a, (x, y) -> Integer.compare(x[0], y[0]));
        int[] dp = new int[n];
        int best = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[j][1] <= a[i][0]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            best = Math.max(best, dp[i]);
        }
        return n - best;
    }
}
