package HammingDistance;

/*
 * 461. Hamming Distance — https://leetcode.com/problems/hamming-distance/
 * The Hamming distance between two integers is the number of positions at which their bits differ.
 * Input: x = 1, y = 4 -> 2 (0001 vs 0100) · x = 3, y = 1 -> 1
 * Explanation: README.md
 */
public class HammingDistance {
    public static void main(String[] args) {
        System.out.println(hammingDistance(1, 4));          // 2
        System.out.println(hammingDistanceLoop(3, 1));      // 1
    }

    /* Approach 1 - differing bits are exactly the 1s of x ^ y. Time O(1). */
    public static int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    /* Approach 2 - count the 1s of x ^ y by clearing the lowest one each time. Time O(#differences). */
    public static int hammingDistanceKernighan(int x, int y) {
        int count = 0;
        for (int z = x ^ y; z != 0; z &= z - 1) count++;
        return count;
    }

    /* Approach 3 - compare the 32 bit positions one by one. Time O(32). */
    public static int hammingDistanceLoop(int x, int y) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if (((x >>> i) & 1) != ((y >>> i) & 1)) count++;
        }
        return count;
    }
}
