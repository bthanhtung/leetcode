package SymmetricTree;

import com.tungdadev.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/*
 * 101. Symmetric Tree — https://leetcode.com/problems/symmetric-tree/
 * Return whether a binary tree is a mirror of itself (symmetric around its center).
 * Input: [1,2,2,3,4,4,3] -> true · [1,2,2,null,3,null,3] -> false
 * Explanation: README.md
 */
public class SymmetricTree {
    public static void main(String[] args) {
        System.out.println(isSymmetric(TreeNode.of(1, 2, 2, 3, 4, 4, 3)));                   // true
        System.out.println(isSymmetricIterative(TreeNode.of(1, 2, 2, null, 3, null, 3)));    // false
    }

    /* Approach 1 - two subtrees mirror each other if roots match, a.left mirrors b.right and a.right mirrors b.left. */
    public static boolean isSymmetric(TreeNode root) {
        return root == null || mirror(root.left, root.right);
    }

    private static boolean mirror(TreeNode a, TreeNode b) {
        if (a == null || b == null) return a == b;
        return a.val == b.val && mirror(a.left, b.right) && mirror(a.right, b.left);
    }

    /* Approach 2 - queue of node pairs that must be mirror images. Time O(n). */
    public static boolean isSymmetricIterative(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList<>();       // LinkedList allows null elements
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            TreeNode a = queue.poll(), b = queue.poll();
            if (a == null && b == null) continue;
            if (a == null || b == null || a.val != b.val) return false;
            queue.add(a.left);
            queue.add(b.right);
            queue.add(a.right);
            queue.add(b.left);
        }
        return true;
    }
}
