package ThreeSumClosest;

import java.util.Arrays;

/*
 * 16. 3Sum Closest — https://leetcode.com/problems/3sum-closest/
 * Find three integers in nums whose sum is closest to target and return that sum. Exactly one answer exists.
 * Input: [-1,2,1,-4], target = 1 -> 2 (-1 + 2 + 1) · [0,0,0], target = 1 -> 0
 * Explanation: README.md
 */
public class ThreeSumClosest {
    public static void main(String[] args) {
        System.out.println(threeSumClosest(new int[]{-1, 2, 1, -4}, 1));            // 2
        System.out.println(threeSumClosestBruteForce(new int[]{0, 0, 0}, 1));       // 0
    }

    /* Approach 1 - all triples. Time O(n^3). */
    public static int threeSumClosestBruteForce(int[] nums, int target) {
        int best = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length; i++)
            for (int j = i + 1; j < nums.length; j++)
                for (int k = j + 1; k < nums.length; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (Math.abs(sum - target) < Math.abs(best - target)) {
                        best = sum;
                    }
                }
        return best;
    }

    /* Approach 2 - sort, fix i, two pointers move toward the target; exact hit returns at once. Time O(n^2), space O(1). */
    public static int threeSumClosest(int[] nums, int target) {
        int[] a = nums.clone();
        Arrays.sort(a);
        int best = a[0] + a[1] + a[2];
        for (int i = 0; i < a.length - 2; i++) {
            int lo = i + 1, hi = a.length - 1;
            while (lo < hi) {
                int sum = a[i] + a[lo] + a[hi];
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }
                if (sum < target) {
                    lo++;
                } else if (sum > target) {
                    hi--;
                } else {
                    return sum;
                }
            }
        }
        return best;
    }
}
