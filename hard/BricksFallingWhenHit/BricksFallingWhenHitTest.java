package BricksFallingWhenHit;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BricksFallingWhenHitTest {

    /** Forward simulation: after each hit, flood from the top row and drop everything not reached. */
    private static int[] simulate(int[][] grid, int[][] hits) {
        int m = grid.length, n = grid[0].length;
        int[][] g = new int[m][];
        for (int i = 0; i < m; i++) g[i] = grid[i].clone();
        int[] out = new int[hits.length + 1];
        for (int k = -1; k < hits.length; k++) {           // k = -1 drops bricks floating from the start (not counted)
            if (k >= 0) g[hits[k][0]][hits[k][1]] = 0;
            boolean[][] stable = new boolean[m][n];
            ArrayDeque<int[]> q = new ArrayDeque<>();
            for (int j = 0; j < n; j++) if (g[0][j] == 1) { stable[0][j] = true; q.add(new int[]{0, j}); }
            while (!q.isEmpty()) {
                int[] c = q.poll();
                int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                for (int[] dd : d) {
                    int a = c[0] + dd[0], b = c[1] + dd[1];
                    if (a >= 0 && b >= 0 && a < m && b < n && g[a][b] == 1 && !stable[a][b]) { stable[a][b] = true; q.add(new int[]{a, b}); }
                }
            }
            for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (g[i][j] == 1 && !stable[i][j]) { g[i][j] = 0; out[k + 1]++; }
        }
        return java.util.Arrays.copyOfRange(out, 1, out.length);
    }

    @Test
    void examplesAndSimulation() {
        assertArrayEquals(new int[]{2}, BricksFallingWhenHit.hitBricks(new int[][]{{1, 0, 0, 0}, {1, 1, 1, 0}}, new int[][]{{1, 0}}));
        assertArrayEquals(new int[]{0, 0}, BricksFallingWhenHit.hitBricks(new int[][]{{1, 0, 0, 0}, {1, 1, 0, 0}}, new int[][]{{1, 1}, {1, 0}}));
        Random r = new Random(803);
        for (int t = 0; t < 1500; t++) {
            int m = 1 + r.nextInt(5), n = 1 + r.nextInt(5);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(4) == 0 ? 0 : 1;
            int[][] hits = new int[1 + r.nextInt(m * n)][];
            java.util.Set<Integer> used = new java.util.HashSet<>();
            int c = 0;
            while (c < hits.length) {
                int cell = r.nextInt(m * n);
                if (used.add(cell)) hits[c++] = new int[]{cell / n, cell % n};
            }
            assertArrayEquals(simulate(g, hits), BricksFallingWhenHit.hitBricks(g, hits));
        }
    }
}
