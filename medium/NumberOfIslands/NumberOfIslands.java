package NumberOfIslands;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 200. Number of Islands — https://leetcode.com/problems/number-of-islands/
 * grid of '1' (land) and '0' (water). Count the islands: groups of land connected horizontally or vertically.
 * Input: [["1","1","0","0","0"],["1","1","0","0","0"],["0","0","1","0","0"],["0","0","0","1","1"]] -> 3
 * Explanation: README.md
 */
public class NumberOfIslands {
    public static void main(String[] args) {
        char[][] grid = {"11000".toCharArray(), "11000".toCharArray(), "00100".toCharArray(), "00011".toCharArray()};
        System.out.println(numIslands(grid));        // 3
    }

    /* Approach 1 - every unvisited land cell starts a new island; DFS sinks the whole island. Time O(m * n). */
    public static int numIslands(char[][] grid) {
        char[][] g = copy(grid);
        int count = 0;
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++)
                if (g[i][j] == '1') {
                    count++;
                    sink(g, i, j);
                }
        return count;
    }

    private static void sink(char[][] g, int i, int j) {
        if (i < 0 || i >= g.length || j < 0 || j >= g[0].length || g[i][j] != '1') return;
        g[i][j] = '0';
        sink(g, i + 1, j);
        sink(g, i - 1, j);
        sink(g, i, j + 1);
        sink(g, i, j - 1);
    }

    /* Approach 2 - the same with a BFS queue (no deep recursion). Time O(m * n), space O(min(m, n)). */
    public static int numIslandsBfs(char[][] grid) {
        char[][] g = copy(grid);
        int m = g.length, n = g[0].length, count = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] != '1') continue;
                count++;
                Queue<int[]> queue = new ArrayDeque<>();
                queue.add(new int[]{i, j});
                g[i][j] = '0';
                while (!queue.isEmpty()) {
                    int[] c = queue.poll();
                    for (int[] d : dirs) {
                        int r = c[0] + d[0], col = c[1] + d[1];
                        if (r >= 0 && r < m && col >= 0 && col < n && g[r][col] == '1') {
                            g[r][col] = '0';
                            queue.add(new int[]{r, col});
                        }
                    }
                }
            }
        }
        return count;
    }

    /* Approach 3 - union-find: start with one set per land cell, every successful union merges two islands. */
    public static int numIslandsUnionFind(char[][] grid) {
        int m = grid.length, n = grid[0].length, count = 0;
        int[] parent = new int[m * n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == '1') {
                    parent[i * n + j] = i * n + j;
                    count++;
                }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != '1') continue;
                if (i + 1 < m && grid[i + 1][j] == '1' && union(parent, i * n + j, (i + 1) * n + j)) count--;
                if (j + 1 < n && grid[i][j + 1] == '1' && union(parent, i * n + j, i * n + j + 1)) count--;
            }
        }
        return count;
    }

    private static int find(int[] p, int x) {
        while (p[x] != x) {
            p[x] = p[p[x]];
            x = p[x];
        }
        return x;
    }

    private static boolean union(int[] p, int a, int b) {
        int ra = find(p, a), rb = find(p, b);
        if (ra == rb) return false;
        p[ra] = rb;
        return true;
    }

    private static char[][] copy(char[][] grid) {
        char[][] g = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) g[i] = grid[i].clone();
        return g;
    }
}
