package MaxAreaOfIsland;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 695. Max Area of Island — https://leetcode.com/problems/max-area-of-island/
 * grid is a binary matrix; an island is a group of 1s connected 4-directionally. Return the largest island area (0 if none).
 * Input: the 8x13 example -> 6 · [[0,0,0,0,0,0,0,0]] -> 0
 * Explanation: README.md
 */
public class MaxAreaOfIsland {
    public static void main(String[] args) {
        int[][] grid = {{1, 1, 0, 0, 0}, {1, 1, 0, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 0, 1, 1}};
        System.out.println(maxAreaOfIsland(grid));          // 4
    }

    /* Approach 1 - DFS from every unvisited land cell, sinking cells (setting them to 0) as we count. Time O(m * n). */
    public static int maxAreaOfIsland(int[][] grid) {
        int[][] g = copy(grid);
        int best = 0;
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++)
                if (g[i][j] == 1) best = Math.max(best, sink(g, i, j));
        return best;
    }

    private static int sink(int[][] g, int i, int j) {
        if (i < 0 || i >= g.length || j < 0 || j >= g[0].length || g[i][j] == 0) return 0;
        g[i][j] = 0;
        return 1 + sink(g, i + 1, j) + sink(g, i - 1, j) + sink(g, i, j + 1) + sink(g, i, j - 1);
    }

    /* Approach 2 - BFS with a queue: no deep recursion on huge islands. Time O(m * n). */
    public static int maxAreaOfIslandBfs(int[][] grid) {
        int[][] g = copy(grid);
        int m = g.length, n = g[0].length, best = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 0) continue;
                int area = 0;
                Queue<int[]> queue = new ArrayDeque<>();
                queue.add(new int[]{i, j});
                g[i][j] = 0;                        // mark when enqueued, not when dequeued, to avoid duplicates
                while (!queue.isEmpty()) {
                    int[] cell = queue.poll();
                    area++;
                    for (int[] d : dirs) {
                        int r = cell[0] + d[0], c = cell[1] + d[1];
                        if (r >= 0 && r < m && c >= 0 && c < n && g[r][c] == 1) {
                            g[r][c] = 0;
                            queue.add(new int[]{r, c});
                        }
                    }
                }
                best = Math.max(best, area);
            }
        }
        return best;
    }

    /* Approach 3 - union-find: union each land cell with land to its right and below; the largest set size wins. */
    public static int maxAreaOfIslandUnionFind(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] parent = new int[m * n], size = new int[m * n];
        int best = 0;
        for (int i = 0; i < m * n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                best = Math.max(best, 1);
                if (i + 1 < m && grid[i + 1][j] == 1) best = Math.max(best, union(parent, size, i * n + j, (i + 1) * n + j));
                if (j + 1 < n && grid[i][j + 1] == 1) best = Math.max(best, union(parent, size, i * n + j, i * n + j + 1));
            }
        }
        return best;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];          // path halving
            x = parent[x];
        }
        return x;
    }

    private static int union(int[] parent, int[] size, int a, int b) {
        int ra = find(parent, a), rb = find(parent, b);
        if (ra == rb) return size[ra];
        if (size[ra] < size[rb]) {
            int t = ra;
            ra = rb;
            rb = t;
        }
        parent[rb] = ra;
        size[ra] += size[rb];
        return size[ra];
    }

    private static int[][] copy(int[][] grid) {
        int[][] g = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) g[i] = grid[i].clone();
        return g;
    }
}
