package RedundantConnection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RedundantConnectionTest {

    @Test
    void examplesAndRandomTreesPlusOneEdge() {
        assertArrayEquals(new int[]{2, 3}, RedundantConnection.findRedundantConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}}));
        assertArrayEquals(new int[]{1, 4}, RedundantConnection.findRedundantConnection(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}}));
        Random r = new Random(684);
        for (int t = 0; t < 500; t++) {
            int n = 3 + r.nextInt(12);
            List<int[]> edges = new ArrayList<>();
            for (int v = 2; v <= n; v++) edges.add(new int[]{1 + r.nextInt(v - 1), v});      // random tree
            java.util.Set<Long> existing = new java.util.HashSet<>();
            for (int[] e : edges) existing.add((long) e[0] * 100 + e[1]);
            int a, b;
            do {
                a = 1 + r.nextInt(n);
                b = 1 + r.nextInt(n);
            } while (a == b || existing.contains((long) Math.min(a, b) * 100 + Math.max(a, b)));
            edges.add(new int[]{Math.min(a, b), Math.max(a, b)});
            Collections.shuffle(edges, r);
            int[][] arr = edges.toArray(new int[0][]);
            assertArrayEquals(RedundantConnection.findRedundantConnectionDfs(arr), RedundantConnection.findRedundantConnection(arr));
        }
    }
}
