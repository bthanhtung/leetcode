package BinaryTreeRightSideView;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 199. Binary Tree Right Side View — https://leetcode.com/problems/binary-tree-right-side-view/
 * Imagine standing on the right side of a binary tree: return the values you can see from top to bottom.
 * Input: [1,2,3,null,5,null,4] -> [1,3,4] · [1,2,3,4,null,null,null,5] -> [1,3,4,5] · [] -> []
 * Explanation: README.md
 */
public class BinaryTreeRightSideView {
    public static void main(String[] args) {
        System.out.println(rightSideView(TreeNode.of(1, 2, 3, null, 5, null, 4)));            // [1, 3, 4]
        System.out.println(rightSideViewDfs(TreeNode.of(1, 2, 3, 4, null, null, null, 5)));   // [1, 3, 4, 5]
    }

    /* Approach 1 - level-order BFS, keep the last node of every level. Time O(n). */
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (i == size - 1) out.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return out;
    }

    /* Approach 2 - DFS visiting right before left; the first node reached at each depth is the visible one. Space O(h). */
    public static List<Integer> rightSideViewDfs(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        dfs(root, 0, out);
        return out;
    }

    private static void dfs(TreeNode node, int depth, List<Integer> out) {
        if (node == null) return;
        if (depth == out.size()) out.add(node.val);
        dfs(node.right, depth + 1, out);
        dfs(node.left, depth + 1, out);
    }
}
