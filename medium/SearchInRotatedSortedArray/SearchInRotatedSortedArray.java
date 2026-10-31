package SearchInRotatedSortedArray;

/*
 * 33. Search in Rotated Sorted Array — https://leetcode.com/problems/search-in-rotated-sorted-array/
 * A sorted array of distinct values was rotated at an unknown pivot. Find target's index in O(log n), or -1.
 * Input: [4,5,6,7,0,1,2], 0 -> 4 · [4,5,6,7,0,1,2], 3 -> -1 · [1], 0 -> -1
 * Explanation: README.md
 */
public class SearchInRotatedSortedArray {
    public static void main(String[] args) {
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));     // 4
    }

    /*
     * At least one half around mid is sorted. If target lies inside the sorted half's range, search there;
     * otherwise search the other half. O(log n).
     */
    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (nums[mid] == target) return mid;
            if (nums[lo] <= nums[mid]) {                             // left half sorted
                if (nums[lo] <= target && target < nums[mid]) hi = mid - 1;
                else lo = mid + 1;
            } else {                                                 // right half sorted
                if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
                else hi = mid - 1;
            }
        }
        return -1;
    }
}
