package SuperEggDrop;

/*
 * 887. Super Egg Drop — https://leetcode.com/problems/super-egg-drop/
 * k identical eggs, a building with n floors. There is a floor f (0 <= f <= n): eggs dropped above f break,
 * at or below do not. Find the minimum number of moves that determines f with certainty in the worst case.
 * Input: k = 1, n = 2 -> 2 · k = 2, n = 6 -> 3 · k = 3, n = 14 -> 4
 * Explanation: README.md
 */
public class SuperEggDrop {
    public static void main(String[] args) {
        System.out.println(superEggDrop(2, 100));     // 14
    }

    /*
     * Approach 1 - flip the question: f(m, k) = the most floors we can fully resolve with m moves and k eggs.
     * Drop once: if it breaks we can resolve f(m-1, k-1) floors below; if not, f(m-1, k) floors above. So
     * f(m, k) = f(m-1, k-1) + f(m-1, k) + 1. Answer: the smallest m with f(m, k) >= n. Time O(k log n).
     */
    public static int superEggDrop(int k, int n) {
        long[] f = new long[k + 1];               // f[e] for the current number of moves
        int moves = 0;
        while (f[k] < n) {
            moves++;
            for (int e = k; e >= 1; e--) f[e] = f[e - 1] + f[e] + 1;
        }
        return moves;
    }

    /*
     * Approach 2 - classic DP dp[e][floors] = 1 + min over x of max(dp[e-1][x-1], dp[e][floors-x]). The first term
     * grows with x, the second shrinks, so the optimal x is found by binary search. Time O(k n log n).
     */
    public static int superEggDropClassic(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        for (int f = 1; f <= n; f++) dp[1][f] = f;
        for (int e = 2; e <= k; e++) {
            for (int f = 1; f <= n; f++) {
                int lo = 1, hi = f;
                while (lo + 1 < hi) {
                    int x = (lo + hi) >>> 1;
                    if (dp[e - 1][x - 1] < dp[e][f - x]) lo = x;
                    else hi = x;
                }
                dp[e][f] = 1 + Math.min(Math.max(dp[e - 1][lo - 1], dp[e][f - lo]), Math.max(dp[e - 1][hi - 1], dp[e][f - hi]));
            }
        }
        return dp[k][n];
    }
}
