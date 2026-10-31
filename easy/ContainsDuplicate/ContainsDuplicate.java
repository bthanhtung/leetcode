package ContainsDuplicate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 217. Contains Duplicate — https://leetcode.com/problems/contains-duplicate/
 * Given an integer array nums, return true if any value appears at least twice, and false if every element is distinct.
 * Input: [1,2,3,1] -> true · [1,2,3,4] -> false
 * Explanation: README.md
 */
public class ContainsDuplicate {
    public static void main(String[] args) {
        System.out.println(containsDuplicateBruteForce(new int[]{1, 2, 3, 1}));   // true
        System.out.println(containsDuplicateSort(new int[]{1, 2, 3, 4}));         // false
        System.out.println(containsDuplicate(new int[]{1, 1, 1, 3, 3, 4}));       // true
    }

    /* Approach 1 - compare every pair. Time O(n^2), space O(1). */
    public static boolean containsDuplicateBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Approach 2 - sort a copy; equal values become neighbours. Time O(n log n), space O(n) for the copy (O(1) in place). */
    public static boolean containsDuplicateSort(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] == sorted[i - 1]) {
                return true;
            }
        }
        return false;
    }

    /* Approach 3 - hash set of seen values; add() returns false when the value is already there. Time O(n), space O(n). */
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }
}
