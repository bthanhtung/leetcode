package com.tungdadev.easy;

/**
 * @author TungDaDev
 * @since 09/01/2026 - 00:27
 */
/**
 * Link: https://leetcode.com/problems/remove-element/description/
 */
public class RemoveElement {
    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{3, 2, 2, 3}, 3)); // 2
        System.out.println(removeElement(new int[]{0,1,2,2,3,0,4,2}, 2)); // 5
        System.out.println(removeElement(new int[]{}, 0)); // 0
    }

    public static int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
}
