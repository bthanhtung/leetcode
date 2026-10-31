package MaximumSubarray;

/*
 * 53. Maximum Subarray — https://leetcode.com/problems/maximum-subarray/
 * Find the contiguous subarray with the largest sum and return the sum.
 * Input: [-2,1,-3,4,-1,2,1,-5,4] -> 6 ([4,-1,2,1]) · [1] -> 1 · [5,4,-1,7,8] -> 23
 * Follow up: try the divide-and-conquer approach as well.
 * Explanation: README.md
 */
public class MaximumSubarray {
    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));       // 6
        System.out.println(maxSubArrayDivide(new int[]{5, 4, -1, 7, 8}));               // 23
    }

    /* Approach 1 - every start, extend to every end. Time O(n^2). */
    public static int maxSubArrayBruteForce(int[] nums) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                best = Math.max(best, sum);
            }
        }
        return best;
    }

    /* Approach 2 - Kadane: the best sum ending here either extends the previous one or starts fresh. Time O(n), O(1). */
    public static int maxSubArray(int[] nums) {
        int endingHere = nums[0], best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            endingHere = Math.max(nums[i], endingHere + nums[i]);
            best = Math.max(best, endingHere);
        }
        return best;
    }

    /* Approach 3 - divide and conquer: the best is in the left half, the right half, or crosses the middle. O(n log n). */
    public static int maxSubArrayDivide(int[] nums) {
        return solve(nums, 0, nums.length - 1);
    }

    private static int solve(int[] a, int lo, int hi) {
        if (lo == hi) return a[lo];
        int mid = (lo + hi) / 2;
        int leftBest = Integer.MIN_VALUE, sum = 0;
        for (int i = mid; i >= lo; i--) {
            sum += a[i];
            leftBest = Math.max(leftBest, sum);
        }
        int rightBest = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= hi; i++) {
            sum += a[i];
            rightBest = Math.max(rightBest, sum);
        }
        return Math.max(leftBest + rightBest, Math.max(solve(a, lo, mid), solve(a, mid + 1, hi)));
    }

    /* Approach 4 - prefix sums: best = max over j of prefix[j] - (smallest prefix before j). Time O(n). */
    public static int maxSubArrayPrefix(int[] nums) {
        int prefix = 0, minPrefix = 0, best = Integer.MIN_VALUE;
        for (int x : nums) {
            prefix += x;
            best = Math.max(best, prefix - minPrefix);
            minPrefix = Math.min(minPrefix, prefix);
        }
        return best;
    }
}
