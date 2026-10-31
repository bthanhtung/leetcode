package RemoveDuplicatesFromSortedArray;

/*
 * 26. Remove Duplicates from Sorted Array — https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * Remove duplicates in place from a sorted array so each value appears once; return k, the number of unique values.
 * The first k elements must hold them in order.
 * Input: [1,1,2] -> 2, [1,2,_] · [0,0,1,1,1,2,2,3,3,4] -> 5, [0,1,2,3,4,...]
 * Explanation: README.md
 */
public class RemoveDuplicatesFromSortedArray {
    public static void main(String[] args) {
        System.out.println(removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));    // 5
        System.out.println(removeDuplicatesKeepK(new int[]{1, 1, 2}, 1));               // 2
    }

    /* Approach 1 - write a value only when it differs from the last written one. Time O(n), space O(1). */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int write = 1;
        for (int read = 1; read < nums.length; read++) {
            if (nums[read] != nums[write - 1]) {
                nums[write++] = nums[read];
            }
        }
        return write;
    }

    /* Approach 2 - keep at most k copies: compare with the element k places back in the output (k = 2 solves problem 80). */
    public static int removeDuplicatesKeepK(int[] nums, int k) {
        int write = 0;
        for (int x : nums) {
            if (write < k || x != nums[write - k]) {
                nums[write++] = x;
            }
        }
        return write;
    }
}
