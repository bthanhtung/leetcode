package com.tungdadev.easy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author TungDaDev
 * @since 25/06/2025 - 00:14
 */
public class ContainsDuplicate {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1}; // Example input
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        boolean result = containsDuplicate.containsDuplicate(nums);
        System.out.println("Contains duplicate? " + result);
    }

    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false; // No duplicates in an empty array
        }

        Set<Integer> seen = new HashSet<>(); // Use a HashSet to track seen numbers

        for (int num : nums) {
            if (seen.contains(num)) {
                return true; // Duplicate found
            }
            seen.add(num); // Add the number to the set
        }

        return false; // No duplicates found
    }
}
