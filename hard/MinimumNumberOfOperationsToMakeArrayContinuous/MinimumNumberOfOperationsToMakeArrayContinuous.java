package MinimumNumberOfOperationsToMakeArrayContinuous;

import java.util.Arrays;

/*
 * 2009. Minimum Number of Operations to Make Array Continuous — https://leetcode.com/problems/minimum-number-of-operations-to-make-array-continuous/
 * An array of length n is continuous if its elements are unique and max - min = n - 1. In one operation you can replace
 * any element with any integer. Return the minimum number of operations to make nums continuous.
 * Input: [4,2,5,3] -> 0 · [1,2,3,5,6] -> 1 · [1,10,100,1000] -> 3
 * Explanation: README.md
 */
public class MinimumNumberOfOperationsToMakeArrayContinuous {
    public static void main(String[] args) {
        System.out.println(minOperations(new int[]{1, 2, 3, 5, 6}));             // 1
        System.out.println(minOperationsBinarySearch(new int[]{1, 10, 100, 1000})); // 3
    }

    private static int[] sortedUnique(int[] nums) {
        return Arrays.stream(nums).distinct().sorted().toArray();
    }

    /*
     * Approach 1 - the final values form a window [start, start + n - 1]. Keep as many existing distinct values as
     * possible inside one such window: slide over the sorted distinct values. Answer = n - most kept. O(n log n).
     */
    public static int minOperations(int[] nums) {
        int n = nums.length;
        int[] u = sortedUnique(nums);
        int best = 0, right = 0;
        for (int left = 0; left < u.length; left++) {
            while (right < u.length && u[right] <= u[left] + n - 1) right++;
            best = Math.max(best, right - left);
        }
        return n - best;
    }

    /* Approach 2 - for each distinct value as the window start, binary search how many values fit. O(n log n). */
    public static int minOperationsBinarySearch(int[] nums) {
        int n = nums.length;
        int[] u = sortedUnique(nums);
        int best = 0;
        for (int left = 0; left < u.length; left++) {
            long limit = (long) u[left] + n - 1;
            int lo = left, hi = u.length;                         // first index with value > limit
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (u[mid] <= limit) lo = mid + 1;
                else hi = mid;
            }
            best = Math.max(best, lo - left);
        }
        return n - best;
    }
}
