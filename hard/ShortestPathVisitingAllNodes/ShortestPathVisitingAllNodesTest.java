package ShortestPathVisitingAllNodes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortestPathVisitingAllNodesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, ShortestPathVisitingAllNodes.shortestPathLength(new int[][]{{1, 2, 3}, {0}, {0}, {0}}));
        assertEquals(4, ShortestPathVisitingAllNodes.shortestPathLength(new int[][]{{1}, {0, 2, 4}, {1, 3, 4}, {2}, {1, 2}}));
        assertEquals(0, ShortestPathVisitingAllNodes.shortestPathLength(new int[][]{{}}));
        Random r = new Random(847);
        for (int t = 0; t < 200; t++) {
            int n = 1 + r.nextInt(8);
            List<Set<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new HashSet<>());
            for (int v = 1; v < n; v++) {
                int u = r.nextInt(v);
                adj.get(u).add(v);
                adj.get(v).add(u);
            }
            for (int e = r.nextInt(n); e > 0; e--) {
                int a = r.nextInt(n), b = r.nextInt(n);
                if (a != b) { adj.get(a).add(b); adj.get(b).add(a); }
            }
            int[][] graph = new int[n][];
            for (int i = 0; i < n; i++) graph[i] = adj.get(i).stream().mapToInt(Integer::intValue).toArray();
            assertEquals(ShortestPathVisitingAllNodes.shortestPathLengthDp(graph), ShortestPathVisitingAllNodes.shortestPathLength(graph));
        }
    }
}
