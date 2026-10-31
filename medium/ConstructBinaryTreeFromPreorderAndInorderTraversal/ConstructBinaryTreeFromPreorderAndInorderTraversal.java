package ConstructBinaryTreeFromPreorderAndInorderTraversal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/*
 * 105. Construct Binary Tree from Preorder and Inorder Traversal — https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * Build the binary tree from its preorder and inorder traversals (values are unique).
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7] -> [3,9,20,null,null,15,7]
 * Explanation: README.md
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public static void main(String[] args) {
        System.out.println(TreeNode.toList(buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7})));
        // [3, 9, 20, null, null, 15, 7]
    }

    /*
     * Approach 1 - preorder[0] is the root; its position in inorder splits left and right subtrees. A map from value to
     * inorder index makes each split O(1). Time O(n), space O(n).
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> where = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) where.put(inorder[i], i);
        return build(preorder, new int[]{0}, 0, inorder.length - 1, where);
    }

    private static TreeNode build(int[] preorder, int[] next, int lo, int hi, Map<Integer, Integer> where) {
        if (lo > hi) return null;
        TreeNode root = new TreeNode(preorder[next[0]++]);       // preorder hands out roots in the order we need them
        int mid = where.get(root.val);
        root.left = build(preorder, next, lo, mid - 1, where);    // left subtree must be built first
        root.right = build(preorder, next, mid + 1, hi, where);
        return root;
    }

    /*
     * Approach 2 - iterative: walk preorder pushing nodes as left children until the stack top equals the next inorder
     * value; then pop while they match - the last popped node gets the next preorder value as its right child.
     */
    public static TreeNode buildTreeIterative(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        int in = 0;
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = new TreeNode(preorder[i]);
            TreeNode parent = null;
            while (!stack.isEmpty() && stack.peek().val == inorder[in]) {
                parent = stack.pop();
                in++;
            }
            if (parent != null) parent.right = node;
            else stack.peek().left = node;
            stack.push(node);
        }
        return root;
    }
}
