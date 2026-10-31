package ConsecutiveNumbersSum;

/*
 * 829. Consecutive Numbers Sum — https://leetcode.com/problems/consecutive-numbers-sum/
 * Return the number of ways to write n as a sum of one or more consecutive positive integers.
 * Input: 5 -> 2 (5, 2+3) · 9 -> 3 (9, 4+5, 2+3+4) · 15 -> 4 (15, 7+8, 4+5+6, 1+2+3+4+5)
 * Explanation: README.md
 */
public class ConsecutiveNumbersSum {
    public static void main(String[] args) {
        System.out.println(consecutiveNumbersSum(15));           // 4
        System.out.println(consecutiveNumbersSumDivisors(9));    // 3
    }

    /*
     * Approach 1 - k terms starting at x: n = k*x + k(k-1)/2, so x = (n - k(k-1)/2) / k must be a positive integer.
     * k only goes up to about sqrt(2n). Time O(sqrt n).
     */
    public static int consecutiveNumbersSum(int n) {
        int ways = 0;
        for (long k = 1; k * (k - 1) / 2 < n; k++) {
            if ((n - k * (k - 1) / 2) % k == 0) ways++;
        }
        return ways;
    }

    /* Approach 2 - the answer equals the number of odd divisors of n. Strip factors of 2, count divisors of the rest. */
    public static int consecutiveNumbersSumDivisors(int n) {
        while (n % 2 == 0) n /= 2;
        int ways = 1;
        for (int p = 3; (long) p * p <= n; p += 2) {
            int e = 0;
            while (n % p == 0) {
                n /= p;
                e++;
            }
            ways *= e + 1;
        }
        if (n > 1) ways *= 2;
        return ways;
    }
}
