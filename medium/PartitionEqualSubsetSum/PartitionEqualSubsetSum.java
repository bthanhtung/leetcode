package PartitionEqualSubsetSum;

import java.math.BigInteger;

/*
 * 416. Partition Equal Subset Sum — https://leetcode.com/problems/partition-equal-subset-sum/
 * Can nums be split into two subsets with equal sums?
 * Input: [1,5,11,5] -> true ([1,5,5] and [11]) · [1,2,3,5] -> false
 * Explanation: README.md
 */
public class PartitionEqualSubsetSum {
    public static void main(String[] args) {
        System.out.println(canPartition(new int[]{1, 5, 11, 5}));     // true
    }

    /* Approach 1 - need a subset with sum total/2. can[s] = some subset sums to s; iterate s downwards. O(n * total). */
    public static boolean canPartition(int[] nums) {
        int total = 0;
        for (int x : nums) total += x;
        if (total % 2 != 0) return false;
        int half = total / 2;
        boolean[] can = new boolean[half + 1];
        can[0] = true;
        for (int x : nums) {
            for (int s = half; s >= x; s--) can[s] |= can[s - x];
            if (can[half]) return true;
        }
        return can[half];
    }

    /* Approach 2 - bit s of `bits` = sum s reachable; adding x is bits |= bits << x. */
    public static boolean canPartitionBits(int[] nums) {
        int total = 0;
        for (int x : nums) total += x;
        if (total % 2 != 0) return false;
        BigInteger bits = BigInteger.ONE;
        for (int x : nums) bits = bits.or(bits.shiftLeft(x));
        return bits.testBit(total / 2);
    }
}
