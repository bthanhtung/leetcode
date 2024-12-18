package com.tungdadev.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * Link: https://leetcode.com/problems/contains-duplicate-ii/
 * Given an integer array nums and an integer k
 * return true if there are two distinct indices i and j in the array such that nums[i] == nums[j] and abs(i - j) <= k.
 */
public class ContainsDuplicateII {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(containsNearbyDuplicate(new int[]{1,2,3,1,2,3}, 2));
    }

    /*
     * Solution implement here
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        // // Sử dụng HashMap để lưu giá trị và chỉ số
        // HashMap<Integer, Integer> map = new HashMap<>();
        //
        // for (int i = 0; i < nums.length; i++) {
        //     // Kiểm tra nếu giá trị đã tồn tại trong HashMap và khoảng cách chỉ số <= k
        //     if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
        //         return true;
        //     }
        //     // Cập nhật chỉ số mới nhất của giá trị
        //     map.put(nums[i], i);
        // }
        //
        // return false;

        // Java 21
        var map = new HashMap<Integer, Integer>();

        return IntStream.range(0, nums.length)
                .anyMatch(i -> {
                    if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                        return true;
                    }
                    map.put(nums[i], i);
                    return false;
                });
    }
}
