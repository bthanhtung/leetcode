package SingleNumberII;

import java.util.HashMap;
import java.util.Map;

/*
 * 137. Single Number II — https://leetcode.com/problems/single-number-ii/
 * Every element appears three times except for one, which appears once. Find it in linear time, constant space.
 * Input: [2,2,3,2] -> 3 · [0,1,0,1,0,1,99] -> 99
 * Explanation: README.md
 */
public class SingleNumberII {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{2, 2, 3, 2}));                 // 3
        System.out.println(singleNumberBits(new int[]{0, 1, 0, 1, 0, 1, 99}));   // 99
    }

    /* Approach 1 - count occurrences. Time O(n), space O(n). */
    public static int singleNumberMap(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int x : nums) count.merge(x, 1, Integer::sum);
        for (Map.Entry<Integer, Integer> e : count.entrySet()) if (e.getValue() == 1) return e.getKey();
        throw new IllegalArgumentException();
    }

    /* Approach 2 - for each of the 32 bits, count how many numbers have it set; count % 3 is the single number's bit. */
    public static int singleNumberBits(int[] nums) {
        int result = 0;
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            for (int x : nums) count += (x >>> bit) & 1;
            if (count % 3 != 0) result |= 1 << bit;
        }
        return result;
    }

    /*
     * Approach 3 - a per-bit counter modulo 3 held in two masks: ones has the bits seen 1 (mod 3) times, twos the bits
     * seen 2 times. A bit seen a third time leaves both. Time O(n), space O(1).
     */
    public static int singleNumber(int[] nums) {
        int ones = 0, twos = 0;
        for (int x : nums) {
            ones = (ones ^ x) & ~twos;
            twos = (twos ^ x) & ~ones;
        }
        return ones;
    }
}
