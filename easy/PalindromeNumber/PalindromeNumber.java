package PalindromeNumber;

/*
 * 9. Palindrome Number — https://leetcode.com/problems/palindrome-number/
 * Given an integer x, return true if x is a palindrome (reads the same backward as forward), false otherwise.
 * Input: 121 -> true · -121 -> false · 10 -> false
 * Follow up: could you solve it without converting the integer to a string?
 * Explanation: README.md
 */
public class PalindromeNumber {
    public static void main(String[] args) {
        System.out.println(isPalindromeString(121));      // true
        System.out.println(isPalindromeReverse(-121));    // false
        System.out.println(isPalindromeHalf(10));         // false
    }

    /* Approach 1 - compare the decimal string with its reverse. Time O(d), space O(d) for d digits. */
    public static boolean isPalindromeString(int x) {
        String s = Integer.toString(x);
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    /* Approach 2 - reverse the whole number (in a long, so it can't overflow) and compare. Time O(d), space O(1). */
    public static boolean isPalindromeReverse(int x) {
        if (x < 0) {
            return false;
        }
        long reversed = 0;
        for (int y = x; y > 0; y /= 10) {
            reversed = reversed * 10 + y % 10;
        }
        return reversed == x;
    }

    /*
     * Approach 3 - reverse only the second half: stop when the reversed half is >= what is left.
     * No overflow possible, half the work. Time O(d/2), space O(1).
     */
    public static boolean isPalindromeHalf(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;               // negatives, and numbers ending in 0 (except 0) can't be palindromes
        }
        int half = 0;
        while (x > half) {
            half = half * 10 + x % 10;
            x /= 10;
        }
        return x == half || x == half / 10;   // even length: equal; odd length: drop the middle digit
    }
}
