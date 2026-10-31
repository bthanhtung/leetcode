package KInversePairsArray;

/*
 * 629. K Inverse Pairs Array — https://leetcode.com/problems/k-inverse-pairs-array/
 * Count the arrays made of 1..n (permutations) that have exactly k inverse pairs (i < j with a[i] > a[j]),
 * modulo 10^9 + 7.
 * Input: n = 3, k = 0 -> 1 · n = 3, k = 1 -> 2
 * Explanation: README.md
 */
public class KInversePairsArray {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        System.out.println(kInversePairs(3, 1));             // 2
        System.out.println(kInversePairsWindow(1000, 1000)); // 663677020
    }

    /*
     * Approach 1 - inserting the number i into a permutation of 1..i-1 creates 0..i-1 new inversions (by where it goes):
     * dp[i][j] = sum of dp[i-1][j - t] for t = 0..min(j, i-1). Compute the sums with prefix sums. Time O(n * k).
     */
    public static int kInversePairs(int n, int k) {
        long[] prev = new long[k + 1];
        prev[0] = 1;
        for (int i = 2; i <= n; i++) {
            long[] prefix = new long[k + 2];
            for (int j = 0; j <= k; j++) prefix[j + 1] = (prefix[j] + prev[j]) % MOD;
            long[] cur = new long[k + 1];
            for (int j = 0; j <= k; j++) {
                int from = Math.max(0, j - (i - 1));
                cur[j] = (prefix[j + 1] - prefix[from] + MOD) % MOD;
            }
            prev = cur;
        }
        return (int) prev[k];
    }

    /* Approach 2 - the same sum as a recurrence: dp[i][j] = dp[i][j-1] + dp[i-1][j] - dp[i-1][j-i]. Time O(n * k). */
    public static int kInversePairsWindow(int n, int k) {
        long[] prev = new long[k + 1];
        prev[0] = 1;
        for (int i = 2; i <= n; i++) {
            long[] cur = new long[k + 1];
            for (int j = 0; j <= k; j++) {
                long v = prev[j] + (j > 0 ? cur[j - 1] : 0) - (j >= i ? prev[j - i] : 0);
                cur[j] = ((v % MOD) + MOD) % MOD;
            }
            prev = cur;
        }
        return (int) prev[k];
    }
}
