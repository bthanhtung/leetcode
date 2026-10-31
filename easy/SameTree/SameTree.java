package SameTree;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 100. Same Tree — https://leetcode.com/problems/same-tree/
 * Return true if two binary trees are structurally identical and their nodes have the same values.
 * Input: [1,2,3] & [1,2,3] -> true · [1,2] & [1,null,2] -> false · [1,2,1] & [1,1,2] -> false
 * Explanation: README.md
 */
public class SameTree {
    public static void main(String[] args) {
        System.out.println(isSameTree(TreeNode.of(1, 2, 3), TreeNode.of(1, 2, 3)));            // true
        System.out.println(isSameTreeIterative(TreeNode.of(1, 2), TreeNode.of(1, null, 2)));  // false
    }

    /* Approach 1 - same root value, same left subtree, same right subtree. Time O(n), space O(h). */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return p == q;                // both null -> true, only one null -> false
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /* Approach 2 - compare node pairs from a stack. Time O(n), space O(h). */
    public static boolean isSameTreeIterative(TreeNode p, TreeNode q) {
        Deque<TreeNode[]> stack = new ArrayDeque<>();
        stack.push(new TreeNode[]{p, q});
        while (!stack.isEmpty()) {
            TreeNode[] pair = stack.pop();
            TreeNode a = pair[0], b = pair[1];
            if (a == null && b == null) continue;
            if (a == null || b == null || a.val != b.val) return false;
            stack.push(new TreeNode[]{a.left, b.left});
            stack.push(new TreeNode[]{a.right, b.right});
        }
        return true;
    }
}
