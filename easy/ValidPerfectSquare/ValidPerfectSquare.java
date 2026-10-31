package ValidPerfectSquare;

/*
 * 367. Valid Perfect Square — https://leetcode.com/problems/valid-perfect-square/
 * Return true if num is a perfect square, without using sqrt.
 * Input: 16 -> true · 14 -> false
 * Explanation: README.md
 */
public class ValidPerfectSquare {
    public static void main(String[] args) {
        System.out.println(isPerfectSquare(16));           // true
        System.out.println(isPerfectSquareNewton(14));     // false
    }

    /* Approach 1 - binary search m with m * m == num (long to avoid overflow). O(log n). */
    public static boolean isPerfectSquare(int num) {
        long lo = 1, hi = num;
        while (lo <= hi) {
            long mid = (lo + hi) >>> 1, sq = mid * mid;
            if (sq == num) return true;
            if (sq < num) lo = mid + 1;
            else hi = mid - 1;
        }
        return false;
    }

    /* Approach 2 - Newton's iteration for the integer square root, then check. */
    public static boolean isPerfectSquareNewton(int num) {
        long r = num;
        while (r * r > num) r = (r + num / r) / 2;
        return r * r == num;
    }

    /* Approach 3 - 1 + 3 + 5 + ... + (2k - 1) = k^2: subtract odd numbers until we hit 0 or go below. O(sqrt n). */
    public static boolean isPerfectSquareOdd(int num) {
        long n = num;
        for (long odd = 1; n > 0; odd += 2) n -= odd;
        return n == 0;
    }
}
