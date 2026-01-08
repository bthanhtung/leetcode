package com.tungdadev.medium;

/**
 * @author TungDaDev
 * @since 11/08/2025 - 00:54
 */
public class _3SumClosest {
    public static void main(String[] args) {
        int [] nums = {-1, 2, 1, -4};
        int target = 1;
        _3SumClosest solution = new _3SumClosest();
        int result = solution.threeSumClosest(nums, target);
        System.out.println("The sum closest to " + target + " is: " + result);
    }

    private int threeSumClosest(int[] nums, int target) {
        int closest = Integer.MAX_VALUE;
        int minDiff = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    int diff = Math.abs(sum - target);

                    if (diff < minDiff) {
                        minDiff = diff;
                        closest = sum;
                    }
                }
            }
        }

        return closest;
    }
}
