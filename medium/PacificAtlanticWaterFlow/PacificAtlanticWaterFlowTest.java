package PacificAtlanticWaterFlow;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacificAtlanticWaterFlowTest {

    /** Forward check: from (i, j), flow downhill and see which oceans are touched. */
    private static List<List<Integer>> brute(int[][] h) {
        int m = h.length, n = h[0].length;
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean pac = false, atl = false;
                boolean[][] seen = new boolean[m][n];
                Queue<int[]> q = new ArrayDeque<>();
                q.add(new int[]{i, j});
                seen[i][j] = true;
                while (!q.isEmpty()) {
                    int[] c = q.poll();
                    if (c[0] == 0 || c[1] == 0) pac = true;
                    if (c[0] == m - 1 || c[1] == n - 1) atl = true;
                    for (int[] d : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                        int r = c[0] + d[0], col = c[1] + d[1];
                        if (r >= 0 && r < m && col >= 0 && col < n && !seen[r][col] && h[r][col] <= h[c[0]][c[1]]) {
                            seen[r][col] = true;
                            q.add(new int[]{r, col});
                        }
                    }
                }
                if (pac && atl) out.add(List.of(i, j));
            }
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        int[][] h = {{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}};
        assertEquals(List.of(List.of(0, 4), List.of(1, 3), List.of(1, 4), List.of(2, 2), List.of(3, 0), List.of(3, 1), List.of(4, 0)),
                PacificAtlanticWaterFlow.pacificAtlantic(h));
        Random r = new Random(417);
        for (int t = 0; t < 300; t++) {
            int m = 1 + r.nextInt(6), n = 1 + r.nextInt(6);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(5);
            List<List<Integer>> expected = brute(g);
            assertEquals(expected, PacificAtlanticWaterFlow.pacificAtlantic(g));
            assertEquals(expected, PacificAtlanticWaterFlow.pacificAtlanticBfs(g));
        }
    }
}
