package BinaryTreeZigzagLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * 103. Binary Tree Zigzag Level Order Traversal — https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 * Return the zigzag level order traversal: left to right, then right to left for the next level, alternating.
 * Input: [3,9,20,null,null,15,7] -> [[3],[20,9],[15,7]]
 * Explanation: README.md
 */
public class BinaryTreeZigzagLevelOrderTraversal {
    public static void main(String[] args) {
        System.out.println(zigzagLevelOrder(TreeNode.of(3, 9, 20, null, null, 15, 7)));      // [[3], [20, 9], [15, 7]]
    }

    /* Approach 1 - normal BFS by levels, reverse every other level. Time O(n). */
    public static List<List<Integer>> zigzagLevelOrderReverse(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int size = queue.size(); size > 0; size--) {
                TreeNode n = queue.poll();
                level.add(n.val);
                if (n.left != null) queue.add(n.left);
                if (n.right != null) queue.add(n.right);
            }
            if (out.size() % 2 == 1) Collections.reverse(level);
            out.add(level);
        }
        return out;
    }

    /* Approach 2 - BFS, but build each level in a deque: add at the back or at the front depending on direction. */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        boolean leftToRight = true;
        while (!queue.isEmpty()) {
            Deque<Integer> level = new ArrayDeque<>();
            for (int size = queue.size(); size > 0; size--) {
                TreeNode n = queue.poll();
                if (leftToRight) level.addLast(n.val);
                else level.addFirst(n.val);
                if (n.left != null) queue.add(n.left);
                if (n.right != null) queue.add(n.right);
            }
            out.add(new ArrayList<>(level));
            leftToRight = !leftToRight;
        }
        return out;
    }

    /* Approach 3 - DFS with depth; even depths append, odd depths insert at the front. */
    public static List<List<Integer>> zigzagLevelOrderDfs(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        dfs(root, 0, out);
        return out;
    }

    private static void dfs(TreeNode n, int depth, List<List<Integer>> out) {
        if (n == null) return;
        if (depth == out.size()) out.add(new LinkedList<>());
        if (depth % 2 == 0) out.get(depth).add(n.val);
        else out.get(depth).addFirst(n.val);
        dfs(n.left, depth + 1, out);
        dfs(n.right, depth + 1, out);
    }
}
