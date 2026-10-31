package MaximumDepthOfBinaryTree;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/*
 * 104. Maximum Depth of Binary Tree — https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * Return the maximum depth of a binary tree: the number of nodes on the longest root-to-leaf path.
 * Input: [3,9,20,null,null,15,7] -> 3 · [1,null,2] -> 2
 * Explanation: README.md
 */
public class MaximumDepthOfBinaryTree {
    public static void main(String[] args) {
        System.out.println(maxDepth(TreeNode.of(3, 9, 20, null, null, 15, 7)));     // 3
        System.out.println(maxDepthBfs(TreeNode.of(1, null, 2)));                  // 2
    }

    /* Approach 1 - depth = 1 + deeper of the two subtrees. Time O(n), space O(h). */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /* Approach 2 - BFS: count the levels. Time O(n), space O(width). */
    public static int maxDepthBfs(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            depth++;
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return depth;
    }

    /* Approach 3 - iterative DFS carrying each node's depth on the stack. Time O(n), space O(h). */
    public static int maxDepthStack(TreeNode root) {
        if (root == null) return 0;
        Deque<Object[]> stack = new ArrayDeque<>();
        stack.push(new Object[]{root, 1});
        int best = 0;
        while (!stack.isEmpty()) {
            Object[] top = stack.pop();
            TreeNode node = (TreeNode) top[0];
            int depth = (int) top[1];
            best = Math.max(best, depth);
            if (node.left != null) stack.push(new Object[]{node.left, depth + 1});
            if (node.right != null) stack.push(new Object[]{node.right, depth + 1});
        }
        return best;
    }
}
