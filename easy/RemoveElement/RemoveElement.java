package RemoveElement;

/*
 * 27. Remove Element — https://leetcode.com/problems/remove-element/
 * Remove all occurrences of val in nums in-place. Return k = number of elements not equal to val; the first k elements of
 * nums must hold them (any order). What is beyond k does not matter.
 * Input: nums = [3,2,2,3], val = 3 -> 2, nums = [2,2,_,_] · [0,1,2,2,3,0,4,2], val = 2 -> 5
 * Explanation: README.md
 */
public class RemoveElement {
    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{3, 2, 2, 3}, 3));                 // 2
        System.out.println(removeElementSwap(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2)); // 5
    }

    /* Approach 1 - read/write pointers: copy every kept element forward. Keeps the order. Time O(n), space O(1). */
    public static int removeElement(int[] nums, int val) {
        int write = 0;
        for (int read = 0; read < nums.length; read++) {
            if (nums[read] != val) {
                nums[write++] = nums[read];
            }
        }
        return write;
    }

    /*
     * Approach 2 - when nums[i] == val, overwrite it with the last element and shrink the array. Fewer writes when val
     * is rare, but the order changes. Time O(n), space O(1).
     */
    public static int removeElementSwap(int[] nums, int val) {
        int i = 0, n = nums.length;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[--n];         // don't advance i: the moved element must be checked too
            } else {
                i++;
            }
        }
        return n;
    }
}
