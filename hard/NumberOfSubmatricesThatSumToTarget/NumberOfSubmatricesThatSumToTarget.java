package NumberOfSubmatricesThatSumToTarget;

import java.util.HashMap;
import java.util.Map;

/*
 * 1074. Number of Submatrices That Sum to Target — https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/
 * Return the number of non-empty submatrices whose elements sum to target.
 * Input: [[0,1,0],[1,1,1],[0,1,0]], 0 -> 4 · [[1,-1],[-1,1]], 0 -> 5 · [[904]], 0 -> 0
 * Explanation: README.md
 */
public class NumberOfSubmatricesThatSumToTarget {
    public static void main(String[] args) {
        System.out.println(numSubmatrixSumTarget(new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}, 0));      // 4
        System.out.println(numSubmatrixSumTargetBrute(new int[][]{{1, -1}, {-1, 1}}, 0));              // 5
    }

    /* Approach 1 - 2D prefix sums, then every (top, bottom, left, right) in O(1). Time O(m^2 n^2). */
    public static int numSubmatrixSumTargetBrute(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int[][] p = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                p[i + 1][j + 1] = matrix[i][j] + p[i][j + 1] + p[i + 1][j] - p[i][j];
        int count = 0;
        for (int r1 = 0; r1 < m; r1++)
            for (int r2 = r1; r2 < m; r2++)
                for (int c1 = 0; c1 < n; c1++)
                    for (int c2 = c1; c2 < n; c2++)
                        if (p[r2 + 1][c2 + 1] - p[r1][c2 + 1] - p[r2 + 1][c1] + p[r1][c1] == target) count++;
        return count;
    }

    /*
     * Approach 2 - fix the top and bottom rows; collapse the band into one array of column sums; then count subarrays
     * with sum = target using prefix sums + a hash map (problem 560). Time O(m^2 n).
     */
    public static int numSubmatrixSumTarget(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length, count = 0;
        for (int top = 0; top < m; top++) {
            int[] colSum = new int[n];
            for (int bottom = top; bottom < m; bottom++) {
                for (int c = 0; c < n; c++) colSum[c] += matrix[bottom][c];
                Map<Integer, Integer> seen = new HashMap<>();
                seen.put(0, 1);
                int prefix = 0;
                for (int c = 0; c < n; c++) {
                    prefix += colSum[c];
                    count += seen.getOrDefault(prefix - target, 0);
                    seen.merge(prefix, 1, Integer::sum);
                }
            }
        }
        return count;
    }
}
