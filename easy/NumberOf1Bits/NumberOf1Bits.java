package NumberOf1Bits;

/*
 * 191. Number of 1 Bits — https://leetcode.com/problems/number-of-1-bits/
 * Return the number of set bits (1s) in the binary representation of a positive integer (Hamming weight).
 * Input: 11 -> 3 (1011) · 128 -> 1 · 2147483645 -> 30
 * Explanation: README.md
 */
public class NumberOf1Bits {
    public static void main(String[] args) {
        System.out.println(hammingWeight(11));            // 3
        System.out.println(hammingWeightShift(128));      // 1
        System.out.println(hammingWeightSwar(2147483645)); // 30
    }

    /* Approach 1 - look at all 32 bits. Time O(32). */
    public static int hammingWeightShift(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            count += (n >>> i) & 1;
        }
        return count;
    }

    /* Approach 2 - n & (n - 1) clears the lowest 1 bit; loop once per 1 bit (Brian Kernighan). Time O(#ones). */
    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1;
            count++;
        }
        return count;
    }

    /* Approach 3 - add bits in parallel: pairs, nibbles, bytes (what Integer.bitCount does). Time O(1). */
    public static int hammingWeightSwar(int n) {
        n = n - ((n >>> 1) & 0x55555555);                    // each 2-bit field holds its count
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);     // each 4-bit field
        n = (n + (n >>> 4)) & 0x0f0f0f0f;                    // each byte
        return (n * 0x01010101) >>> 24;                      // sum of the four bytes
    }
}
