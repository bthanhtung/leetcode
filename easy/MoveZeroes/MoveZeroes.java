package MoveZeroes;

/*
 * 283. Move Zeroes — https://leetcode.com/problems/move-zeroes/
 * Move all 0s to the end of nums in place, keeping the relative order of the non-zero elements.
 * Input: [0,1,0,3,12] -> [1,3,12,0,0] · [0] -> [0]. Follow up: minimize the number of operations.
 * Explanation: README.md
 */
public class MoveZeroes {
    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);
        System.out.println(java.util.Arrays.toString(nums));     // [1, 3, 12, 0, 0]
    }

    /* Approach 1 - copy non-zeros into a new array, copy back. Time O(n), space O(n). */
    public static void moveZeroesExtra(int[] nums) {
        int[] out = new int[nums.length];
        int k = 0;
        for (int x : nums) if (x != 0) out[k++] = x;
        System.arraycopy(out, 0, nums, 0, nums.length);
    }

    /* Approach 2 - write pointer: compact non-zeros to the front, then fill the tail with zeros. Time O(n), space O(1). */
    public static void moveZeroesFill(int[] nums) {
        int write = 0;
        for (int x : nums) {
            if (x != 0) nums[write++] = x;
        }
        while (write < nums.length) nums[write++] = 0;
    }

    /* Approach 3 - swap each non-zero with the first zero; one pass, no writes when there are no zeros before it. */
    public static void moveZeroes(int[] nums) {
        int write = 0;
        for (int read = 0; read < nums.length; read++) {
            if (nums[read] != 0) {
                if (read != write) {
                    nums[write] = nums[read];
                    nums[read] = 0;
                }
                write++;
            }
        }
    }
}
