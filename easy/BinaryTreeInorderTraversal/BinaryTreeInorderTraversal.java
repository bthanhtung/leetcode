package BinaryTreeInorderTraversal;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * 94. Binary Tree Inorder Traversal — https://leetcode.com/problems/binary-tree-inorder-traversal/
 * Return the inorder traversal (left, node, right) of a binary tree's node values.
 * Input: [1,null,2,3] -> [1,3,2] · [] -> [] · [1] -> [1]
 * Explanation: README.md
 */
public class BinaryTreeInorderTraversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(1, null, 2, 3);
        System.out.println(inorderTraversal(root));            // [1, 3, 2]
        System.out.println(inorderTraversalMorris(root));      // [1, 3, 2]
    }

    /* Approach 1 - recursion. Time O(n), space O(h). */
    public static List<Integer> inorderTraversalRecursive(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        visit(root, out);
        return out;
    }

    private static void visit(TreeNode node, List<Integer> out) {
        if (node == null) return;
        visit(node.left, out);
        out.add(node.val);
        visit(node.right, out);
    }

    /* Approach 2 - explicit stack: go left as far as possible, pop, visit, then turn right. Time O(n), space O(h). */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            out.add(curr.val);
            curr = curr.right;
        }
        return out;
    }

    /*
     * Approach 3 - Morris traversal: temporarily link each node's inorder predecessor back to it, so we can climb back up
     * without a stack. The links are removed on the second visit. Time O(n), space O(1).
     */
    public static List<Integer> inorderTraversalMorris(TreeNode root) {
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
                    pred.right = curr;          // thread: come back to curr after the left subtree
                    curr = curr.left;
                } else {
                    pred.right = null;          // second visit: remove the thread
                    out.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        return out;
    }
}
