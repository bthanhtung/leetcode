package MaximumProductOfThreeNumbers;

import java.util.Arrays;

/*
 * 628. Maximum Product of Three Numbers — https://leetcode.com/problems/maximum-product-of-three-numbers/
 * Find three numbers whose product is maximum and return that product.
 * Input: [1,2,3] -> 6 · [1,2,3,4] -> 24 · [-1,-2,-3] -> -6 · [-10,-10,5,2] -> 500
 * Explanation: README.md
 */
public class MaximumProductOfThreeNumbers {
    public static void main(String[] args) {
        System.out.println(maximumProduct(new int[]{-10, -10, 5, 2}));     // 500
    }

    /* Approach 1 - sort; the answer is the top three, or the two smallest (negatives) times the largest. O(n log n). */
    public static int maximumProductSort(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        int n = a.length;
        return Math.max(a[n - 1] * a[n - 2] * a[n - 3], a[0] * a[1] * a[n - 1]);
    }

    /* Approach 2 - the same five numbers found in one pass. O(n), O(1). */
    public static int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int x : nums) {
            if (x > max1) { max3 = max2; max2 = max1; max1 = x; }
            else if (x > max2) { max3 = max2; max2 = x; }
            else if (x > max3) max3 = x;
            if (x < min1) { min2 = min1; min1 = x; }
            else if (x < min2) min2 = x;
        }
        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
    }
}
