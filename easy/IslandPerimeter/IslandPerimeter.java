package IslandPerimeter;

/*
 * 463. Island Perimeter — https://leetcode.com/problems/island-perimeter/
 * grid has exactly one island of 1s (land) surrounded by 0s (water), connected horizontally/vertically, no lakes.
 * Return the perimeter of the island.
 * Input: [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]] -> 16 · [[1]] -> 4
 * Explanation: README.md
 */
public class IslandPerimeter {
    public static void main(String[] args) {
        int[][] grid = {{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}};
        System.out.println(islandPerimeter(grid));           // 16
        System.out.println(islandPerimeterShared(grid));     // 16
    }

    /* Approach 1 - each land cell adds one for every side that touches water or the border. Time O(m * n). */
    public static int islandPerimeter(int[][] grid) {
        int m = grid.length, n = grid[0].length, perimeter = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                for (int[] d : dirs) {
                    int r = i + d[0], c = j + d[1];
                    if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == 0) perimeter++;
                }
            }
        }
        return perimeter;
    }

    /* Approach 2 - 4 * land - 2 * shared edges; count only the up and left neighbours to see each edge once. */
    public static int islandPerimeterShared(int[][] grid) {
        int land = 0, shared = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) continue;
                land++;
                if (i > 0 && grid[i - 1][j] == 1) shared++;
                if (j > 0 && grid[i][j - 1] == 1) shared++;
            }
        }
        return 4 * land - 2 * shared;
    }

    /* Approach 3 - DFS from any land cell; stepping into water or off the grid is one edge of perimeter. */
    public static int islandPerimeterDfs(int[][] grid) {
        boolean[][] seen = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] == 1) return dfs(grid, i, j, seen);
        return 0;
    }

    private static int dfs(int[][] grid, int i, int j, boolean[][] seen) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) return 1;
        if (seen[i][j]) return 0;
        seen[i][j] = true;
        return dfs(grid, i + 1, j, seen) + dfs(grid, i - 1, j, seen) + dfs(grid, i, j + 1, seen) + dfs(grid, i, j - 1, seen);
    }
}
