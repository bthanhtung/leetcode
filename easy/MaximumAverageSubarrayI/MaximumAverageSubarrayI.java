package MaximumAverageSubarrayI;

/*
 * 643. Maximum Average Subarray I — https://leetcode.com/problems/maximum-average-subarray-i/
 * Find the contiguous subarray of length k with the maximum average and return that average.
 * Input: [1,12,-5,-6,50,3], k = 4 -> 12.75 ((12 - 5 - 6 + 50) / 4) · [5], k = 1 -> 5.0
 * Explanation: README.md
 */
public class MaximumAverageSubarrayI {
    public static void main(String[] args) {
        System.out.println(findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4));      // 12.75
    }

    /* Approach 1 - sliding window of size k: add the entering element, subtract the leaving one. O(n), O(1). */
    public static double findMaxAverage(int[] nums, int k) {
        long sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];
        long best = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            best = Math.max(best, sum);
        }
        return (double) best / k;
    }

    /* Approach 2 - prefix sums: window sum = prefix[i + k] - prefix[i]. O(n), O(n). */
    public static double findMaxAveragePrefix(int[] nums, int k) {
        long[] prefix = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) prefix[i + 1] = prefix[i] + nums[i];
        long best = Long.MIN_VALUE;
        for (int i = 0; i + k <= nums.length; i++) best = Math.max(best, prefix[i + k] - prefix[i]);
        return (double) best / k;
    }
}
