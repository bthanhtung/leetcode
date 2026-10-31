package BinaryTreePreorderTraversal;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreePreorderTraversalTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(1, 2, 3), BinaryTreePreorderTraversal.preorderTraversal(TreeNode.of(1, null, 2, 3)));
        assertEquals(List.of(1, 2, 4, 5, 6, 7, 3, 8, 9), BinaryTreePreorderTraversal.preorderTraversal(TreeNode.of(1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9)));
        Random r = new Random(144);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(25));
            List<Integer> shape = TreeNode.toList(root);
            List<Integer> expected = BinaryTreePreorderTraversal.preorderTraversalRecursive(root);
            assertEquals(expected, BinaryTreePreorderTraversal.preorderTraversal(root));
            assertEquals(expected, BinaryTreePreorderTraversal.preorderTraversalMorris(root));
            assertEquals(shape, TreeNode.toList(root));
        }
    }
}
