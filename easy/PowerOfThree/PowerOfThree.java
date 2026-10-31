package PowerOfThree;

/*
 * 326. Power of Three — https://leetcode.com/problems/power-of-three/
 * Return true if n is a power of three (n = 3^x for some integer x >= 0). Follow up: no loops or recursion.
 * Input: 27 -> true · 0 -> false · -1 -> false · 9 -> true
 * Explanation: README.md
 */
public class PowerOfThree {
    public static void main(String[] args) {
        System.out.println(isPowerOfThree(27));       // true
        System.out.println(isPowerOfThreeLoop(45));   // false
    }

    /* Approach 1 - divide by 3 while it divides evenly; a power of 3 ends at 1. O(log n). */
    public static boolean isPowerOfThreeLoop(int n) {
        if (n <= 0) return false;
        while (n % 3 == 0) n /= 3;
        return n == 1;
    }

    /* Approach 2 - 3^19 = 1162261467 is the largest power of 3 in int; since 3 is prime, n divides it iff n = 3^x. O(1). */
    public static boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }

    /* Approach 3 - log3(n) must be an integer; compare after rounding to dodge floating-point error. */
    public static boolean isPowerOfThreeLog(int n) {
        if (n <= 0) return false;
        long p = Math.round(Math.log(n) / Math.log(3));
        return Math.pow(3, p) == n;
    }
}
