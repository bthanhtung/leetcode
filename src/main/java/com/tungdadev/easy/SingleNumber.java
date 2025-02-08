package com.tungdadev.easy;

import java.util.HashMap;
import java.util.Map;

public class SingleNumber {
    public static void main(String[] args) {
        int[] nums = {2, 2, 1};
        int[] nums2 = {4, 1, 2, 1, 2};
        int[] nums3 = {1};
        System.out.println(singleNumber(nums)); // Output: 1
        System.out.println(singleNumber(nums2)); // Output: 4
        System.out.println(singleNumber(nums3)); // Output: 1
    }

    private static int singleNumber(int[] nums) {
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int num : nums) {
            if (map.containsKey(num) && map.get(num) == 1) {
                result = num;
                break;
            }
        }
        return result;
    }
}
