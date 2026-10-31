package MinimumSizeSubarraySum;

/*
 * 209. Minimum Size Subarray Sum — https://leetcode.com/problems/minimum-size-subarray-sum/
 * Given positive integers nums and a positive target, return the minimal length of a contiguous subarray whose sum is
 * >= target, or 0 if none. Follow up: also an O(n log n) solution.
 * Input: target = 7, [2,3,1,2,4,3] -> 2 ([4,3]) · target = 4, [1,4,4] -> 1 · target = 11, [1,1,1,1,1,1,1,1] -> 0
 * Explanation: README.md
 */
public class MinimumSizeSubarraySum {
    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));                     // 2
        System.out.println(minSubArrayLenBinarySearch(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1})); // 0
    }

    /* Approach 1 - every start, extend until the sum reaches target. Time O(n^2). */
    public static int minSubArrayLenBruteForce(int target, int[] nums) {
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length && j - i + 1 < best; j++) {
                sum += nums[j];
                if (sum >= target) {
                    best = j - i + 1;
                    break;
                }
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    /*
     * Approach 2 - sliding window (works because all numbers are positive): grow right; while the window's sum is
     * >= target, record its length and shrink from the left. Each index enters and leaves once: O(n).
     */
    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    /* Approach 3 - prefix sums are increasing; for each start i binary-search the first end with prefix >= prefix[i] + target. */
    public static int minSubArrayLenBinarySearch(int target, int[] nums) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            long need = prefix[i] + target;
            int lo = i + 1, hi = n + 1;                       // search in prefix[i+1..n]; n+1 means "not found"
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (prefix[mid] >= need) hi = mid;
                else lo = mid + 1;
            }
            if (lo <= n) best = Math.min(best, lo - i);
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }
}
