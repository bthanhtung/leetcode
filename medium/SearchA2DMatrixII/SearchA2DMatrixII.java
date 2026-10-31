package SearchA2DMatrixII;

/*
 * 240. Search a 2D Matrix II — https://leetcode.com/problems/search-a-2d-matrix-ii/
 * Each row of the matrix is sorted left to right and each column top to bottom. Is target in the matrix?
 * Input: [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], 5 -> true · 20 -> false
 * Explanation: README.md
 */
public class SearchA2DMatrixII {
    public static void main(String[] args) {
        int[][] m = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        System.out.println(searchMatrix(m, 5));                  // true
        System.out.println(searchMatrixBinary(m, 20));           // false
    }

    /* Approach 1 - binary search inside every row. Time O(m log n). */
    public static boolean searchMatrixBinary(int[][] matrix, int target) {
        for (int[] row : matrix) {
            if (java.util.Arrays.binarySearch(row, target) >= 0) return true;
        }
        return false;
    }

    /*
     * Approach 2 - start at the top-right corner: too big -> the whole column below is bigger, go left; too small ->
     * the whole row to the left is smaller, go down. Each step drops a row or a column. Time O(m + n).
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        int r = 0, c = matrix[0].length - 1;
        while (r < matrix.length && c >= 0) {
            int v = matrix[r][c];
            if (v == target) return true;
            if (v > target) c--;
            else r++;
        }
        return false;
    }
}
