package RemoveDuplicatesFromSortedArrayII;

/*
 * 80. Remove Duplicates from Sorted Array II — https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 * In a sorted array, remove duplicates in place so each value appears at most twice. Return k, the new length;
 * the first k elements must hold the result in order.
 * Input: [1,1,1,2,2,3] -> 5, [1,1,2,2,3] · [0,0,1,1,1,1,2,3,3] -> 7, [0,0,1,1,2,3,3]
 * Explanation: README.md
 */
public class RemoveDuplicatesFromSortedArrayII {
    public static void main(String[] args) {
        System.out.println(removeDuplicates(new int[]{1, 1, 1, 2, 2, 3}));             // 5
        System.out.println(removeDuplicatesCount(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3})); // 7
    }

    /* Approach 1 - track how long the current run of equal values is; copy only the first two of each run. */
    public static int removeDuplicatesCount(int[] nums) {
        int write = 0, run = 0;
        for (int read = 0; read < nums.length; read++) {
            run = read > 0 && nums[read] == nums[read - 1] ? run + 1 : 1;
            if (run <= 2) nums[write++] = nums[read];
        }
        return write;
    }

    /* Approach 2 - keep x unless it equals the element written two places back. Time O(n), space O(1). */
    public static int removeDuplicates(int[] nums) {
        int write = 0;
        for (int x : nums) {
            if (write < 2 || x != nums[write - 2]) nums[write++] = x;
        }
        return write;
    }
}
