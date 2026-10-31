package SearchA2DMatrix;

/*
 * 74. Search a 2D Matrix — https://leetcode.com/problems/search-a-2d-matrix/
 * Each row is sorted, and the first integer of each row is greater than the last of the previous row. Is target in
 * the matrix? Must be O(log(m * n)).
 * Input: [[1,3,5,7],[10,11,16,20],[23,30,34,60]], 3 -> true · 13 -> false
 * Explanation: README.md
 */
public class SearchA2DMatrix {
    public static void main(String[] args) {
        int[][] m = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        System.out.println(searchMatrix(m, 3));           // true
        System.out.println(searchMatrixTwoStep(m, 13));   // false
    }

    /* Approach 1 - binary search the last row whose first element <= target, then binary search inside it. */
    public static boolean searchMatrixTwoStep(int[][] matrix, int target) {
        int lo = 0, hi = matrix.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (matrix[mid][0] <= target) lo = mid;
            else hi = mid - 1;
        }
        return java.util.Arrays.binarySearch(matrix[lo], target) >= 0;
    }

    /* Approach 2 - the matrix read row by row is one sorted array of m * n: index k is matrix[k / n][k % n]. */
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int lo = 0, hi = m * n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int v = matrix[mid / n][mid % n];
            if (v == target) return true;
            if (v < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return false;
    }
}
