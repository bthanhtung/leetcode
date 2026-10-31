package TransposeMatrix;

import java.util.stream.IntStream;

/*
 * 867. Transpose Matrix — https://leetcode.com/problems/transpose-matrix/
 * Return the transpose of a matrix: rows become columns (flip over the main diagonal).
 * Input: [[1,2,3],[4,5,6],[7,8,9]] -> [[1,4,7],[2,5,8],[3,6,9]] · [[1,2,3],[4,5,6]] -> [[1,4],[2,5],[3,6]]
 * Explanation: README.md
 */
public class TransposeMatrix {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.deepToString(transpose(new int[][]{{1, 2, 3}, {4, 5, 6}})));   // [[1, 4], [2, 5], [3, 6]]
    }

    /* Approach 1 - out[j][i] = matrix[i][j] in a new n x m matrix. Time O(m * n). */
    public static int[][] transpose(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] out = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                out[j][i] = matrix[i][j];
            }
        }
        return out;
    }

    /* Approach 2 - the same with streams: column j of the input is row j of the output. */
    public static int[][] transposeStream(int[][] matrix) {
        return IntStream.range(0, matrix[0].length)
                .mapToObj(j -> IntStream.range(0, matrix.length).map(i -> matrix[i][j]).toArray())
                .toArray(int[][]::new);
    }
}
