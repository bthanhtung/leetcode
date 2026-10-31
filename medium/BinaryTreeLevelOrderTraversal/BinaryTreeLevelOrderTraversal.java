package BinaryTreeLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 102. Binary Tree Level Order Traversal — https://leetcode.com/problems/binary-tree-level-order-traversal/
 * Return the level order traversal of a binary tree's values: left to right, level by level.
 * Input: [3,9,20,null,null,15,7] -> [[3],[9,20],[15,7]] · [1] -> [[1]] · [] -> []
 * Explanation: README.md
 */
public class BinaryTreeLevelOrderTraversal {
    public static void main(String[] args) {
        System.out.println(levelOrder(TreeNode.of(3, 9, 20, null, null, 15, 7)));        // [[3], [9, 20], [15, 7]]
        System.out.println(levelOrderDfs(TreeNode.of(3, 9, 20, null, null, 15, 7)));
    }

    /* Approach 1 - BFS; the queue size at the start of a round is exactly the size of the current level. Time O(n). */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            out.add(level);
        }
        return out;
    }

    /* Approach 2 - preorder DFS carrying the depth; append the value to the list of its depth. Time O(n), space O(h). */
    public static List<List<Integer>> levelOrderDfs(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        dfs(root, 0, out);
        return out;
    }

    private static void dfs(TreeNode node, int depth, List<List<Integer>> out) {
        if (node == null) return;
        if (depth == out.size()) out.add(new ArrayList<>());       // first time we reach this depth
        out.get(depth).add(node.val);
        dfs(node.left, depth + 1, out);                             // left before right keeps left-to-right order
        dfs(node.right, depth + 1, out);
    }
}
