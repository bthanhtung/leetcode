package SetMatrixZeroes;

/*
 * 73. Set Matrix Zeroes — https://leetcode.com/problems/set-matrix-zeroes/
 * If an element is 0, set its entire row and column to 0, in place. Follow up: constant extra space.
 * Input: [[1,1,1],[1,0,1],[1,1,1]] -> [[1,0,1],[0,0,0],[1,0,1]]
 * Explanation: README.md
 */
public class SetMatrixZeroes {
    public static void main(String[] args) {
        int[][] m = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        setZeroes(m);
        System.out.println(java.util.Arrays.deepToString(m));     // [[0, 0, 0, 0], [0, 4, 5, 0], [0, 3, 1, 0]]
    }

    /* Approach 1 - remember which rows and columns contain a zero, then clear them. Space O(m + n). */
    public static void setZeroesSets(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[] zeroRow = new boolean[m], zeroCol = new boolean[n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] == 0) zeroRow[i] = zeroCol[j] = true;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (zeroRow[i] || zeroCol[j]) matrix[i][j] = 0;
    }

    /*
     * Approach 2 - use row 0 and column 0 as the marker arrays; two flags remember whether row 0 / column 0 themselves
     * must be cleared. Clear the inner part first, the first row/column last. Space O(1).
     */
    public static void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean firstRow = false, firstCol = false;
        for (int j = 0; j < n; j++) if (matrix[0][j] == 0) firstRow = true;
        for (int i = 0; i < m; i++) if (matrix[i][0] == 0) firstCol = true;
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                if (matrix[i][j] == 0) matrix[i][0] = matrix[0][j] = 0;
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                if (matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
        if (firstRow) for (int j = 0; j < n; j++) matrix[0][j] = 0;
        if (firstCol) for (int i = 0; i < m; i++) matrix[i][0] = 0;
    }
}
