package SumRootToLeafNumbers;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 129. Sum Root to Leaf Numbers — https://leetcode.com/problems/sum-root-to-leaf-numbers/
 * Each root-to-leaf path of digits 0-9 forms a number (1 -> 2 -> 3 is 123). Return the sum of all such numbers.
 * Input: [1,2,3] -> 25 (12 + 13) · [4,9,0,5,1] -> 1026 (495 + 491 + 40)
 * Explanation: README.md
 */
public class SumRootToLeafNumbers {
    public static void main(String[] args) {
        System.out.println(sumNumbers(TreeNode.of(1, 2, 3)));            // 25
        System.out.println(sumNumbersBfs(TreeNode.of(4, 9, 0, 5, 1)));   // 1026
    }

    /* Approach 1 - DFS carrying the number formed so far: current * 10 + node.val. Time O(n), space O(h). */
    public static int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private static int dfs(TreeNode node, int current) {
        if (node == null) return 0;
        current = current * 10 + node.val;
        if (node.left == null && node.right == null) return current;
        return dfs(node.left, current) + dfs(node.right, current);
    }

    /* Approach 2 - BFS with (node, number so far) pairs; add the number at each leaf. Time O(n). */
    public static int sumNumbersBfs(TreeNode root) {
        if (root == null) return 0;
        record Item(TreeNode node, int value) {}
        Queue<Item> queue = new ArrayDeque<>();
        queue.add(new Item(root, root.val));
        int total = 0;
        while (!queue.isEmpty()) {
            Item it = queue.poll();
            TreeNode n = it.node;
            if (n.left == null && n.right == null) total += it.value;
            if (n.left != null) queue.add(new Item(n.left, it.value * 10 + n.left.val));
            if (n.right != null) queue.add(new Item(n.right, it.value * 10 + n.right.val));
        }
        return total;
    }
}
