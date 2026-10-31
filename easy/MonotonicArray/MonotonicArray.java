package MonotonicArray;

/*
 * 896. Monotonic Array — https://leetcode.com/problems/monotonic-array/
 * An array is monotonic if it is entirely non-increasing or entirely non-decreasing. Return whether nums is monotonic.
 * Input: [1,2,2,3] -> true · [6,5,4,4] -> true · [1,3,2] -> false
 * Explanation: README.md
 */
public class MonotonicArray {
    public static void main(String[] args) {
        System.out.println(isMonotonic(new int[]{1, 2, 2, 3}));            // true
        System.out.println(isMonotonicDirection(new int[]{1, 3, 2}));      // false
    }

    /* Approach 1 - track whether a rise and a fall have been seen; monotonic unless both. Time O(n), space O(1). */
    public static boolean isMonotonic(int[] nums) {
        boolean up = false, down = false;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) up = true;
            if (nums[i] < nums[i - 1]) down = true;
            if (up && down) return false;
        }
        return true;
    }

    /* Approach 2 - the first non-zero difference fixes the direction; every later difference must agree. */
    public static boolean isMonotonicDirection(int[] nums) {
        int direction = 0;
        for (int i = 1; i < nums.length; i++) {
            int d = Integer.compare(nums[i], nums[i - 1]);
            if (d == 0) continue;
            if (direction == 0) direction = d;
            else if (d != direction) return false;
        }
        return true;
    }
}
