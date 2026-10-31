package ReverseInteger;

/*
 * 7. Reverse Integer — https://leetcode.com/problems/reverse-integer/
 * Given a signed 32-bit integer x, return x with its digits reversed. If the result overflows [-2^31, 2^31 - 1], return 0.
 * Assume the environment does not allow storing 64-bit integers.
 * Input: 123 -> 321 · -123 -> -321 · 120 -> 21 · 1534236469 -> 0
 * Explanation: README.md
 */
public class ReverseInteger {
    public static void main(String[] args) {
        System.out.println(reverse(-123));              // -321
        System.out.println(reverseLong(120));           // 21
        System.out.println(reverseString(1534236469));  // 0
    }

    /* Approach 1 - via the string. Simple, but parse failure is used to detect overflow. Time O(d). */
    public static int reverseString(int x) {
        String digits = new StringBuilder(Long.toString(Math.abs((long) x))).reverse().toString();
        try {
            return x < 0 ? -Integer.parseInt(digits) : Integer.parseInt(digits);
        } catch (NumberFormatException overflow) {
            return 0;
        }
    }

    /* Approach 2 - build the result in a long and compare against int limits. Breaks the "no 64-bit" rule. Time O(d). */
    public static int reverseLong(int x) {
        long r = 0;
        while (x != 0) {
            r = r * 10 + x % 10;
            x /= 10;
        }
        return r > Integer.MAX_VALUE || r < Integer.MIN_VALUE ? 0 : (int) r;
    }

    /*
     * Approach 3 - pure int: before r = r * 10 + digit, check it would stay in range.
     * r > MAX / 10 overflows for sure; r == MAX / 10 overflows only if digit > 7 (MAX = 2147483647). Same for MIN with -8.
     * Time O(d), space O(1).
     */
    public static int reverse(int x) {
        int r = 0;
        while (x != 0) {
            int digit = x % 10;                  // keeps the sign in Java: -123 % 10 == -3
            x /= 10;
            if (r > Integer.MAX_VALUE / 10 || (r == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (r < Integer.MIN_VALUE / 10 || (r == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }
            r = r * 10 + digit;
        }
        return r;
    }
}
