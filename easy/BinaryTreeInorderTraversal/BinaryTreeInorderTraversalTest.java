package BinaryTreeInorderTraversal;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeInorderTraversalTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examples() {
        assertEquals(List.of(1, 3, 2), BinaryTreeInorderTraversal.inorderTraversal(TreeNode.of(1, null, 2, 3)));
        assertEquals(List.of(), BinaryTreeInorderTraversal.inorderTraversal(null));
        assertEquals(List.of(4, 2, 6, 5, 7, 1, 3, 9, 8), BinaryTreeInorderTraversal.inorderTraversalMorris(TreeNode.of(1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9)));
    }

    @Test
    void randomTreesAndMorrisRestoresTree() {
        Random r = new Random(94);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(25));
            List<Integer> shape = TreeNode.toList(root);
            List<Integer> expected = BinaryTreeInorderTraversal.inorderTraversalRecursive(root);
            assertEquals(expected, BinaryTreeInorderTraversal.inorderTraversal(root));
            assertEquals(expected, BinaryTreeInorderTraversal.inorderTraversalMorris(root));
            assertEquals(shape, TreeNode.toList(root), "Morris must undo its threads");
        }
    }
}
