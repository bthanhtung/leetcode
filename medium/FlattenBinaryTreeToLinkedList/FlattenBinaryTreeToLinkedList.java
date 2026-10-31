package FlattenBinaryTreeToLinkedList;

import java.util.ArrayList;
import java.util.List;

/*
 * 114. Flatten Binary Tree to Linked List — https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
 * Flatten a binary tree into a "linked list" in place: use right pointers as next, left pointers all null, nodes in
 * preorder. Follow up: O(1) extra space.
 * Input: [1,2,5,3,4,null,6] -> [1,null,2,null,3,null,4,null,5,null,6]
 * Explanation: README.md
 */
public class FlattenBinaryTreeToLinkedList {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(1, 2, 5, 3, 4, null, 6);
        flatten(root);
        System.out.println(TreeNode.toList(root));     // [1, null, 2, null, 3, null, 4, null, 5, null, 6]
    }

    /* Approach 1 - collect nodes in preorder, then relink them. Time O(n), space O(n). */
    public static void flattenList(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        preorder(root, nodes);
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).left = null;
            nodes.get(i).right = i + 1 < nodes.size() ? nodes.get(i + 1) : null;
        }
    }

    private static void preorder(TreeNode n, List<TreeNode> out) {
        if (n == null) return;
        out.add(n);
        preorder(n.left, out);
        preorder(n.right, out);
    }

    /* Approach 2 - visit in reverse preorder (right, left, node) and prepend each node to the list built so far. */
    public static void flattenReverse(TreeNode root) {
        reverse(root, new TreeNode[1]);
    }

    private static void reverse(TreeNode node, TreeNode[] prev) {
        if (node == null) return;
        reverse(node.right, prev);
        reverse(node.left, prev);
        node.right = prev[0];
        node.left = null;
        prev[0] = node;
    }

    /*
     * Approach 3 - in place, O(1) space: for each node with a left subtree, hang the right subtree after the rightmost
     * node of the left subtree, move the left subtree to the right, and continue down the right.
     */
    public static void flatten(TreeNode root) {
        for (TreeNode curr = root; curr != null; curr = curr.right) {
            if (curr.left != null) {
                TreeNode pred = curr.left;
                while (pred.right != null) pred = pred.right;   // last node of the left subtree in preorder
                pred.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
        }
    }
}
