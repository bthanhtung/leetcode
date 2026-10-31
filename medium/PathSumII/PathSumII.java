package PathSumII;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 113. Path Sum II — https://leetcode.com/problems/path-sum-ii/
 * Return all root-to-leaf paths whose node values sum to targetSum (each path as a list of values).
 * Input: [5,4,8,11,null,13,4,7,2,null,null,5,1], 22 -> [[5,4,11,2],[5,8,4,5]]
 * Explanation: README.md
 */
public class PathSumII {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1);
        System.out.println(pathSum(root, 22));       // [[5, 4, 11, 2], [5, 8, 4, 5]]
    }

    /* Approach 1 - DFS with one shared path; add the node, recurse, remove it. Time O(n^2) worst (copying paths). */
    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> out = new ArrayList<>();
        dfs(root, targetSum, new ArrayList<>(), out);
        return out;
    }

    private static void dfs(TreeNode node, int remaining, List<Integer> path, List<List<Integer>> out) {
        if (node == null) return;
        path.add(node.val);
        remaining -= node.val;
        if (node.left == null && node.right == null && remaining == 0) {
            out.add(new ArrayList<>(path));
        } else {
            dfs(node.left, remaining, path, out);
            dfs(node.right, remaining, path, out);
        }
        path.removeLast();                               // backtrack
    }

    /* Approach 2 - BFS carrying (node, path, sum); simple but copies a path at every node. */
    public static List<List<Integer>> pathSumBfs(TreeNode root, int targetSum) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        record Item(TreeNode node, List<Integer> path, int sum) {}
        Queue<Item> queue = new ArrayDeque<>();
        queue.add(new Item(root, List.of(root.val), root.val));
        while (!queue.isEmpty()) {
            Item it = queue.poll();
            TreeNode n = it.node;
            if (n.left == null && n.right == null && it.sum == targetSum) out.add(it.path);
            for (TreeNode child : new TreeNode[]{n.left, n.right}) {
                if (child == null) continue;
                List<Integer> p = new ArrayList<>(it.path);
                p.add(child.val);
                queue.add(new Item(child, p, it.sum + child.val));
            }
        }
        return out;
    }
}
