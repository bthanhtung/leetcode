package BinarySearch;

/*
 * 704. Binary Search — https://leetcode.com/problems/binary-search/
 * Given a sorted array of distinct integers and a target, return its index or -1. Must run in O(log n).
 * Input: [-1,0,3,5,9,12], 9 -> 4 · [-1,0,3,5,9,12], 2 -> -1
 * Explanation: README.md
 */
public class BinarySearch {
    public static void main(String[] args) {
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 9));            // 4
        System.out.println(searchHalfOpen(new int[]{-1, 0, 3, 5, 9, 12}, 2));    // -1
    }

    /* Approach 1 - closed interval [lo, hi]: loop while lo <= hi. Time O(log n), space O(1). */
    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    /* Approach 2 - half-open [lo, hi): find the first index with nums[i] >= target, then check it. */
    public static int searchHalfOpen(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo < nums.length && nums[lo] == target ? lo : -1;
    }

    /* Approach 3 - recursion on the interval. Time O(log n), space O(log n). */
    public static int searchRecursive(int[] nums, int target) {
        return find(nums, target, 0, nums.length - 1);
    }

    private static int find(int[] nums, int target, int lo, int hi) {
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        return nums[mid] < target ? find(nums, target, mid + 1, hi) : find(nums, target, lo, mid - 1);
    }
}
