package BricksFallingWhenHit;

import java.util.Arrays;

/*
 * 803. Bricks Falling When Hit — https://leetcode.com/problems/bricks-falling-when-hit/
 * grid of bricks (1) and empty cells (0). A brick is stable if it is in the top row or 4-connected to a stable
 * brick. hits[i] erases one cell (possibly already empty); after each hit, unstable bricks fall immediately.
 * Return how many bricks fall after each hit.
 * Input: grid = [[1,0,0,0],[1,1,1,0]], hits = [[1,0]] -> [2] · grid = [[1,0,0,0],[1,1,0,0]], hits = [[1,1],[1,0]] -> [0,0]
 * Explanation: README.md
 */
public class BricksFallingWhenHit {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(hitBricks(new int[][]{{1, 0, 0, 0}, {1, 1, 1, 0}}, new int[][]{{1, 0}})));   // [2]
    }

    private static int[] parent, size;

    /*
     * Falling is hard to simulate forwards, but adding bricks back in reverse is just union-find. Remove all hit
     * bricks first, union the rest (top row joins a virtual ROOF node). Then undo hits from last to first: the roof
     * component grows by (new size - old size - 1) bricks - exactly those that fell at that hit.
     * Time O((m*n + h) * alpha).
     */
    public static int[] hitBricks(int[][] grid, int[][] hits) {
        int m = grid.length, n = grid[0].length, roof = m * n;
        int[][] g = new int[m][];
        for (int i = 0; i < m; i++) g[i] = grid[i].clone();
        for (int[] h : hits) if (g[h[0]][h[1]] == 1) g[h[0]][h[1]] = 2;          // 2 = brick that will be hit
        parent = new int[roof + 1];
        size = new int[roof + 1];
        for (int i = 0; i <= roof; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (g[i][j] == 1) attach(g, i, j, roof);
        int[] result = new int[hits.length];
        for (int k = hits.length - 1; k >= 0; k--) {
            int i = hits[k][0], j = hits[k][1];
            if (g[i][j] != 2) continue;                                          // nothing was there
            int before = size[find(roof)];
            g[i][j] = 1;
            attach(g, i, j, roof);
            int after = size[find(roof)];
            result[k] = Math.max(0, after - before - 1);
        }
        return result;
    }

    /** Unions (i, j) with its brick neighbours (all four; union is idempotent) and with the roof if in row 0. */
    private static void attach(int[][] g, int i, int j, int roof) {
        int n = g[0].length, id = i * n + j;
        if (i == 0) union(id, roof);
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            int a = i + d[0], b = j + d[1];
            if (a >= 0 && b >= 0 && a < g.length && b < n && g[a][b] == 1) union(id, a * n + b);
        }
    }

    private static int find(int x) {
        while (parent[x] != x) x = parent[x] = parent[parent[x]];
        return x;
    }

    private static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return;
        if (size[a] < size[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        size[a] += size[b];
    }
}
