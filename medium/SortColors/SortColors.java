package SortColors;

/*
 * 75. Sort Colors — https://leetcode.com/problems/sort-colors/
 * nums holds 0 (red), 1 (white) and 2 (blue). Sort it in place so equal colors are adjacent in the order 0, 1, 2,
 * without the library sort. Follow up: one pass with O(1) extra space.
 * Input: [2,0,2,1,1,0] -> [0,0,1,1,2,2] · [2,0,1] -> [0,1,2]
 * Explanation: README.md
 */
public class SortColors {
    public static void main(String[] args) {
        int[] nums = {2, 0, 2, 1, 1, 0};
        sortColors(nums);
        System.out.println(java.util.Arrays.toString(nums));      // [0, 0, 1, 1, 2, 2]
    }

    /* Approach 1 - count the three colors, then overwrite. Two passes. Time O(n), space O(1). */
    public static void sortColorsCounting(int[] nums) {
        int[] count = new int[3];
        for (int x : nums) count[x]++;
        int k = 0;
        for (int color = 0; color < 3; color++) {
            for (int c = count[color]; c > 0; c--) nums[k++] = color;
        }
    }

    /*
     * Approach 2 - Dijkstra's Dutch national flag: [0, low) are 0s, [low, mid) are 1s, (high, n) are 2s, [mid, high]
     * is unknown. Look at nums[mid] and grow the right region. One pass, O(1) space.
     */
    public static void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low++, mid++);
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                swap(nums, mid, high--);        // don't advance mid: the swapped-in value is still unknown
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
