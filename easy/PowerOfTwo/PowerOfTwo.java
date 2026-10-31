package PowerOfTwo;

/*
 * 231. Power of Two — https://leetcode.com/problems/power-of-two/
 * Return true if n is a power of two. Follow up: no loops or recursion.
 * Input: 1 -> true · 16 -> true · 3 -> false · 0 -> false
 * Explanation: README.md
 */
public class PowerOfTwo {
    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(16));      // true
        System.out.println(isPowerOfTwoLoop(6));   // false
    }

    /* Approach 1 - halve while even. O(log n). */
    public static boolean isPowerOfTwoLoop(int n) {
        if (n <= 0) return false;
        while (n % 2 == 0) n /= 2;
        return n == 1;
    }

    /* Approach 2 - a power of two has exactly one 1 bit; n & (n - 1) clears it, leaving 0. O(1). */
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    /* Approach 3 - n & -n isolates the lowest set bit; for a power of two that bit is n itself. */
    public static boolean isPowerOfTwoLowestBit(int n) {
        return n > 0 && (n & -n) == n;
    }
}
