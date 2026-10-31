package ContainerWithMostWater;

/*
 * 11. Container With Most Water — https://leetcode.com/problems/container-with-most-water/
 * height[i] is a vertical line at x = i. Choose two lines that, with the x-axis, hold the most water. Return that area.
 * Input: [1,8,6,2,5,4,8,3,7] -> 49 (lines 1 and 8: min(8,7) * 7) · [1,1] -> 1
 * Explanation: README.md
 */
public class ContainerWithMostWater {
    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));       // 49
        System.out.println(maxAreaBruteForce(new int[]{1, 1}));                 // 1
    }

    /* Approach 1 - every pair. Time O(n^2). */
    public static int maxAreaBruteForce(int[] height) {
        int best = 0;
        for (int i = 0; i < height.length; i++)
            for (int j = i + 1; j < height.length; j++)
                best = Math.max(best, Math.min(height[i], height[j]) * (j - i));
        return best;
    }

    /*
     * Approach 2 - start with the widest pair; always move the shorter wall inwards. Keeping the shorter wall can only
     * shrink the width without raising the water level, so those pairs never beat the current one. Time O(n).
     */
    public static int maxArea(int[] height) {
        int lo = 0, hi = height.length - 1, best = 0;
        while (lo < hi) {
            best = Math.max(best, Math.min(height[lo], height[hi]) * (hi - lo));
            if (height[lo] < height[hi]) lo++;
            else hi--;
        }
        return best;
    }
}
