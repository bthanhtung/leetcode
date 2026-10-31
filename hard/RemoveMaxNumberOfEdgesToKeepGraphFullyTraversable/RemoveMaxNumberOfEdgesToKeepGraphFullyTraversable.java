package RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable;

/*
 * 1579. Remove Max Number of Edges to Keep Graph Fully Traversable — https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/
 * Edge types: 1 = only Alice can use, 2 = only Bob, 3 = both. Remove as many edges as possible while both Alice and
 * Bob can still reach every node. Return the number removed, or -1 if they cannot traverse the graph even with all edges.
 * Input: n = 4, edges = [[3,1,2],[3,2,3],[1,1,3],[1,2,4],[1,1,2],[2,3,4]] -> 2 · n = 4, [[3,2,3],[1,1,2],[2,3,4]] -> -1
 * Explanation: README.md
 */
public class RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable {
    public static void main(String[] args) {
        int[][] edges = {{3, 1, 2}, {3, 2, 3}, {1, 1, 3}, {1, 2, 4}, {1, 1, 2}, {2, 3, 4}};
        System.out.println(maxNumEdgesToRemove(4, edges));       // 2
    }

    /** Union-find that also counts its components. */
    static final class DSU {
        final int[] parent;
        int components;

        DSU(int n) {
            parent = new int[n + 1];
            for (int i = 0; i <= n; i++) parent[i] = i;
            components = n;
        }

        int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            parent[ra] = rb;
            components--;
            return true;
        }
    }

    /*
     * Greedy - shared (type 3) edges are worth two personal edges, so add them first to both Alice's and Bob's
     * union-find; then personal edges. Every edge that joins nothing new is removable. Time ~O(E α(n)).
     */
    public static int maxNumEdgesToRemove(int n, int[][] edges) {
        DSU alice = new DSU(n), bob = new DSU(n);
        int used = 0;
        for (int[] e : edges) {
            if (e[0] == 3) {
                boolean a = alice.union(e[1], e[2]);
                boolean b = bob.union(e[1], e[2]);
                if (a || b) used++;                     // in practice a == b: both forests receive the same type-3 edges
            }
        }
        for (int[] e : edges) {
            if (e[0] == 1 && alice.union(e[1], e[2])) used++;
            if (e[0] == 2 && bob.union(e[1], e[2])) used++;
        }
        if (alice.components != 1 || bob.components != 1) return -1;
        return edges.length - used;
    }

    /** Reference for tests: try every subset of edges to keep (tiny inputs only). */
    static int bruteForce(int n, int[][] edges) {
        int best = -1, m = edges.length;
        for (int mask = 0; mask < 1 << m; mask++) {
            DSU a = new DSU(n), b = new DSU(n);
            for (int i = 0; i < m; i++) {
                if ((mask >> i & 1) == 0) continue;
                if (edges[i][0] != 2) a.union(edges[i][1], edges[i][2]);
                if (edges[i][0] != 1) b.union(edges[i][1], edges[i][2]);
            }
            if (a.components == 1 && b.components == 1) best = Math.max(best, m - Integer.bitCount(mask));
        }
        return best;
    }
}
