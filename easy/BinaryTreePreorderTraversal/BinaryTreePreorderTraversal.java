package BinaryTreePreorderTraversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * 144. Binary Tree Preorder Traversal — https://leetcode.com/problems/binary-tree-preorder-traversal/
 * Return the preorder traversal (node, left, right) of a binary tree's node values.
 * Input: [1,null,2,3] -> [1,2,3] · [1,2,3,4,5,null,8,null,null,6,7,9] -> [1,2,4,5,6,7,3,8,9]
 * Explanation: README.md
 */
public class BinaryTreePreorderTraversal {
    public static void main(String[] args) {
        System.out.println(preorderTraversal(TreeNode.of(1, null, 2, 3)));         // [1, 2, 3]
    }

    /* Approach 1 - recursion. Time O(n), space O(h). */
    public static List<Integer> preorderTraversalRecursive(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        visit(root, out);
        return out;
    }

    private static void visit(TreeNode node, List<Integer> out) {
        if (node == null) return;
        out.add(node.val);
        visit(node.left, out);
        visit(node.right, out);
    }

    /* Approach 2 - stack: pop, visit, push right then left (so left comes out first). Time O(n), space O(h). */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            out.add(node.val);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        return out;
    }

    /* Approach 3 - Morris: like the inorder version, but a node is visited when its thread is created. Space O(1). */
    public static List<Integer> preorderTraversalMorris(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                out.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) pred = pred.right;
                if (pred.right == null) {
                    out.add(curr.val);          // first time here: visit before going left
                    pred.right = curr;
                    curr = curr.left;
                } else {
                    pred.right = null;
                    curr = curr.right;
                }
            }
        }
        return out;
    }
}
