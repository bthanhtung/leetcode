package RedundantConnectionII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RedundantConnectionIITest {

    private static boolean isRootedTree(int n, List<int[]> edges) {
        int[] indeg = new int[n + 1];
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i <= n; i++) next.add(new ArrayList<>());
        for (int[] e : edges) { indeg[e[1]]++; next.get(e[0]).add(e[1]); }
        int root = -1;
        for (int v = 1; v <= n; v++) {
            if (indeg[v] > 1) return false;
            if (indeg[v] == 0) { if (root != -1) return false; root = v; }
        }
        if (root == -1) return false;
        boolean[] seen = new boolean[n + 1];
        java.util.ArrayDeque<Integer> q = new java.util.ArrayDeque<>(List.of(root));
        seen[root] = true;
        int count = 1;
        while (!q.isEmpty()) for (int w : next.get(q.poll())) if (!seen[w]) { seen[w] = true; count++; q.add(w); }
        return count == n;
    }

    private static int[] brute(int[][] edges) {
        for (int k = edges.length - 1; k >= 0; k--) {
            List<int[]> rest = new ArrayList<>();
            for (int i = 0; i < edges.length; i++) if (i != k) rest.add(edges[i]);
            if (isRootedTree(edges.length, rest)) return edges[k];
        }
        throw new AssertionError();
    }

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{2, 3}, RedundantConnectionII.findRedundantDirectedConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}}));
        assertArrayEquals(new int[]{4, 1}, RedundantConnectionII.findRedundantDirectedConnection(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 1}, {1, 5}}));
        assertArrayEquals(new int[]{2, 1}, RedundantConnectionII.findRedundantDirectedConnection(new int[][]{{2, 1}, {3, 1}, {4, 2}, {1, 4}}));
        Random r = new Random(685);
        for (int t = 0; t < 3000; t++) {
            int n = 3 + r.nextInt(6);
            List<Integer> perm = new ArrayList<>();
            for (int v = 1; v <= n; v++) perm.add(v);
            Collections.shuffle(perm, r);
            List<int[]> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new int[]{perm.get(r.nextInt(i)), perm.get(i)});
            int u, v;
            do { u = 1 + r.nextInt(n); v = 1 + r.nextInt(n); } while (u == v || hasEdge(edges, u, v));
            edges.add(new int[]{u, v});
            Collections.shuffle(edges, r);
            int[][] arr = edges.toArray(new int[0][]);
            assertArrayEquals(brute(arr), RedundantConnectionII.findRedundantDirectedConnection(arr));
        }
    }

    private static boolean hasEdge(List<int[]> edges, int u, int v) {
        for (int[] e : edges) if (e[0] == u && e[1] == v) return true;
        return false;
    }
}
