package BinaryTreePaths;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 257. Binary Tree Paths — https://leetcode.com/problems/binary-tree-paths/
 * Return all root-to-leaf paths of a binary tree as strings "1->2->5". Any order.
 * Input: [1,2,3,null,5] -> ["1->2->5","1->3"] · [1] -> ["1"]
 * Explanation: README.md
 */
public class BinaryTreePaths {
    public static void main(String[] args) {
        System.out.println(binaryTreePaths(TreeNode.of(1, 2, 3, null, 5)));      // [1->2->5, 1->3]
    }

    /* Approach 1 - DFS passing the path so far as an immutable string. Time O(n * h) for copying strings. */
    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> out = new ArrayList<>();
        if (root != null) dfs(root, "", out);
        return out;
    }

    private static void dfs(TreeNode node, String path, List<String> out) {
        String here = path + node.val;
        if (node.left == null && node.right == null) {
            out.add(here);
            return;
        }
        if (node.left != null) dfs(node.left, here + "->", out);
        if (node.right != null) dfs(node.right, here + "->", out);
    }

    /* Approach 2 - one shared StringBuilder; cut it back to its old length after each child (backtracking). */
    public static List<String> binaryTreePathsBacktrack(TreeNode root) {
        List<String> out = new ArrayList<>();
        backtrack(root, new StringBuilder(), out);
        return out;
    }

    private static void backtrack(TreeNode node, StringBuilder path, List<String> out) {
        if (node == null) return;
        int len = path.length();
        if (len > 0) path.append("->");
        path.append(node.val);
        if (node.left == null && node.right == null) {
            out.add(path.toString());
        } else {
            backtrack(node.left, path, out);
            backtrack(node.right, path, out);
        }
        path.setLength(len);
    }

    /* Approach 3 - BFS with a queue of (node, path) pairs. */
    public static List<String> binaryTreePathsBfs(TreeNode root) {
        List<String> out = new ArrayList<>();
        if (root == null) return out;
        record Item(TreeNode node, String path) {}
        Queue<Item> queue = new ArrayDeque<>();
        queue.add(new Item(root, String.valueOf(root.val)));
        while (!queue.isEmpty()) {
            Item it = queue.poll();
            if (it.node.left == null && it.node.right == null) out.add(it.path);
            if (it.node.left != null) queue.add(new Item(it.node.left, it.path + "->" + it.node.left.val));
            if (it.node.right != null) queue.add(new Item(it.node.right, it.path + "->" + it.node.right.val));
        }
        return out;
    }
}
