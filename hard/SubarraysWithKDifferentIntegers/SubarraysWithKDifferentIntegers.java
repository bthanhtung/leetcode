package SubarraysWithKDifferentIntegers;

import java.util.HashSet;
import java.util.Set;

/*
 * 992. Subarrays with K Different Integers — https://leetcode.com/problems/subarrays-with-k-different-integers/
 * Return the number of contiguous subarrays with exactly k distinct integers.
 * Input: [1,2,1,2,3], k = 2 -> 7 · [1,2,1,3,4], k = 3 -> 3
 * Explanation: README.md
 */
public class SubarraysWithKDifferentIntegers {
    public static void main(String[] args) {
        System.out.println(subarraysWithKDistinct(new int[]{1, 2, 1, 2, 3}, 2));          // 7
        System.out.println(subarraysWithKDistinctBrute(new int[]{1, 2, 1, 3, 4}, 3));     // 3
    }

    /* Approach 1 - every start; extend while tracking distinct values. Time O(n^2). */
    public static int subarraysWithKDistinctBrute(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            Set<Integer> seen = new HashSet<>();
            for (int j = i; j < nums.length; j++) {
                seen.add(nums[j]);
                if (seen.size() == k) count++;
                else if (seen.size() > k) break;
            }
        }
        return count;
    }

    /*
     * Approach 2 - "exactly k" is hard to slide, "at most k" is easy: for each right end, every start in the minimal
     * window is valid, adding (right - left + 1). exactly(k) = atMost(k) - atMost(k - 1). Time O(n).
     */
    public static int subarraysWithKDistinct(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private static int atMost(int[] nums, int k) {
        int[] count = new int[nums.length + 1];             // values are in 1..n
        int left = 0, distinct = 0, total = 0;
        for (int right = 0; right < nums.length; right++) {
            if (count[nums[right]]++ == 0) distinct++;
            while (distinct > k) {
                if (--count[nums[left++]] == 0) distinct--;
            }
            total += right - left + 1;                     // subarrays ending at right with <= k distinct
        }
        return total;
    }
}
