package SquaresOfASortedArray;

import java.util.Arrays;

/*
 * 977. Squares of a Sorted Array — https://leetcode.com/problems/squares-of-a-sorted-array/
 * Given an integer array sorted in non-decreasing order, return the squares of each number, also sorted.
 * Input: [-4,-1,0,3,10] -> [0,1,9,16,100] · [-7,-3,2,3,11] -> [4,9,9,49,121]. Follow up: O(n).
 * Explanation: README.md
 */
public class SquaresOfASortedArray {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(sortedSquares(new int[]{-4, -1, 0, 3, 10})));      // [0, 1, 9, 16, 100]
    }

    /* Approach 1 - square everything, then sort. Time O(n log n). */
    public static int[] sortedSquaresSort(int[] nums) {
        int[] out = new int[nums.length];
        for (int i = 0; i < nums.length; i++) out[i] = nums[i] * nums[i];
        Arrays.sort(out);
        return out;
    }

    /*
     * Approach 2 - the largest square is at one of the two ends (most negative or most positive). Compare the ends and
     * fill the output from the back. Time O(n), space O(n) for the output.
     */
    public static int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        int lo = 0, hi = n - 1;
        for (int k = n - 1; k >= 0; k--) {
            if (Math.abs(nums[lo]) > Math.abs(nums[hi])) {
                out[k] = nums[lo] * nums[lo];
                lo++;
            } else {
                out[k] = nums[hi] * nums[hi];
                hi--;
            }
        }
        return out;
    }
}
