package FindMinimumInRotatedSortedArrayII;

/*
 * 154. Find Minimum in Rotated Sorted Array II — https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
 * A sorted array with possible duplicates was rotated. Return its minimum, decreasing the operation steps as much as
 * possible.
 * Input: [1,3,5] -> 1 · [2,2,2,0,1] -> 0 · [3,1,3] -> 1
 * Explanation: README.md
 */
public class FindMinimumInRotatedSortedArrayII {
    public static void main(String[] args) {
        System.out.println(findMin(new int[]{2, 2, 2, 0, 1}));          // 0
        System.out.println(findMinLinear(new int[]{3, 1, 3}));          // 1
    }

    /* Approach 1 - O(n) scan. */
    public static int findMinLinear(int[] nums) {
        int min = nums[0];
        for (int x : nums) min = Math.min(min, x);
        return min;
    }

    /*
     * Approach 2 - like problem 153, compare mid with hi. When they are equal we cannot tell which side holds the
     * minimum, but nums[hi] has a copy at mid, so dropping hi is safe. Average O(log n), worst O(n) (all equal).
     */
    public static int findMin(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] > nums[hi]) lo = mid + 1;
            else if (nums[mid] < nums[hi]) hi = mid;
            else hi--;
        }
        return nums[lo];
    }
}
