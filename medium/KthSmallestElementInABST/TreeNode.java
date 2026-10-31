package KthSmallestElementInABST;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Binary tree node as used by LeetCode, plus helpers to build trees from LeetCode's level-order notation.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /** of(3, 9, 20, null, null, 15, 7) builds the tree LeetCode writes as [3,9,20,null,null,15,7]. */
    public static TreeNode of(Integer... values) {
        if (values.length == 0 || values[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.add(node.left);
            }
            i++;
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.add(node.right);
            }
            i++;
        }
        return root;
    }

    /** Back to LeetCode's level-order notation, without trailing nulls. */
    public static List<Integer> toList(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();   // LinkedList accepts null children
        queue.add(root);
        while (root != null && !queue.isEmpty()) {
            TreeNode n = queue.poll();
            if (n == null) {
                out.add(null);
                continue;
            }
            out.add(n.val);
            queue.add(n.left);
            queue.add(n.right);
        }
        while (!out.isEmpty() && out.getLast() == null) {
            out.removeLast();
        }
        return out;
    }
}
