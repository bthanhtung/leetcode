package RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveMaxNumberOfEdgesToKeepGraphFullyTraversableTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable.maxNumEdgesToRemove(4, new int[][]{{3, 1, 2}, {3, 2, 3}, {1, 1, 3}, {1, 2, 4}, {1, 1, 2}, {2, 3, 4}}));
        assertEquals(0, RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable.maxNumEdgesToRemove(4, new int[][]{{3, 1, 2}, {3, 2, 3}, {1, 1, 4}, {2, 1, 4}}));
        assertEquals(-1, RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable.maxNumEdgesToRemove(4, new int[][]{{3, 2, 3}, {1, 1, 2}, {2, 3, 4}}));
        Random r = new Random(1579);
        for (int t = 0; t < 500; t++) {
            int n = 1 + r.nextInt(5), m = r.nextInt(10);
            int[][] edges = new int[m][];
            for (int i = 0; i < m; i++) {
                int a = 1 + r.nextInt(n), b = 1 + r.nextInt(n);
                if (a == b) b = a % n + 1;
                edges[i] = new int[]{1 + r.nextInt(3), a, b};
            }
            if (n == 1) edges = new int[0][];
            assertEquals(RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable.bruteForce(n, edges), RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable.maxNumEdgesToRemove(n, edges));
        }
    }
}
