package TwoSum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * 1. Two Sum — https://leetcode.com/problems/two-sum/
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 * Each input has exactly one solution, and you may not use the same element twice. Return the answer in any order.
 * Input: nums = [2,7,11,15], target = 9 -> Output: [0,1]
 * Explanation: README.md
 */
public class TwoSum {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSumBruteForce(new int[]{2, 7, 11, 15}, 9)));   // [0, 1]
        System.out.println(Arrays.toString(twoSumHashMap(new int[]{3, 2, 4}, 6)));          // [1, 2]
        System.out.println(Arrays.toString(twoSumSortedPointers(new int[]{3, 3}, 6)));      // [0, 1]
    }

    /* Approach 1 - try every pair. Time O(n^2), space O(1). */
    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /*
     * Approach 2 - one pass with a hash map value -> index. For each number, its partner (target - x)
     * must have appeared earlier if the pair exists. Time O(n), space O(n).
     */
    public static int[] twoSumHashMap(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer j = seen.get(target - nums[i]);
            if (j != null) {
                return new int[]{j, i};
            }
            seen.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    /*
     * Approach 3 - sort indexes by value, then two pointers from both ends. Time O(n log n), space O(n).
     * Useful when the array is already sorted (problem 167) - then it needs O(1) extra space.
     */
    public static int[] twoSumSortedPointers(int[] nums, int target) {
        Integer[] idx = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (a, b) -> Integer.compare(nums[a], nums[b]));
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[idx[lo]] + nums[idx[hi]];
            if (sum == target) {
                return new int[]{Math.min(idx[lo], idx[hi]), Math.max(idx[lo], idx[hi])};
            } else if (sum < target) {
                lo++;
            } else {
                hi--;
            }
        }
        return new int[]{-1, -1};
    }
}
