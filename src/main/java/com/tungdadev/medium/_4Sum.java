package com.tungdadev.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Link: https://leetcode.com/problems/4sum/
 * Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
  - 0 <= a, b, c, d < n
  - a, b, c, and d are distinct.
  - nums[a] + nums[b] + nums[c] + nums[d] == target
 */
public class _4Sum {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        int[] nums = {1000000000,1000000000,1000000000,1000000000};
        int target = -294967296;

        List<List<Integer>> result = fourSum(nums, target);

        // In kết quả
        for (List<Integer> quad : result) {
            System.out.println(quad);
        }
    }

    /*
     * Solution implement here
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        // Sắp xếp mảng
        Arrays.sort(nums);

        // Duyệt qua mảng
        for (int i = 0; i < nums.length - 3; i++) {
            // Tránh trùng lặp với số i
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Duyệt từ phần tử tiếp theo sau i
            for (int j = i + 1; j < nums.length - 2; j++) {
                // Tránh trùng lặp với số j
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // Sử dụng hai con trỏ
                int left = j + 1;
                int right = nums.length - 1;

                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        // Nếu tìm thấy một bộ bốn số, thêm vào kết quả
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // Tránh trùng lặp với phần tử left
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }

                        // Tránh trùng lặp với phần tử right
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }

                        // Di chuyển con trỏ
                        left++;
                        right--;
                    } else if (sum < target) {
                        // Nếu tổng nhỏ hơn target, di chuyển con trỏ trái
                        left++;
                    } else {
                        // Nếu tổng lớn hơn target, di chuyển con trỏ phải
                        right--;
                    }
                }
            }
        }
        return result;
    }

}
