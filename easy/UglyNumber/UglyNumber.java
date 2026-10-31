package UglyNumber;

/*
 * 263. Ugly Number — https://leetcode.com/problems/ugly-number/
 * An ugly number is a positive integer whose prime factors are only 2, 3 and 5. Return whether n is ugly.
 * Input: 6 -> true · 1 -> true · 14 -> false (7) · 0 -> false
 * Explanation: README.md
 */
public class UglyNumber {
    public static void main(String[] args) {
        System.out.println(isUgly(6));      // true
        System.out.println(isUgly(14));     // false
    }

    /* Remove every factor 2, 3 and 5; an ugly number is left with 1. Time O(log n). */
    public static boolean isUgly(int n) {
        if (n <= 0) return false;
        for (int p : new int[]{2, 3, 5}) {
            while (n % p == 0) n /= p;
        }
        return n == 1;
    }
}
