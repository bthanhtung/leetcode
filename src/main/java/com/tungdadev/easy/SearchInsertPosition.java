package com.tungdadev.easy;

public class SearchInsertPosition {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int target = 5;
        int target2 = 2;
        int target3 = 7;
        int target4 = 0;
        System.out.println(searchInsert(nums, target)); // Output: 2
        System.out.println(searchInsert(nums, target2)); // Output: 1
        System.out.println(searchInsert(nums, target3)); // Output: 4
        System.out.println(searchInsert(nums, target4)); // Output: 0
    }

    private static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
