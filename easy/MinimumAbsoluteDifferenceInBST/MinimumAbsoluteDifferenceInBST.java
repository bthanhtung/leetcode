package MinimumAbsoluteDifferenceInBST;

import com.tungdadev.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/*
 * 530. Minimum Absolute Difference in BST — https://leetcode.com/problems/minimum-absolute-difference-in-bst/
 * Return the minimum absolute difference between the values of any two different nodes of a BST.
 * Input: [4,2,6,1,3] -> 1 · [1,0,48,null,null,12,49] -> 1
 * Explanation: README.md
 */
public class MinimumAbsoluteDifferenceInBST {
    public static void main(String[] args) {
        System.out.println(getMinimumDifference(TreeNode.of(4, 2, 6, 1, 3)));                  // 1
        System.out.println(getMinimumDifferenceList(TreeNode.of(1, 0, 48, null, null, 12, 49))); // 1
    }

    /* Approach 1 - inorder gives sorted values; the answer is the smallest gap between neighbours. Time O(n), space O(n). */
    public static int getMinimumDifferenceList(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        collect(root, values);
        int best = Integer.MAX_VALUE;
        for (int i = 1; i < values.size(); i++) best = Math.min(best, values.get(i) - values.get(i - 1));
        return best;
    }

    private static void collect(TreeNode node, List<Integer> out) {
        if (node == null) return;
        collect(node.left, out);
        out.add(node.val);
        collect(node.right, out);
    }

    /* Approach 2 - the same inorder walk, remembering only the previous value. Time O(n), space O(h). */
    public static int getMinimumDifference(TreeNode root) {
        int[] state = {Integer.MAX_VALUE, -1};   // {best, previous value or -1}
        walk(root, state);
        return state[0];
    }

    private static void walk(TreeNode node, int[] state) {
        if (node == null) return;
        walk(node.left, state);
        if (state[1] >= 0) state[0] = Math.min(state[0], node.val - state[1]);
        state[1] = node.val;
        walk(node.right, state);
    }
}
