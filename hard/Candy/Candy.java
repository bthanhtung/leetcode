package Candy;

import java.util.Arrays;

/*
 * 135. Candy — https://leetcode.com/problems/candy/
 * Children stand in a line with ratings. Each child gets at least one candy, and a child with a higher rating than a
 * neighbour must get more candies than that neighbour. Return the minimum total.
 * Input: [1,0,2] -> 5 (2,1,2) · [1,2,2] -> 4 (1,2,1)
 * Explanation: README.md
 */
public class Candy {
    public static void main(String[] args) {
        System.out.println(candy(new int[]{1, 0, 2}));            // 5
        System.out.println(candySlopes(new int[]{1, 2, 2}));      // 4
    }

    /* Approach 1 - left pass satisfies "more than the left neighbour", right pass the right neighbour. O(n), O(n). */
    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] c = new int[n];
        Arrays.fill(c, 1);
        for (int i = 1; i < n; i++) if (ratings[i] > ratings[i - 1]) c[i] = c[i - 1] + 1;
        for (int i = n - 2; i >= 0; i--) if (ratings[i] > ratings[i + 1]) c[i] = Math.max(c[i], c[i + 1] + 1);
        int total = 0;
        for (int x : c) total += x;
        return total;
    }

    /*
     * Approach 2 - one pass over up-slopes and down-slopes: an up-slope of length u costs 1 + 2 + ... + u extra, a
     * down-slope likewise; the peak belongs to the longer of the two. O(n) time, O(1) space.
     */
    public static int candySlopes(int[] ratings) {
        int n = ratings.length;
        if (n <= 1) return n;
        int total = 1, up = 0, down = 0, peak = 0;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                peak = up;
                down = 0;
                total += 1 + up;
            } else if (ratings[i] == ratings[i - 1]) {
                up = down = peak = 0;
                total += 1;
            } else {
                up = 0;
                down++;
                total += 1 + down - (peak >= down ? 1 : 0);        // the whole down-slope grows by one; the peak only if it is now too small
            }
        }
        return total;
    }
}
