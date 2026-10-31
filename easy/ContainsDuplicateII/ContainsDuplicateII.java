package ContainsDuplicateII;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * 219. Contains Duplicate II — https://leetcode.com/problems/contains-duplicate-ii/
 * Given an integer array nums and an integer k, return true if there are two distinct indices i and j with
 * nums[i] == nums[j] and abs(i - j) <= k.
 * Input: [1,2,3,1], k = 3 -> true · [1,0,1,1], k = 1 -> true · [1,2,3,1,2,3], k = 2 -> false
 * Explanation: README.md
 */
public class ContainsDuplicateII {
    public static void main(String[] args) {
        System.out.println(containsNearbyDuplicateBruteForce(new int[]{1, 2, 3, 1}, 3));      // true
        System.out.println(containsNearbyDuplicateLastIndex(new int[]{1, 0, 1, 1}, 1));       // true
        System.out.println(containsNearbyDuplicateWindow(new int[]{1, 2, 3, 1, 2, 3}, 2));    // false
    }

    /* Approach 1 - for each i, look at the next k elements. Time O(n * k), space O(1). */
    public static boolean containsNearbyDuplicateBruteForce(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length && j - i <= k; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Approach 2 - remember the last index of every value; compare distance on a repeat. Time O(n), space O(n). */
    public static boolean containsNearbyDuplicateLastIndex(int[] nums, int k) {
        Map<Integer, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer prev = lastIndex.put(nums[i], i);
            if (prev != null && i - prev <= k) {
                return true;
            }
        }
        return false;
    }

    /*
     * Approach 3 - sliding window: a set holding only the last k values. A repeat inside the set is a nearby duplicate.
     * Time O(n), space O(min(n, k)).
     */
    public static boolean containsNearbyDuplicateWindow(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!window.add(nums[i])) {
                return true;
            }
            if (window.size() > k) {
                window.remove(nums[i - k]);      // the element that just left the window
            }
        }
        return false;
    }
}
