package MaximalRectangle;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
 * 85. Maximal Rectangle — https://leetcode.com/problems/maximal-rectangle/
 * Given a binary matrix of '0' and '1', return the area of the largest rectangle containing only '1's.
 * Input: [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]] -> 6 · [["0"]] -> 0
 * Explanation: README.md
 */
public class MaximalRectangle {
    public static void main(String[] args) {
        char[][] m = {"10100".toCharArray(), "10111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        System.out.println(maximalRectangle(m));          // 6
        System.out.println(maximalRectangleDp(m));        // 6
    }

    /*
     * Approach 1 - treat each row as the floor of a histogram whose bars are the runs of '1' above it; solve
     * "largest rectangle in histogram" (problem 84) with a monotonic stack per row. Time O(m * n).
     */
    public static int maximalRectangle(char[][] matrix) {
        int n = matrix[0].length, best = 0;
        int[] heights = new int[n];
        for (char[] row : matrix) {
            for (int j = 0; j < n; j++) heights[j] = row[j] == '1' ? heights[j] + 1 : 0;
            best = Math.max(best, largestInHistogram(heights));
        }
        return best;
    }

    private static int largestInHistogram(int[] h) {
        Deque<Integer> stack = new ArrayDeque<>();            // indexes of increasing heights
        int best = 0;
        for (int i = 0; i <= h.length; i++) {
            int cur = i == h.length ? 0 : h[i];               // a final 0 flushes the stack
            while (!stack.isEmpty() && h[stack.peek()] >= cur) {
                int height = h[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                best = Math.max(best, height * (i - left - 1));
            }
            stack.push(i);
        }
        return best;
    }

    /*
     * Approach 2 - for every cell keep height (run of 1s above), left and right (how far the bar of that height can
     * stretch in the current row, constrained by the rows above). Area = height * (right - left). Time O(m * n).
     */
    public static int maximalRectangleDp(char[][] matrix) {
        int n = matrix[0].length, best = 0;
        int[] height = new int[n], left = new int[n], right = new int[n];
        Arrays.fill(right, n);
        for (char[] row : matrix) {
            int curLeft = 0, curRight = n;
            for (int j = 0; j < n; j++) height[j] = row[j] == '1' ? height[j] + 1 : 0;
            for (int j = 0; j < n; j++) {
                if (row[j] == '1') left[j] = Math.max(left[j], curLeft);
                else {
                    left[j] = 0;
                    curLeft = j + 1;
                }
            }
            for (int j = n - 1; j >= 0; j--) {
                if (row[j] == '1') right[j] = Math.min(right[j], curRight);
                else {
                    right[j] = n;
                    curRight = j;
                }
            }
            for (int j = 0; j < n; j++) best = Math.max(best, height[j] * (right[j] - left[j]));
        }
        return best;
    }

    /** Reference for tests: every rectangle checked with 2D prefix sums. */
    static int bruteForce(char[][] m) {
        int rows = m.length, cols = m[0].length, best = 0;
        int[][] p = new int[rows + 1][cols + 1];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) p[i + 1][j + 1] = (m[i][j] - '0') + p[i][j + 1] + p[i + 1][j] - p[i][j];
        for (int r1 = 0; r1 < rows; r1++)
            for (int r2 = r1; r2 < rows; r2++)
                for (int c1 = 0; c1 < cols; c1++)
                    for (int c2 = c1; c2 < cols; c2++) {
                        int area = (r2 - r1 + 1) * (c2 - c1 + 1);
                        if (p[r2 + 1][c2 + 1] - p[r1][c2 + 1] - p[r2 + 1][c1] + p[r1][c1] == area) best = Math.max(best, area);
                    }
        return best;
    }
}
