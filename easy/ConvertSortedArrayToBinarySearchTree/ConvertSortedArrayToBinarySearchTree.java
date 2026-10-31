package ConvertSortedArrayToBinarySearchTree;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 108. Convert Sorted Array to Binary Search Tree — https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
 * Convert a sorted array into a height-balanced binary search tree (depths of subtrees differ by at most 1).
 * Input: [-10,-3,0,5,9] -> [0,-3,9,-10,null,5] (one valid answer) · [1,3] -> [3,1] or [1,null,3]
 * Explanation: README.md
 */
public class ConvertSortedArrayToBinarySearchTree {
    public static void main(String[] args) {
        System.out.println(TreeNode.toList(sortedArrayToBST(new int[]{-10, -3, 0, 5, 9})));    // [0, -10, 5, null, -3, null, 9]
    }

    /* Approach 1 - the middle element is the root; build both halves recursively. Time O(n), space O(log n). */
    public static TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private static TreeNode build(int[] nums, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int mid = lo + (hi - lo) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = build(nums, lo, mid - 1);
        root.right = build(nums, mid + 1, hi);
        return root;
    }

    /* Approach 2 - the same split without recursion: a stack of (node, lo, hi) jobs. Time O(n). */
    public static TreeNode sortedArrayToBSTIterative(int[] nums) {
        if (nums.length == 0) return null;
        record Job(TreeNode node, int lo, int hi) {}
        int mid = (nums.length - 1) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        Deque<Job> stack = new ArrayDeque<>();
        stack.push(new Job(root, 0, nums.length - 1));
        while (!stack.isEmpty()) {
            Job job = stack.pop();
            int m = job.lo + (job.hi - job.lo) / 2;
            if (job.lo <= m - 1) {
                int l = job.lo + (m - 1 - job.lo) / 2;
                job.node.left = new TreeNode(nums[l]);
                stack.push(new Job(job.node.left, job.lo, m - 1));
            }
            if (m + 1 <= job.hi) {
                int r = m + 1 + (job.hi - m - 1) / 2;
                job.node.right = new TreeNode(nums[r]);
                stack.push(new Job(job.node.right, m + 1, job.hi));
            }
        }
        return root;
    }
}
