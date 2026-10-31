package NextPermutation;

import java.util.Arrays;

/*
 * 31. Next Permutation — https://leetcode.com/problems/next-permutation/
 * Rearrange nums into the next lexicographically greater permutation in place; if none, the smallest (sorted).
 * Input: [1,2,3] -> [1,3,2] · [3,2,1] -> [1,2,3] · [1,1,5] -> [1,5,1]
 * Explanation: README.md
 */
public class NextPermutation {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 4, 2};
        nextPermutation(a);
        System.out.println(Arrays.toString(a));     // [1, 4, 2, 3, 5]
    }

    /*
     * 1. From the right, find the first i with nums[i] < nums[i + 1] (the suffix after i is non-increasing).
     * 2. If found, swap nums[i] with the rightmost element larger than it.
     * 3. Reverse the suffix so it becomes the smallest arrangement. O(n), O(1).
     */
    public static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;
        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) j--;
            swap(nums, i, j);
        }
        for (int l = i + 1, r = nums.length - 1; l < r; l++, r--) swap(nums, l, r);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
