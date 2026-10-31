package BalancedBinaryTree;

import com.tungdadev.common.TreeNode;

/*
 * 110. Balanced Binary Tree — https://leetcode.com/problems/balanced-binary-tree/
 * A binary tree is height-balanced if the two subtrees of every node differ in height by at most one.
 * Input: [3,9,20,null,null,15,7] -> true · [1,2,2,3,3,null,null,4,4] -> false · [] -> true
 * Explanation: README.md
 */
public class BalancedBinaryTree {
    public static void main(String[] args) {
        System.out.println(isBalanced(TreeNode.of(3, 9, 20, null, null, 15, 7)));            // true
        System.out.println(isBalancedTopDown(TreeNode.of(1, 2, 2, 3, 3, null, null, 4, 4))); // false
    }

    /* Approach 1 - check each node's subtree heights, recursing into both subtrees. Time O(n log n) balanced, O(n^2) worst. */
    public static boolean isBalancedTopDown(TreeNode root) {
        if (root == null) return true;
        return Math.abs(height(root.left) - height(root.right)) <= 1
                && isBalancedTopDown(root.left) && isBalancedTopDown(root.right);
    }

    private static int height(TreeNode node) {
        return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
    }

    /* Approach 2 - one postorder pass returning the height, or -1 as soon as any subtree is unbalanced. Time O(n). */
    public static boolean isBalanced(TreeNode root) {
        return check(root) != -1;
    }

    private static int check(TreeNode node) {
        if (node == null) return 0;
        int left = check(node.left);
        if (left == -1) return -1;
        int right = check(node.right);
        if (right == -1 || Math.abs(left - right) > 1) return -1;
        return 1 + Math.max(left, right);
    }
}
