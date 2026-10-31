package RussianDollEnvelopes;

import java.util.Arrays;

/*
 * 354. Russian Doll Envelopes — https://leetcode.com/problems/russian-doll-envelopes/
 * envelopes[i] = [width, height]. One envelope fits into another if both its width and height are strictly smaller.
 * Return the maximum number of envelopes you can nest.
 * Input: [[5,4],[6,4],[6,7],[2,3]] -> 3 ([2,3] => [5,4] => [6,7]) · [[1,1],[1,1],[1,1]] -> 1
 * Explanation: README.md
 */
public class RussianDollEnvelopes {
    public static void main(String[] args) {
        System.out.println(maxEnvelopes(new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}}));          // 3
        System.out.println(maxEnvelopesDp(new int[][]{{1, 1}, {1, 1}, {1, 1}}));               // 1
    }

    /*
     * Approach 1 - sort by width ascending and, for equal widths, height DESCENDING. Then the answer is the longest
     * strictly increasing subsequence of heights (equal widths can never chain because their heights decrease).
     * LIS with patience sorting: O(n log n).
     */
    public static int maxEnvelopes(int[][] envelopes) {
        int[][] e = envelopes.clone();
        Arrays.sort(e, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        int[] tails = new int[e.length];
        int size = 0;
        for (int[] env : e) {
            int h = env[1], lo = 0, hi = size;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (tails[mid] < h) lo = mid + 1;
                else hi = mid;
            }
            tails[lo] = h;
            if (lo == size) size++;
        }
        return size;
    }

    /* Approach 2 - sort by width, dp[i] = longest chain ending with envelope i. O(n^2). */
    public static int maxEnvelopesDp(int[][] envelopes) {
        int[][] e = envelopes.clone();
        Arrays.sort(e, (a, b) -> a[0] - b[0]);
        int[] dp = new int[e.length];
        int best = 0;
        for (int i = 0; i < e.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (e[j][0] < e[i][0] && e[j][1] < e[i][1]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            best = Math.max(best, dp[i]);
        }
        return best;
    }
}
