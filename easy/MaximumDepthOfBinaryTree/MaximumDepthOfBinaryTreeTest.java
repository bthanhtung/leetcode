package MaximumDepthOfBinaryTree;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumDepthOfBinaryTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(3, MaximumDepthOfBinaryTree.maxDepth(TreeNode.of(3, 9, 20, null, null, 15, 7)));
        assertEquals(2, MaximumDepthOfBinaryTree.maxDepth(TreeNode.of(1, null, 2)));
        assertEquals(0, MaximumDepthOfBinaryTree.maxDepthBfs(null));
        Random r = new Random(104);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(30));
            int expected = MaximumDepthOfBinaryTree.maxDepth(root);
            assertEquals(expected, MaximumDepthOfBinaryTree.maxDepthBfs(root));
            assertEquals(expected, MaximumDepthOfBinaryTree.maxDepthStack(root));
        }
    }
}
