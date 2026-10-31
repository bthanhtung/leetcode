package CountingBits;

/*
 * 338. Counting Bits — https://leetcode.com/problems/counting-bits/
 * Given n, return ans of length n + 1 where ans[i] is the number of 1 bits in i.
 * Input: 2 -> [0,1,1] · 5 -> [0,1,1,2,1,2]. Follow up: O(n) without built-in popcount.
 * Explanation: README.md
 */
public class CountingBits {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(countBits(5)));            // [0, 1, 1, 2, 1, 2]
        System.out.println(java.util.Arrays.toString(countBitsLowestBit(5)));   // same
    }

    /* Approach 1 - count the bits of every number by clearing the lowest set bit. Time O(n log n). */
    public static int[] countBitsNaive(int n) {
        int[] ans = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            for (int x = i; x != 0; x &= x - 1) ans[i]++;
        }
        return ans;
    }

    /* Approach 2 - i has the bits of i / 2 plus its last bit: ans[i] = ans[i >> 1] + (i & 1). Time O(n). */
    public static int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }

    /* Approach 3 - i & (i - 1) clears the lowest set bit: ans[i] = ans[i & (i - 1)] + 1. Time O(n). */
    public static int[] countBitsLowestBit(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i & (i - 1)] + 1;
        }
        return ans;
    }
}
