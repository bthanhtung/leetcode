package SpiralMatrix;

import java.util.ArrayList;
import java.util.List;

/*
 * 54. Spiral Matrix — https://leetcode.com/problems/spiral-matrix/
 * Return all elements of an m x n matrix in spiral order (clockwise from the top-left).
 * Input: [[1,2,3],[4,5,6],[7,8,9]] -> [1,2,3,6,9,8,7,4,5] · [[1,2,3,4],[5,6,7,8],[9,10,11,12]] -> [1,2,3,4,8,12,11,10,9,5,6,7]
 * Explanation: README.md
 */
public class SpiralMatrix {
    public static void main(String[] args) {
        System.out.println(spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));        // [1, 2, 3, 6, 9, 8, 7, 4, 5]
    }

    /* Approach 1 - four walls that move inwards after each side is read. Time O(m * n). */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> out = new ArrayList<>();
        int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) out.add(matrix[top][j]);
            top++;
            for (int i = top; i <= bottom; i++) out.add(matrix[i][right]);
            right--;
            if (top <= bottom) {                                   // a single remaining row was already read
                for (int j = right; j >= left; j--) out.add(matrix[bottom][j]);
                bottom--;
            }
            if (left <= right) {                                   // a single remaining column was already read
                for (int i = bottom; i >= top; i--) out.add(matrix[i][left]);
                left++;
            }
        }
        return out;
    }

    /* Approach 2 - walk and turn right whenever the next cell is outside or already visited. Time O(m * n). */
    public static List<Integer> spiralOrderTurns(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[][] seen = new boolean[m][n];
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Integer> out = new ArrayList<>();
        int r = 0, c = 0, d = 0;
        for (int k = 0; k < m * n; k++) {
            out.add(matrix[r][c]);
            seen[r][c] = true;
            int nr = r + dirs[d][0], nc = c + dirs[d][1];
            if (nr < 0 || nr >= m || nc < 0 || nc >= n || seen[nr][nc]) {
                d = (d + 1) % 4;
                nr = r + dirs[d][0];
                nc = c + dirs[d][1];
            }
            r = nr;
            c = nc;
        }
        return out;
    }
}
