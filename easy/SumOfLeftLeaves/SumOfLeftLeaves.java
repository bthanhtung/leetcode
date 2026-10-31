package SumOfLeftLeaves;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 404. Sum of Left Leaves — https://leetcode.com/problems/sum-of-left-leaves/
 * Return the sum of all left leaves (leaves that are the left child of their parent).
 * Input: [3,9,20,null,null,15,7] -> 24 (9 + 15) · [1] -> 0
 * Explanation: README.md
 */
public class SumOfLeftLeaves {
    public static void main(String[] args) {
        System.out.println(sumOfLeftLeaves(TreeNode.of(3, 9, 20, null, null, 15, 7)));         // 24
    }

    /* Approach 1 - recursion that knows whether the current node is a left child. Time O(n), space O(h). */
    public static int sumOfLeftLeaves(TreeNode root) {
        return sum(root, false);
    }

    private static int sum(TreeNode node, boolean isLeft) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return isLeft ? node.val : 0;
        return sum(node.left, true) + sum(node.right, false);
    }

    /* Approach 2 - iterative: when visiting a node, check whether its left child is a leaf. Time O(n). */
    public static int sumOfLeftLeavesIterative(TreeNode root) {
        int total = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                if (node.left.left == null && node.left.right == null) total += node.left.val;
                else stack.push(node.left);
            }
            if (node.right != null) stack.push(node.right);
        }
        return total;
    }
}
