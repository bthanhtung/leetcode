package UniquePathsII;

/*
 * 63. Unique Paths II — https://leetcode.com/problems/unique-paths-ii/
 * A robot moves only right or down from the top-left to the bottom-right of a grid; 1 marks an obstacle.
 * Count the unique paths.
 * Input: [[0,0,0],[0,1,0],[0,0,0]] -> 2 · [[0,1],[0,0]] -> 1 · [[1]] -> 0
 * Explanation: README.md
 */
public class UniquePathsII {
    public static void main(String[] args) {
        System.out.println(uniquePathsWithObstacles(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}));     // 2
    }

    /* ways[j] = paths to the current row's cell j = paths from above (old ways[j]) + from the left (ways[j-1]). */
    public static int uniquePathsWithObstacles(int[][] grid) {
        int n = grid[0].length;
        int[] ways = new int[n];
        ways[0] = 1;
        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                if (row[j] == 1) ways[j] = 0;
                else if (j > 0) ways[j] += ways[j - 1];
            }
        }
        return ways[n - 1];
    }
}
