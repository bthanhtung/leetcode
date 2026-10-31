package DivideTwoIntegers;

/*
 * 29. Divide Two Integers — https://leetcode.com/problems/divide-two-integers/
 * Divide two 32-bit integers without *, / or %; truncate toward zero. Clamp overflow to Integer.MAX_VALUE.
 * Input: (10, 3) -> 3 · (7, -3) -> -2 · (-2147483648, -1) -> 2147483647
 * Explanation: README.md
 */
public class DivideTwoIntegers {
    public static void main(String[] args) {
        System.out.println(divide(10, 3));                    // 3
        System.out.println(divide(Integer.MIN_VALUE, -1));    // 2147483647
    }

    /*
     * Work with negatives (their range is larger, so MIN_VALUE needs no special case). For the remaining dividend,
     * double the divisor while it still fits, subtract the largest chunk, add the matching power of two to the
     * quotient. O(log^2 n).
     */
    public static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        boolean negative = (dividend < 0) != (divisor < 0);
        int a = dividend > 0 ? -dividend : dividend, b = divisor > 0 ? -divisor : divisor;
        int quotient = 0;
        while (a <= b) {
            int chunk = b, count = -1;
            while (chunk >= Integer.MIN_VALUE >> 1 && a <= chunk + chunk) {     // chunk + chunk does not overflow
                chunk += chunk;
                count += count;
            }
            a -= chunk;
            quotient += count;
        }
        return negative ? quotient : -quotient;
    }

    /* Approach 2 - long arithmetic with shifts: try divisor << k for k = 31..0 like long division in binary. O(32). */
    public static int divideShift(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        long a = Math.abs((long) dividend), b = Math.abs((long) divisor), q = 0;
        for (int k = 31; k >= 0; k--) {
            if ((a >> k) >= b) {
                a -= b << k;
                q |= 1L << k;
            }
        }
        return (int) (((dividend < 0) != (divisor < 0)) ? -q : q);
    }
}
