package CloneGraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * 133. Clone Graph — https://leetcode.com/problems/clone-graph/
 * Return a deep copy of a connected undirected graph given a reference to one node. Node values are 1..n and unique.
 * Input: adjList = [[2,4],[1,3],[2,4],[1,3]] -> a new graph with the same structure
 * Explanation: README.md
 */
public class CloneGraph {
    /** Graph node as used by LeetCode. */
    public static class Node {
        public int val;
        public List<Node> neighbors = new ArrayList<>();

        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Node start = fromAdjacency(new int[][]{{2, 4}, {1, 3}, {2, 4}, {1, 3}});
        Node copy = cloneGraph(start);
        System.out.println(copy != start && copy.val == 1 && copy.neighbors.size() == 2);   // true
    }

    /** Builds a graph from LeetCode's adjacency list (node i + 1 has neighbours adj[i]); returns node 1. */
    public static Node fromAdjacency(int[][] adj) {
        if (adj.length == 0) return null;
        Node[] nodes = new Node[adj.length + 1];
        for (int i = 1; i <= adj.length; i++) nodes[i] = new Node(i);
        for (int i = 1; i <= adj.length; i++) for (int j : adj[i - 1]) nodes[i].neighbors.add(nodes[j]);
        return nodes[1];
    }

    /* Approach 1 - DFS; the map original -> copy both prevents infinite loops on cycles and reuses copies. Time O(V + E). */
    public static Node cloneGraph(Node node) {
        return clone(node, new HashMap<>());
    }

    private static Node clone(Node node, Map<Node, Node> copies) {
        if (node == null) return null;
        Node existing = copies.get(node);
        if (existing != null) return existing;
        Node copy = new Node(node.val);
        copies.put(node, copy);                          // register before recursing: neighbours may point back here
        for (Node next : node.neighbors) copy.neighbors.add(clone(next, copies));
        return copy;
    }

    /* Approach 2 - BFS: create a copy when a node is first discovered, wire edges as each node is processed. */
    public static Node cloneGraphBfs(Node node) {
        if (node == null) return null;
        Map<Node, Node> copies = new HashMap<>();
        copies.put(node, new Node(node.val));
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.neighbors) {
                if (!copies.containsKey(next)) {
                    copies.put(next, new Node(next.val));
                    queue.add(next);
                }
                copies.get(cur).neighbors.add(copies.get(next));
            }
        }
        return copies.get(node);
    }
}
