package BalancedBinaryTree;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalancedBinaryTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(0, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, BalancedBinaryTree.isBalanced(TreeNode.of(3, 9, 20, null, null, 15, 7)));
        assertEquals(false, BalancedBinaryTree.isBalanced(TreeNode.of(1, 2, 2, 3, 3, null, null, 4, 4)));
        assertEquals(true, BalancedBinaryTree.isBalanced(null));
        // root's subtrees have equal height but a child is unbalanced
        assertEquals(false, BalancedBinaryTree.isBalanced(TreeNode.of(1, 2, 2, 3, null, null, 3, 4, null, null, 4)));
        Random r = new Random(110);
        for (int t = 0; t < 1000; t++) {
            TreeNode root = randomTree(r, r.nextInt(15));
            assertEquals(BalancedBinaryTree.isBalancedTopDown(root), BalancedBinaryTree.isBalanced(root));
        }
    }
}
