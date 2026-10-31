package BinaryTreeMaximumPathSum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeMaximumPathSumTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(21) - 10, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(6, BinaryTreeMaximumPathSum.maxPathSum(TreeNode.of(1, 2, 3)));
        assertEquals(42, BinaryTreeMaximumPathSum.maxPathSum(TreeNode.of(-10, 9, 20, null, null, 15, 7)));
        assertEquals(-3, BinaryTreeMaximumPathSum.maxPathSum(TreeNode.of(-3)));
        Random r = new Random(124);
        for (int t = 0; t < 500; t++) {
            TreeNode root = randomTree(r, 1 + r.nextInt(15));
            assertEquals(BinaryTreeMaximumPathSum.bruteForce(root), BinaryTreeMaximumPathSum.maxPathSum(root));
        }
    }
}
