package BinarySearchTreeIterator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * 173. Binary Search Tree Iterator — https://leetcode.com/problems/binary-search-tree-iterator/
 * Implement BSTIterator over the inorder traversal of a BST: next() returns the next smallest value, hasNext() tells
 * whether one exists. Follow up: next() and hasNext() in average O(1) time with O(h) memory.
 * Example: [7,3,15,null,null,9,20] -> next 3, next 7, hasNext true, next 9, hasNext true, next 15, next 20, hasNext false
 * Explanation: README.md
 */
public class BinarySearchTreeIterator {
    public static void main(String[] args) {
        BSTIterator it = new BSTIterator(TreeNode.of(7, 3, 15, null, null, 9, 20));
        while (it.hasNext()) System.out.print(it.next() + " ");      // 3 7 9 15 20
    }

    /* Approach 1 - inorder into a list up front. O(1) per call, O(n) memory. */
    public static class BSTIteratorList {
        private final List<Integer> values = new ArrayList<>();
        private int index;

        public BSTIteratorList(TreeNode root) {
            fill(root);
        }

        private void fill(TreeNode n) {
            if (n == null) return;
            fill(n.left);
            values.add(n.val);
            fill(n.right);
        }

        public int next() {
            return values.get(index++);
        }

        public boolean hasNext() {
            return index < values.size();
        }
    }

    /*
     * Approach 2 - keep the "left spine" on a stack: the top is always the next smallest. After popping a node, push
     * the left spine of its right subtree. Each node is pushed and popped once -> amortized O(1), memory O(h).
     */
    public static class BSTIterator {
        private final Deque<TreeNode> stack = new ArrayDeque<>();

        public BSTIterator(TreeNode root) {
            pushLeft(root);
        }

        private void pushLeft(TreeNode n) {
            for (; n != null; n = n.left) stack.push(n);
        }

        public int next() {
            TreeNode node = stack.pop();
            pushLeft(node.right);
            return node.val;
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }
}
