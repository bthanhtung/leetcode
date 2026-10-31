package NumberOfDigitOne;

/*
 * 233. Number of Digit One — https://leetcode.com/problems/number-of-digit-one/
 * Count the total number of digit 1 appearing in all integers from 0 to n.
 * Input: 13 -> 6 (1, 10, 11, 12, 13 contain six 1s) · 0 -> 0
 * Explanation: README.md
 */
public class NumberOfDigitOne {
    public static void main(String[] args) {
        System.out.println(countDigitOne(13));            // 6
        System.out.println(countDigitOneDp(1_000_000));   // 600001
    }

    /*
     * Approach 1 - for each position with weight m (1, 10, 100, ...): split n into high = n / (10m), cur = n / m % 10,
     * low = n % m. The position shows a 1 in high * m full cycles, plus m more if cur > 1, or low + 1 more if cur == 1.
     * Time O(log n).
     */
    public static int countDigitOne(int n) {
        long count = 0;
        for (long m = 1; m <= n; m *= 10) {
            long high = n / (m * 10), cur = n / m % 10, low = n % m;
            count += high * m;
            if (cur > 1) count += m;
            else if (cur == 1) count += low + 1;
        }
        return (int) count;
    }

    /*
     * Approach 2 - digit DP over the decimal digits of n: state (position, ones so far, still tight to n's prefix).
     * Memoize on (position, ones) for non-tight states. Time O(d^2 * 10).
     */
    public static int countDigitOneDp(int n) {
        char[] digits = Integer.toString(n).toCharArray();
        Long[][] memo = new Long[digits.length][digits.length + 1];
        return (int) dfs(digits, 0, 0, true, memo);
    }

    private static long dfs(char[] d, int pos, int ones, boolean tight, Long[][] memo) {
        if (pos == d.length) return ones;
        if (!tight && memo[pos][ones] != null) return memo[pos][ones];
        int limit = tight ? d[pos] - '0' : 9;
        long total = 0;
        for (int digit = 0; digit <= limit; digit++) {
            total += dfs(d, pos + 1, ones + (digit == 1 ? 1 : 0), tight && digit == limit, memo);
        }
        if (!tight) memo[pos][ones] = total;
        return total;
    }
}
