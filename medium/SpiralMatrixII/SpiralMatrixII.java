package SpiralMatrixII;

/*
 * 59. Spiral Matrix II — https://leetcode.com/problems/spiral-matrix-ii/
 * Generate an n x n matrix filled with 1 .. n^2 in spiral order (clockwise from the top-left).
 * Input: 3 -> [[1,2,3],[8,9,4],[7,6,5]] · 1 -> [[1]]
 * Explanation: README.md
 */
public class SpiralMatrixII {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.deepToString(generateMatrix(3)));           // [[1, 2, 3], [8, 9, 4], [7, 6, 5]]
        System.out.println(java.util.Arrays.deepToString(generateMatrixTurns(4)));
    }

    /* Approach 1 - four walls top/bottom/left/right; fill one side, move that wall inwards, repeat. Time O(n^2). */
    public static int[][] generateMatrix(int n) {
        int[][] m = new int[n][n];
        int top = 0, bottom = n - 1, left = 0, right = n - 1, v = 1;
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) m[top][j] = v++;
            top++;
            for (int i = top; i <= bottom; i++) m[i][right] = v++;
            right--;
            for (int j = right; j >= left && top <= bottom; j--) m[bottom][j] = v++;
            bottom--;
            for (int i = bottom; i >= top && left <= right; i--) m[i][left] = v++;
            left++;
        }
        return m;
    }

    /* Approach 2 - walk with a direction (right, down, left, up); turn when the next cell is outside or already filled. */
    public static int[][] generateMatrixTurns(int n) {
        int[][] m = new int[n][n];
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int r = 0, c = 0, d = 0;
        for (int v = 1; v <= n * n; v++) {
            m[r][c] = v;
            int nr = r + dirs[d][0], nc = c + dirs[d][1];
            if (nr < 0 || nr >= n || nc < 0 || nc >= n || m[nr][nc] != 0) {
                d = (d + 1) % 4;
                nr = r + dirs[d][0];
                nc = c + dirs[d][1];
            }
            r = nr;
            c = nc;
        }
        return m;
    }
}
