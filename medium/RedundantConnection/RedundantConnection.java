package RedundantConnection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 684. Redundant Connection — https://leetcode.com/problems/redundant-connection/
 * A tree of n nodes (1..n) got one extra edge. Return an edge that can be removed so the graph is a tree again;
 * if several work, return the one that appears last in the input.
 * Input: [[1,2],[1,3],[2,3]] -> [2,3] · [[1,2],[2,3],[3,4],[1,4],[1,5]] -> [1,4]
 * Explanation: README.md
 */
public class RedundantConnection {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(findRedundantConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}})));             // [2, 3]
        System.out.println(java.util.Arrays.toString(findRedundantConnectionDfs(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}}))); // [1, 4]
    }

    /* Approach 1 - before adding (u, v), DFS: if v is already reachable from u, this edge closes a cycle. Time O(n^2). */
    public static int[] findRedundantConnectionDfs(int[][] edges) {
        int n = edges.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] e : edges) {
            if (reachable(graph, e[0], e[1], new HashSet<>())) return e;
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        throw new IllegalArgumentException("no redundant edge");
    }

    private static boolean reachable(List<List<Integer>> graph, int from, int to, Set<Integer> seen) {
        if (from == to) return true;
        seen.add(from);
        for (int next : graph.get(from)) {
            if (!seen.contains(next) && reachable(graph, next, to, seen)) return true;
        }
        return false;
    }

    /* Approach 2 - union-find: an edge whose ends are already in the same set is the redundant one. Time ~O(n α(n)). */
    public static int[] findRedundantConnection(int[][] edges) {
        int[] parent = new int[edges.length + 1], rank = new int[edges.length + 1];
        for (int i = 0; i < parent.length; i++) parent[i] = i;
        for (int[] e : edges) {
            int a = find(parent, e[0]), b = find(parent, e[1]);
            if (a == b) return e;
            if (rank[a] < rank[b]) {
                int t = a;
                a = b;
                b = t;
            }
            parent[b] = a;                                  // union by rank keeps the trees shallow
            if (rank[a] == rank[b]) rank[a]++;
        }
        throw new IllegalArgumentException("no redundant edge");
    }

    private static int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);      // path compression
        return parent[x];
    }
}
