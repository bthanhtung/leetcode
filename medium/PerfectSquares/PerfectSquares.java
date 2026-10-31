package PerfectSquares;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/*
 * 279. Perfect Squares — https://leetcode.com/problems/perfect-squares/
 * Return the least number of perfect square numbers (1, 4, 9, 16, ...) that sum to n.
 * Input: 12 -> 3 (4 + 4 + 4) · 13 -> 2 (4 + 9)
 * Explanation: README.md
 */
public class PerfectSquares {
    public static void main(String[] args) {
        System.out.println(numSquares(12));          // 3
        System.out.println(numSquaresMath(13));      // 2
    }

    /* Approach 1 - dp[i] = 1 + min dp[i - s*s] over squares s*s <= i (like coin change). Time O(n * sqrt n). */
    public static int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int s = 1; s * s <= i; s++) {
                dp[i] = Math.min(dp[i], dp[i - s * s] + 1);
            }
        }
        return dp[n];
    }

    /* Approach 2 - BFS from n, each edge subtracts a square; the first time we reach 0 is the fewest squares. */
    public static int numSquaresBfs(int n) {
        boolean[] seen = new boolean[n + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(n);
        seen[n] = true;
        for (int level = 1; ; level++) {
            for (int size = queue.size(); size > 0; size--) {
                int rest = queue.poll();
                for (int s = 1; s * s <= rest; s++) {
                    int next = rest - s * s;
                    if (next == 0) return level;
                    if (!seen[next]) {
                        seen[next] = true;
                        queue.add(next);
                    }
                }
            }
        }
    }

    /*
     * Approach 3 - math: every number is a sum of at most 4 squares (Lagrange); it needs exactly 4 iff it has the form
     * 4^a (8b + 7) (Legendre). Otherwise check 1 and 2 squares directly; else 3. Time O(sqrt n).
     */
    public static int numSquaresMath(int n) {
        if (isSquare(n)) return 1;
        int m = n;
        while (m % 4 == 0) m /= 4;
        if (m % 8 == 7) return 4;
        for (int s = 1; s * s <= n; s++) {
            if (isSquare(n - s * s)) return 2;
        }
        return 3;
    }

    private static boolean isSquare(int x) {
        int r = (int) Math.sqrt(x);
        return r * r == x;
    }
}
