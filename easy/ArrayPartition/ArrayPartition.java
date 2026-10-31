package ArrayPartition;

import java.util.Arrays;

/*
 * 561. Array Partition — https://leetcode.com/problems/array-partition/
 * Group 2n integers into n pairs so that the sum of min(a, b) over all pairs is as large as possible. Return that sum.
 * Input: [1,4,3,2] -> 4 ((1,2) and (3,4): 1 + 3) · [6,2,6,5,1,2] -> 9
 * Explanation: README.md
 */
public class ArrayPartition {
    public static void main(String[] args) {
        System.out.println(arrayPairSum(new int[]{1, 4, 3, 2}));                 // 4
        System.out.println(arrayPairSumCounting(new int[]{6, 2, 6, 5, 1, 2}));   // 9
    }

    /* Approach 1 - sort and pair neighbours; the answer is every element at an even index. Time O(n log n). */
    public static int arrayPairSum(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        int sum = 0;
        for (int i = 0; i < a.length; i += 2) {
            sum += a[i];
        }
        return sum;
    }

    /* Approach 2 - values are in [-10^4, 10^4]: counting sort, then walk values taking every other element. Time O(n + range). */
    public static int arrayPairSumCounting(int[] nums) {
        int offset = 10_000;
        int[] count = new int[2 * offset + 1];
        for (int x : nums) count[x + offset]++;
        int sum = 0;
        boolean take = true;                       // the 1st, 3rd, 5th... smallest are the pair minimums
        for (int v = 0; v < count.length; v++) {
            for (int c = count[v]; c > 0; c--) {
                if (take) sum += v - offset;
                take = !take;
            }
        }
        return sum;
    }
}
