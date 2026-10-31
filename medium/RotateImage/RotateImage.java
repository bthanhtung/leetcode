package RotateImage;

/*
 * 48. Rotate Image — https://leetcode.com/problems/rotate-image/
 * Rotate an n x n matrix 90 degrees clockwise, in place.
 * Input: [[1,2,3],[4,5,6],[7,8,9]] -> [[7,4,1],[8,5,2],[9,6,3]]
 * Explanation: README.md
 */
public class RotateImage {
    public static void main(String[] args) {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        rotate(m);
        System.out.println(java.util.Arrays.deepToString(m));     // [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
    }

    /* Approach 1 - new matrix: element (i, j) goes to (j, n - 1 - i). Not in place: O(n^2) extra space. */
    public static void rotateCopy(int[][] matrix) {
        int n = matrix.length;
        int[][] out = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                out[j][n - 1 - i] = matrix[i][j];
        for (int i = 0; i < n; i++) matrix[i] = out[i];
    }

    /* Approach 2 - transpose (flip over the main diagonal), then reverse every row. Time O(n^2), space O(1). */
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {          // j > i: swap each pair once
                int t = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = t;
            }
        }
        for (int[] row : matrix) {
            for (int l = 0, r = n - 1; l < r; l++, r--) {
                int t = row[l];
                row[l] = row[r];
                row[r] = t;
            }
        }
    }

    /* Approach 3 - layer by layer, move four cells around the ring in one go (top <- left <- bottom <- right <- top). */
    public static void rotateLayers(int[][] matrix) {
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer, last = n - 1 - layer;
            for (int k = 0; k < last - first; k++) {
                int top = matrix[first][first + k];
                matrix[first][first + k] = matrix[last - k][first];          // left -> top
                matrix[last - k][first] = matrix[last][last - k];            // bottom -> left
                matrix[last][last - k] = matrix[first + k][last];            // right -> bottom
                matrix[first + k][last] = top;                               // top -> right
            }
        }
    }
}
