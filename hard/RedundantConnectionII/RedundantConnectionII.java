package RedundantConnectionII;

import java.util.Arrays;

/*
 * 685. Redundant Connection II — https://leetcode.com/problems/redundant-connection-ii/
 * A rooted tree on nodes 1..n got one extra directed edge. Return an edge whose removal leaves a rooted tree;
 * if several answers exist, the one that occurs last in the input.
 * Input: [[1,2],[1,3],[2,3]] -> [2,3] · [[1,2],[2,3],[3,4],[4,1],[1,5]] -> [4,1]
 * Explanation: README.md
 */
public class RedundantConnectionII {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findRedundantDirectedConnection(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 1}, {1, 5}})));
    }

    /*
     * The extra edge creates either a node with two parents, a cycle, or both.
     * 1. Look for a node with two incoming edges: cand1 (earlier) and cand2 (later).
     * 2. Union-find over all edges except cand2. If a cycle still appears: the answer is cand1 when it exists
     *    (cand2 was innocent), otherwise the edge that closed the cycle. If no cycle: cand2.
     * Time O(n * alpha).
     */
    public static int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] parentOf = new int[n + 1];
        int[] cand1 = null, cand2 = null;
        for (int[] e : edges) {
            if (parentOf[e[1]] != 0) {
                cand1 = new int[]{parentOf[e[1]], e[1]};
                cand2 = e;
            } else {
                parentOf[e[1]] = e[0];
            }
        }
        int[] uf = new int[n + 1];
        for (int i = 0; i <= n; i++) uf[i] = i;
        for (int[] e : edges) {
            if (e == cand2) continue;
            int a = find(uf, e[0]), b = find(uf, e[1]);
            if (a == b) return cand1 != null ? cand1 : e;      // cycle
            uf[b] = a;
        }
        return cand2;
    }

    private static int find(int[] uf, int x) {
        while (uf[x] != x) x = uf[x] = uf[uf[x]];
        return x;
    }
}
