package FindFirstAndLastPositionOfElementInSortedArray;

import java.util.Arrays;

/*
 * 34. Find First and Last Position of Element in Sorted Array — https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 * Sorted array: return [first index, last index] of target, or [-1,-1]. Must run in O(log n).
 * Input: [5,7,7,8,8,10], 8 -> [3,4] · [5,7,7,8,8,10], 6 -> [-1,-1] · [], 0 -> [-1,-1]
 * Explanation: README.md
 */
public class FindFirstAndLastPositionOfElementInSortedArray {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));     // [3, 4]
    }

    /* first = lowerBound(target); last = lowerBound(target + 1) - 1. Two binary searches, O(log n). */
    public static int[] searchRange(int[] nums, int target) {
        int first = lowerBound(nums, target);
        if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
        return new int[]{first, lowerBound(nums, (long) target + 1) - 1};
    }

    /** First index i with nums[i] >= x (nums.length if none). */
    private static int lowerBound(int[] nums, long x) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (nums[mid] < x) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
