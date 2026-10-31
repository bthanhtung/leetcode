package CloneGraph;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class CloneGraphTest {

    /** Adjacency lists by value plus the set of node objects, to check structure and that no node is shared. */
    private static Map<Integer, List<Integer>> describe(CloneGraph.Node start, Set<CloneGraph.Node> objects) {
        Map<Integer, List<Integer>> adj = new java.util.TreeMap<>();
        Queue<CloneGraph.Node> queue = new ArrayDeque<>();
        Map<CloneGraph.Node, Boolean> seen = new IdentityHashMap<>();
        queue.add(start);
        seen.put(start, true);
        while (!queue.isEmpty()) {
            CloneGraph.Node n = queue.poll();
            objects.add(n);
            List<Integer> list = new ArrayList<>();
            for (CloneGraph.Node m : n.neighbors) {
                list.add(m.val);
                if (seen.put(m, true) == null) queue.add(m);
            }
            adj.put(n.val, list);
        }
        return adj;
    }

    @Test
    void randomGraphs() {
        assertNull(CloneGraph.cloneGraph(null));
        Random r = new Random(133);
        List<UnaryOperator<CloneGraph.Node>> approaches = List.of(CloneGraph::cloneGraph, CloneGraph::cloneGraphBfs);
        for (int t = 0; t < 300; t++) {
            int n = 1 + r.nextInt(10);
            List<Set<Integer>> adj = new ArrayList<>();
            for (int i = 0; i <= n; i++) adj.add(new HashSet<>());
            for (int v = 2; v <= n; v++) {                     // spanning tree keeps it connected
                int u = 1 + r.nextInt(v - 1);
                adj.get(u).add(v);
                adj.get(v).add(u);
            }
            for (int e = r.nextInt(n); e > 0; e--) {
                int a = 1 + r.nextInt(n), b = 1 + r.nextInt(n);
                if (a != b) { adj.get(a).add(b); adj.get(b).add(a); }
            }
            int[][] lists = new int[n][];
            for (int i = 1; i <= n; i++) lists[i - 1] = adj.get(i).stream().mapToInt(Integer::intValue).toArray();
            CloneGraph.Node original = CloneGraph.fromAdjacency(lists);
            Set<CloneGraph.Node> originalObjects = java.util.Collections.newSetFromMap(new IdentityHashMap<>());
            Map<Integer, List<Integer>> expected = describe(original, originalObjects);
            for (UnaryOperator<CloneGraph.Node> f : approaches) {
                Set<CloneGraph.Node> copyObjects = java.util.Collections.newSetFromMap(new IdentityHashMap<>());
                assertEquals(expected, describe(f.apply(original), copyObjects));
                for (CloneGraph.Node c : copyObjects) assertFalse(originalObjects.contains(c), "copy shares a node");
            }
        }
    }
}
