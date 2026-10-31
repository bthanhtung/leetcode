package ReverseBits;

/*
 * 190. Reverse Bits — https://leetcode.com/problems/reverse-bits/
 * Reverse the bits of a 32-bit unsigned integer (given as a Java int).
 * Input: 00000010100101000001111010011100 (43261596) -> 00111001011110000010100101000000 (964176192)
 * Explanation: README.md
 */
public class ReverseBits {
    public static void main(String[] args) {
        System.out.println(reverseBits(43261596));           // 964176192
        System.out.println(reverseBitsSwap(-3));             // -1073741825 (bits 1011...1)
    }

    /* Approach 1 - pop the lowest bit of n, push it onto the result. 32 steps. */
    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1;
        }
        return result;
    }

    /* Approach 2 - swap halves, then quarters, bytes, nibbles, pairs, single bits (what Integer.reverse does). O(1). */
    public static int reverseBitsSwap(int n) {
        n = (n >>> 16) | (n << 16);
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }
}
