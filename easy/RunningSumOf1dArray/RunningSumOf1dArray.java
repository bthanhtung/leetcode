package RunningSumOf1dArray;

/*
 * 1480. Running Sum of 1d Array — https://leetcode.com/problems/running-sum-of-1d-array/
 * runningSum[i] = nums[0] + ... + nums[i]. Return the running sum of nums.
 * Input: [1,2,3,4] -> [1,3,6,10] · [1,1,1,1,1] -> [1,2,3,4,5]
 * Explanation: README.md
 */
public class RunningSumOf1dArray {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(runningSum(new int[]{1, 2, 3, 4})));          // [1, 3, 6, 10]
        System.out.println(java.util.Arrays.toString(runningSumInPlace(new int[]{3, 1, 2, 10, 1}))); // [3, 4, 6, 16, 17]
    }

    /* Approach 1 - new array: out[i] = out[i - 1] + nums[i]. Time O(n), space O(n). */
    public static int[] runningSum(int[] nums) {
        int[] out = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            out[i] = (i == 0 ? 0 : out[i - 1]) + nums[i];
        }
        return out;
    }

    /* Approach 2 - overwrite the input. Time O(n), space O(1). */
    public static int[] runningSumInPlace(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }
}
