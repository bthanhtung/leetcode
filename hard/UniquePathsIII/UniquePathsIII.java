package UniquePathsIII;

import java.util.Arrays;

/*
 * 980. Unique Paths III — https://leetcode.com/problems/unique-paths-iii/
 * Grid: 1 = start, 2 = end, 0 = empty, -1 = obstacle. Count the 4-directional walks from start to end that visit
 * every non-obstacle cell exactly once.
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]] -> 2 · [[1,0,0,0],[0,0,0,0],[0,0,0,2]] -> 4 · [[0,1],[2,0]] -> 0
 * Explanation: README.md
 */
public class UniquePathsIII {
    public static void main(String[] args) {
        System.out.println(uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}}));      // 2
        System.out.println(uniquePathsIIIBitmask(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 2}})); // 4
    }

    /* Approach 1 - backtracking: walk, mark cells, count arrivals at the end that used up every free cell. O(3^cells). */
    public static int uniquePathsIII(int[][] grid) {
        int[][] g = new int[grid.length][];
        int free = 0, sr = 0, sc = 0;
        for (int i = 0; i < grid.length; i++) {
            g[i] = grid[i].clone();
            for (int j = 0; j < g[i].length; j++) {
                if (g[i][j] != -1) free++;
                if (g[i][j] == 1) {
                    sr = i;
                    sc = j;
                }
            }
        }
        return walk(g, sr, sc, free);
    }

    private static int walk(int[][] g, int r, int c, int left) {
        if (r < 0 || r >= g.length || c < 0 || c >= g[0].length || g[r][c] == -1) return 0;
        if (g[r][c] == 2) return left == 1 ? 1 : 0;            // the end must be the last free cell
        int saved = g[r][c];
        g[r][c] = -1;                                           // visited
        int ways = walk(g, r + 1, c, left - 1) + walk(g, r - 1, c, left - 1) + walk(g, r, c + 1, left - 1) + walk(g, r, c - 1, left - 1);
        g[r][c] = saved;
        return ways;
    }

    /* Approach 2 - DP over (current cell, set of visited cells) with memoization. O(cells * 2^cells), fine for <= 20 cells. */
    public static int uniquePathsIIIBitmask(int[][] grid) {
        int m = grid.length, n = grid[0].length, cells = m * n;
        int target = 0, start = 0, end = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                int id = i * n + j;
                if (grid[i][j] != -1) target |= 1 << id;
                if (grid[i][j] == 1) start = id;
                if (grid[i][j] == 2) end = id;
            }
        int[][] memo = new int[cells][];
        return count(grid, start, 1 << start, target, end, memo);
    }

    private static int count(int[][] grid, int cell, int visited, int target, int end, int[][] memo) {
        if (cell == end) return visited == target ? 1 : 0;
        if (memo[cell] == null) {
            memo[cell] = new int[1 << (grid.length * grid[0].length)];
            Arrays.fill(memo[cell], -1);
        }
        if (memo[cell][visited] >= 0) return memo[cell][visited];
        int n = grid[0].length, r = cell / n, c = cell % n, ways = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr < 0 || nr >= grid.length || nc < 0 || nc >= n || grid[nr][nc] == -1) continue;
            int id = nr * n + nc;
            if ((visited >> id & 1) == 0) ways += count(grid, id, visited | 1 << id, target, end, memo);
        }
        return memo[cell][visited] = ways;
    }
}
