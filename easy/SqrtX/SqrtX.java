package SqrtX;

/*
 * 69. Sqrt(x) — https://leetcode.com/problems/sqrtx/
 * Given a non-negative integer x, return the square root of x rounded down, without using any built-in power/sqrt function.
 * Input: 4 -> 2 · 8 -> 2 (sqrt(8) = 2.828...)
 * Explanation: README.md
 */
public class SqrtX {
    public static void main(String[] args) {
        System.out.println(mySqrt(8));                    // 2
        System.out.println(mySqrtLinear(16));             // 4
        System.out.println(mySqrtNewton(2147395599));     // 46339
    }

    /* Approach 1 - try 1, 2, 3, ... while i * i <= x. Time O(sqrt x). */
    public static int mySqrtLinear(int x) {
        long i = 0;
        while ((i + 1) * (i + 1) <= x) {
            i++;
        }
        return (int) i;
    }

    /* Approach 2 - binary search for the largest m with m * m <= x. Time O(log x), space O(1). */
    public static int mySqrt(int x) {
        long lo = 0, hi = x;
        while (lo < hi) {
            long mid = lo + (hi - lo + 1) / 2;     // round up so lo = mid always makes progress
            if (mid * mid <= x) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return (int) lo;
    }

    /* Approach 3 - Newton's method on f(r) = r^2 - x: r = (r + x / r) / 2 converges very fast. Time O(log log x). */
    public static int mySqrtNewton(int x) {
        if (x < 2) {
            return x;
        }
        long r = x;
        while (r * r > x) {
            r = (r + x / r) / 2;
        }
        return (int) r;
    }
}
