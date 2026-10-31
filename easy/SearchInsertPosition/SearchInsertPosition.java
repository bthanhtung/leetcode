package SearchInsertPosition;

import java.util.Arrays;

/*
 * 35. Search Insert Position — https://leetcode.com/problems/search-insert-position/
 * Given a sorted array of distinct integers and a target, return its index if found, or the index where it would be inserted
 * to keep the order. Must run in O(log n).
 * Input: [1,3,5,6], 5 -> 2 · [1,3,5,6], 2 -> 1 · [1,3,5,6], 7 -> 4
 * Explanation: README.md
 */
public class SearchInsertPosition {
    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));         // 2
        System.out.println(searchInsertLinear(new int[]{1, 3, 5, 6}, 2));   // 1
        System.out.println(searchInsertLibrary(new int[]{1, 3, 5, 6}, 7));  // 4
    }

    /* Approach 1 - linear scan for the first element >= target. Time O(n). Correct but misses the O(log n) requirement. */
    public static int searchInsertLinear(int[] nums, int target) {
        int i = 0;
        while (i < nums.length && nums[i] < target) {
            i++;
        }
        return i;
    }

    /*
     * Approach 2 - binary search for the lower bound: the first index whose value is >= target.
     * Half-open range [lo, hi); the answer is always inside it. Time O(log n), space O(1).
     */
    public static int searchInsert(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) {
                lo = mid + 1;      // mid and everything left of it is too small
            } else {
                hi = mid;          // mid could be the answer
            }
        }
        return lo;
    }

    /* Approach 3 - Arrays.binarySearch returns -(insertion point) - 1 when the key is missing. */
    public static int searchInsertLibrary(int[] nums, int target) {
        int i = Arrays.binarySearch(nums, target);
        return i >= 0 ? i : -i - 1;
    }
}
