package FindMinimumInRotatedSortedArray;

/*
 * 153. Find Minimum in Rotated Sorted Array — https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 * A sorted array of unique values was rotated (e.g. [0,1,2,4,5,6,7] -> [4,5,6,7,0,1,2]). Return its minimum in O(log n).
 * Input: [3,4,5,1,2] -> 1 · [4,5,6,7,0,1,2] -> 0 · [11,13,15,17] -> 11
 * Explanation: README.md
 */
public class FindMinimumInRotatedSortedArray {
    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3, 4, 5, 1, 2}));               // 1
        System.out.println(findMinLinear(new int[]{4, 5, 6, 7, 0, 1, 2}));   // 0
    }

    /* Approach 1 - the minimum is where the order breaks (or the first element). Time O(n). */
    public static int findMinLinear(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) return nums[i];
        }
        return nums[0];
    }

    /*
     * Approach 2 - compare the middle with the right end: nums[mid] > nums[hi] means the drop (the minimum) is to the
     * right of mid; otherwise mid..hi is sorted and the minimum is at mid or to its left. Time O(log n).
     */
    public static int findMin(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] > nums[hi]) lo = mid + 1;
            else hi = mid;
        }
        return nums[lo];
    }
}
