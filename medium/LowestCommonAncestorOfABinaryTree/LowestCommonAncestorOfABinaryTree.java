package LowestCommonAncestorOfABinaryTree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * 236. Lowest Common Ancestor of a Binary Tree — https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * Given a binary tree (not a BST) and two of its nodes p and q, return their lowest common ancestor.
 * Input: [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1 -> 3 · p = 5, q = 4 -> 5
 * Explanation: README.md
 */
public class LowestCommonAncestorOfABinaryTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        System.out.println(lowestCommonAncestor(root, root.left, root.right).val);             // 3
        System.out.println(lowestCommonAncestorParents(root, root.left, root.left.right.right).val);   // 5
    }

    /*
     * Approach 1 - postorder: a call returns p or q if it finds one in its subtree (or the LCA if it finds both).
     * The first node where both sides return something is the LCA. Time O(n), space O(h).
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;         // p and q are on different sides
        return left != null ? left : right;
    }

    /* Approach 2 - record every node's parent, collect p's ancestors, walk up from q until one is shared. Time O(n). */
    public static TreeNode lowestCommonAncestorParents(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        parent.put(root, null);
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }
        Set<TreeNode> ancestors = new HashSet<>();
        for (TreeNode n = p; n != null; n = parent.get(n)) ancestors.add(n);
        TreeNode n = q;
        while (!ancestors.contains(n)) n = parent.get(n);
        return n;
    }
}
