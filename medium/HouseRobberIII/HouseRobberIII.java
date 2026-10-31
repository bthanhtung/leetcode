package HouseRobberIII;

import java.util.HashMap;
import java.util.Map;

/*
 * 337. House Robber III — https://leetcode.com/problems/house-robber-iii/
 * Houses form a binary tree; robbing two directly-linked houses alerts the police. Return the maximum amount.
 * Input: [3,2,3,null,3,null,1] -> 7 (3 + 3 + 1) · [3,4,5,1,3,null,1] -> 9 (4 + 5)
 * Explanation: README.md
 */
public class HouseRobberIII {
    public static void main(String[] args) {
        System.out.println(rob(TreeNode.of(3, 2, 3, null, 3, null, 1)));          // 7
        System.out.println(robMemo(TreeNode.of(3, 4, 5, 1, 3, null, 1)));         // 9
    }

    /* Approach 1 - best(node) = max(node + best of grandchildren, best of children), memoized per node. Time O(n). */
    public static int robMemo(TreeNode root) {
        return best(root, new HashMap<>());
    }

    private static int best(TreeNode n, Map<TreeNode, Integer> memo) {
        if (n == null) return 0;
        Integer known = memo.get(n);
        if (known != null) return known;
        int take = n.val;
        if (n.left != null) take += best(n.left.left, memo) + best(n.left.right, memo);
        if (n.right != null) take += best(n.right.left, memo) + best(n.right.right, memo);
        int skip = best(n.left, memo) + best(n.right, memo);
        int result = Math.max(take, skip);
        memo.put(n, result);
        return result;
    }

    /* Approach 2 - each call returns {best if robbing this node, best if not}; no memo needed. Time O(n), space O(h). */
    public static int rob(TreeNode root) {
        int[] r = dfs(root);
        return Math.max(r[0], r[1]);
    }

    private static int[] dfs(TreeNode n) {
        if (n == null) return new int[]{0, 0};
        int[] l = dfs(n.left), r = dfs(n.right);
        int robThis = n.val + l[1] + r[1];                          // children must be skipped
        int skipThis = Math.max(l[0], l[1]) + Math.max(r[0], r[1]); // children are free to choose
        return new int[]{robThis, skipThis};
    }
}
