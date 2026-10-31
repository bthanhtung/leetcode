package PowXN;

/*
 * 50. Pow(x, n) — https://leetcode.com/problems/powx-n/
 * Implement pow(x, n) for a double x and a 32-bit integer n (possibly negative).
 * Input: (2.0, 10) -> 1024.0 · (2.1, 3) -> 9.261 · (2.0, -2) -> 0.25
 * Explanation: README.md
 */
public class PowXN {
    public static void main(String[] args) {
        System.out.println(myPow(2.0, 10));     // 1024.0
        System.out.println(myPow(2.0, -2));     // 0.25
    }

    /*
     * Approach 1 - binary exponentiation: walk the bits of |n|; square the base each step and multiply it in when the
     * bit is set. Use long for n so -Integer.MIN_VALUE does not overflow. O(log n).
     */
    public static double myPow(double x, int n) {
        long e = n;
        if (e < 0) {
            x = 1 / x;
            e = -e;
        }
        double result = 1;
        for (; e > 0; e >>= 1, x *= x) {
            if ((e & 1) == 1) result *= x;
        }
        return result;
    }

    /* Approach 2 - x^n = (x^(n/2))^2 * (x if n is odd). */
    public static double myPowRecursive(double x, int n) {
        long e = n;
        return e < 0 ? 1 / pow(x, -e) : pow(x, e);
    }

    private static double pow(double x, long e) {
        if (e == 0) return 1;
        double half = pow(x, e / 2);
        return e % 2 == 0 ? half * half : half * half * x;
    }
}
