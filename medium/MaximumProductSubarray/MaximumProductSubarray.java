package MaximumProductSubarray;

/*
 * 152. Maximum Product Subarray — https://leetcode.com/problems/maximum-product-subarray/
 * Find the contiguous non-empty subarray with the largest product and return the product.
 * Input: [2,3,-2,4] -> 6 · [-2,0,-1] -> 0 · [-2,3,-4] -> 24
 * Explanation: README.md
 */
public class MaximumProductSubarray {
    public static void main(String[] args) {
        System.out.println(maxProduct(new int[]{2, 3, -2, 4}));     // 6
        System.out.println(maxProduct(new int[]{-2, 3, -4}));       // 24
    }

    /*
     * Approach 1 - a negative number swaps the largest and smallest products, so keep both for subarrays ending at i.
     * O(n), O(1).
     */
    public static int maxProduct(int[] nums) {
        int max = nums[0], min = nums[0], best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            if (x < 0) {
                int t = max;
                max = min;
                min = t;
            }
            max = Math.max(x, max * x);
            min = Math.min(x, min * x);
            best = Math.max(best, max);
        }
        return best;
    }

    /*
     * Approach 2 - the best subarray (between zeros) is a prefix or a suffix of a zero-free segment, because an even
     * count of negatives keeps everything and an odd count drops a prefix or a suffix. Scan from both ends.
     */
    public static int maxProductPrefixSuffix(int[] nums) {
        int n = nums.length, best = Integer.MIN_VALUE;
        long left = 0, right = 0;
        for (int i = 0; i < n; i++) {
            left = (left == 0 ? 1 : left) * nums[i];
            right = (right == 0 ? 1 : right) * nums[n - 1 - i];
            best = (int) Math.max(best, Math.max(left, right));
        }
        return best;
    }
}
