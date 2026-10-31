package InvertBinaryTree;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 226. Invert Binary Tree — https://leetcode.com/problems/invert-binary-tree/
 * Invert a binary tree (mirror it: every left child becomes the right child) and return its root.
 * Input: [4,2,7,1,3,6,9] -> [4,7,2,9,6,3,1] · [2,1,3] -> [2,3,1]
 * Explanation: README.md
 */
public class InvertBinaryTree {
    public static void main(String[] args) {
        System.out.println(TreeNode.toList(invertTree(TreeNode.of(4, 2, 7, 1, 3, 6, 9))));      // [4, 7, 2, 9, 6, 3, 1]
        System.out.println(TreeNode.toList(invertTreeBfs(TreeNode.of(2, 1, 3))));               // [2, 3, 1]
    }

    /* Approach 1 - recursion: invert both subtrees, then swap them. Time O(n), space O(h). */
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        root.left = invertTree(root.right);
        root.right = left;
        return root;
    }

    /* Approach 2 - level by level with a queue, swapping the children of each node. Time O(n), space O(width). */
    public static TreeNode invertTreeBfs(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        return root;
    }
}
