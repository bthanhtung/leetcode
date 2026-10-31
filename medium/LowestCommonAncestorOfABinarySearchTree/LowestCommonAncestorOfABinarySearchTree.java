package LowestCommonAncestorOfABinarySearchTree;

import com.tungdadev.common.TreeNode;

/*
 * 235. Lowest Common Ancestor of a Binary Search Tree — https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 * Given a BST and two of its nodes p and q, return their lowest common ancestor (a node may be its own ancestor).
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8 -> 6 · p = 2, q = 4 -> 2
 * Explanation: README.md
 */
public class LowestCommonAncestorOfABinarySearchTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        System.out.println(lowestCommonAncestor(root, root.left, root.right).val);        // 6
        System.out.println(lowestCommonAncestorRecursive(root, root.left, root.left.right).val);   // 2
    }

    /* Approach 1 - both smaller: go left; both larger: go right; otherwise they split here. Time O(h), space O(h). */
    public static TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val) return lowestCommonAncestorRecursive(root.left, p, q);
        if (p.val > root.val && q.val > root.val) return lowestCommonAncestorRecursive(root.right, p, q);
        return root;
    }

    /* Approach 2 - the same walk with a loop. Time O(h), space O(1). */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        while (true) {
            if (p.val < node.val && q.val < node.val) node = node.left;
            else if (p.val > node.val && q.val > node.val) node = node.right;
            else return node;
        }
    }
}
