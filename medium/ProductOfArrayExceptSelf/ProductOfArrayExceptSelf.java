package ProductOfArrayExceptSelf;

/*
 * 238. Product of Array Except Self — https://leetcode.com/problems/product-of-array-except-self/
 * Return answer where answer[i] is the product of all nums except nums[i], in O(n) and without division.
 * Follow up: O(1) extra space (the output does not count).
 * Input: [1,2,3,4] -> [24,12,8,6] · [-1,1,0,-3,3] -> [0,0,9,0,0]
 * Explanation: README.md
 */
public class ProductOfArrayExceptSelf {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(productExceptSelf(new int[]{1, 2, 3, 4})));        // [24, 12, 8, 6]
        System.out.println(java.util.Arrays.toString(productExceptSelfDivision(new int[]{-1, 1, 0, -3, 3})));
    }

    /* Approach 1 - left[i] = product before i, right[i] = product after i; answer = left * right. O(n) time and space. */
    public static int[] productExceptSelfArrays(int[] nums) {
        int n = nums.length;
        int[] left = new int[n], right = new int[n], out = new int[n];
        left[0] = 1;
        for (int i = 1; i < n; i++) left[i] = left[i - 1] * nums[i - 1];
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) right[i] = right[i + 1] * nums[i + 1];
        for (int i = 0; i < n; i++) out[i] = left[i] * right[i];
        return out;
    }

    /* Approach 2 - store prefix products in the output, then multiply by a running suffix product from the right. O(1) extra. */
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        out[0] = 1;
        for (int i = 1; i < n; i++) out[i] = out[i - 1] * nums[i - 1];
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            out[i] *= suffix;
            suffix *= nums[i];
        }
        return out;
    }

    /* Approach 3 - with division (not allowed by the statement, but a common first idea): handle zeros separately. */
    public static int[] productExceptSelfDivision(int[] nums) {
        int zeros = 0, product = 1;
        for (int x : nums) {
            if (x == 0) zeros++;
            else product *= x;
        }
        int[] out = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (zeros > 1) out[i] = 0;
            else if (zeros == 1) out[i] = nums[i] == 0 ? product : 0;
            else out[i] = product / nums[i];
        }
        return out;
    }
}
