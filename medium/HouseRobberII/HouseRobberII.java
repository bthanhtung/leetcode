package HouseRobberII;

/*
 * 213. House Robber II — https://leetcode.com/problems/house-robber-ii/
 * Houses are arranged in a circle (first and last are neighbours). You cannot rob two adjacent houses.
 * Return the maximum amount.
 * Input: [2,3,2] -> 3 · [1,2,3,1] -> 4 · [1,2,3] -> 3
 * Explanation: README.md
 */
public class HouseRobberII {
    public static void main(String[] args) {
        System.out.println(rob(new int[]{2, 3, 2}));        // 3
        System.out.println(rob(new int[]{1, 2, 3, 1}));     // 4
    }

    /* The first and last houses cannot both be robbed: answer = max(rob(0..n-2), rob(1..n-1)). O(n), O(1). */
    public static int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        return Math.max(robLine(nums, 0, n - 2), robLine(nums, 1, n - 1));
    }

    /** Problem 198 on nums[from..to]: best = max(skip this house, rob it + best two back). */
    private static int robLine(int[] nums, int from, int to) {
        int prev2 = 0, prev1 = 0;
        for (int i = from; i <= to; i++) {
            int cur = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }
}
