package BinaryTreePostorderTraversal;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/*
 * 145. Binary Tree Postorder Traversal — https://leetcode.com/problems/binary-tree-postorder-traversal/
 * Return the postorder traversal (left, right, node) of a binary tree's node values.
 * Input: [1,null,2,3] -> [3,2,1] · [1,2,3,4,5,null,8,null,null,6,7,9] -> [4,6,7,5,2,9,8,3,1]
 * Explanation: README.md
 */
public class BinaryTreePostorderTraversal {
    public static void main(String[] args) {
        System.out.println(postorderTraversal(TreeNode.of(1, null, 2, 3)));            // [3, 2, 1]
        System.out.println(postorderTraversalReverse(TreeNode.of(1, null, 2, 3)));     // [3, 2, 1]
    }

    /* Approach 1 - recursion. Time O(n), space O(h). */
    public static List<Integer> postorderTraversalRecursive(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        visit(root, out);
        return out;
    }

    private static void visit(TreeNode node, List<Integer> out) {
        if (node == null) return;
        visit(node.left, out);
        visit(node.right, out);
        out.add(node.val);
    }

    /* Approach 2 - preorder with right before left gives (node, right, left); reversed that is (left, right, node). */
    public static List<Integer> postorderTraversalReverse(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            out.add(node.val);
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        Collections.reverse(out);
        return out;
    }

    /*
     * Approach 3 - true iterative postorder with one stack: a node is emitted only after its right subtree is done,
     * which we know when the last emitted node is its right child. Time O(n), space O(h).
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root, lastVisited = null;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            TreeNode top = stack.peek();
            if (top.right != null && top.right != lastVisited) {
                curr = top.right;                  // right subtree not done yet
            } else {
                out.add(top.val);
                lastVisited = stack.pop();
            }
        }
        return out;
    }
}
