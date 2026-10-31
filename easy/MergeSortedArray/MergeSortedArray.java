package MergeSortedArray;

import java.util.Arrays;

/*
 * 88. Merge Sorted Array — https://leetcode.com/problems/merge-sorted-array/
 * nums1 has length m + n: m sorted numbers followed by n zeros. Merge the sorted nums2 (length n) into nums1 in place.
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3 -> [1,2,2,3,5,6]. Follow up: O(m + n).
 * Explanation: README.md
 */
public class MergeSortedArray {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        merge(nums1, 3, new int[]{2, 5, 6}, 3);
        System.out.println(Arrays.toString(nums1));       // [1, 2, 2, 3, 5, 6]
    }

    /* Approach 1 - copy nums2 into the empty tail, then sort everything. Time O((m + n) log(m + n)). */
    public static void mergeSort(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
    }

    /*
     * Approach 2 - fill nums1 from the back with the larger of the two current tails. Writing at the back never
     * overwrites an unread element of nums1. Time O(m + n), space O(1).
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (j >= 0) {                              // once nums2 is used up, the rest of nums1 is already in place
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }
}
