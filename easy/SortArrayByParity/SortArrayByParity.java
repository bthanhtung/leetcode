package SortArrayByParity;

/*
 * 905. Sort Array By Parity — https://leetcode.com/problems/sort-array-by-parity/
 * Move all even integers to the front of the array, followed by all odd integers. Any such order is accepted.
 * Input: [3,1,2,4] -> [2,4,3,1] (also [4,2,3,1], [2,4,1,3], [4,2,1,3]) · [0] -> [0]
 * Explanation: README.md
 */
public class SortArrayByParity {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(sortArrayByParity(new int[]{3, 1, 2, 4})));       // [4, 2, 1, 3]
        System.out.println(java.util.Arrays.toString(sortArrayByParityTwoPass(new int[]{3, 1, 2, 4}))); // [2, 4, 3, 1]
    }

    /* Approach 1 - new array: evens first, then odds. Keeps relative order. Time O(n), space O(n). */
    public static int[] sortArrayByParityTwoPass(int[] nums) {
        int[] out = new int[nums.length];
        int k = 0;
        for (int x : nums) if (x % 2 == 0) out[k++] = x;
        for (int x : nums) if (x % 2 != 0) out[k++] = x;
        return out;
    }

    /* Approach 2 - two pointers from both ends (Hoare-style partition): swap an odd on the left with an even on the right. */
    public static int[] sortArrayByParity(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            if (nums[lo] % 2 == 0) {
                lo++;
            } else if (nums[hi] % 2 != 0) {
                hi--;
            } else {
                int tmp = nums[lo];
                nums[lo++] = nums[hi];
                nums[hi--] = tmp;
            }
        }
        return nums;
    }

    /* Approach 3 - write pointer (Lomuto-style, like Move Zeroes): swap each even to the front. Keeps evens in order. */
    public static int[] sortArrayByParityWrite(int[] nums) {
        int write = 0;
        for (int read = 0; read < nums.length; read++) {
            if (nums[read] % 2 == 0) {
                int tmp = nums[write];
                nums[write++] = nums[read];
                nums[read] = tmp;
            }
        }
        return nums;
    }
}
