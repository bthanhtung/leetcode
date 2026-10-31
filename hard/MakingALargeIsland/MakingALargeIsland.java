package MakingALargeIsland;

import java.util.HashSet;
import java.util.Set;

/*
 * 827. Making A Large Island — https://leetcode.com/problems/making-a-large-island/
 * n x n binary grid. You may change at most one 0 into 1. Return the size of the largest island afterwards
 * (4-directional connectivity).
 * Input: [[1,0],[0,1]] -> 3 · [[1,1],[1,0]] -> 4 · [[1,1],[1,1]] -> 4
 * Explanation: README.md
 */
public class MakingALargeIsland {
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        System.out.println(largestIsland(new int[][]{{1, 0}, {0, 1}}));          // 3
        System.out.println(largestIslandBrute(new int[][]{{1, 1}, {1, 0}}));     // 4
    }

    /* Approach 1 - flip each 0 in turn and measure the island it creates with a flood fill. Time O(n^4). */
    public static int largestIslandBrute(int[][] grid) {
        int n = grid.length, best = 0;
        boolean anyZero = false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == 0) {
                    anyZero = true;
                    grid[i][j] = 1;
                    best = Math.max(best, area(grid, i, j, new boolean[n][n]));
                    grid[i][j] = 0;
                }
        return anyZero ? best : n * n;
    }

    private static int area(int[][] g, int i, int j, boolean[][] seen) {
        if (i < 0 || j < 0 || i >= g.length || j >= g.length || g[i][j] == 0 || seen[i][j]) return 0;
        seen[i][j] = true;
        int a = 1;
        for (int[] d : DIRS) a += area(g, i + d[0], j + d[1], seen);
        return a;
    }

    /*
     * Approach 2 - label every island with an id (2, 3, ...) and remember its size. Then for each 0, add up the sizes
     * of the distinct islands touching it, plus one. Time O(n^2).
     */
    public static int largestIsland(int[][] grid) {
        int n = grid.length;
        int[][] g = new int[n][];
        for (int i = 0; i < n; i++) g[i] = grid[i].clone();
        int[] size = new int[n * n + 2];
        int id = 2, best = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (g[i][j] == 1) {
                    size[id] = label(g, i, j, id);
                    best = Math.max(best, size[id]);
                    id++;
                }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] != 0) continue;
                Set<Integer> touching = new HashSet<>();
                for (int[] d : DIRS) {
                    int r = i + d[0], c = j + d[1];
                    if (r >= 0 && c >= 0 && r < n && c < n && g[r][c] > 1) touching.add(g[r][c]);
                }
                int total = 1;
                for (int t : touching) total += size[t];
                best = Math.max(best, total);
            }
        }
        return best;
    }

    private static int label(int[][] g, int i, int j, int id) {
        if (i < 0 || j < 0 || i >= g.length || j >= g.length || g[i][j] != 1) return 0;
        g[i][j] = id;
        int a = 1;
        for (int[] d : DIRS) a += label(g, i + d[0], j + d[1], id);
        return a;
    }
}
