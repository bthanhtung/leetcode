package MinimumDepthOfBinaryTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumDepthOfBinaryTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(0, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(2, MinimumDepthOfBinaryTree.minDepth(TreeNode.of(3, 9, 20, null, null, 15, 7)));
        assertEquals(5, MinimumDepthOfBinaryTree.minDepth(TreeNode.of(2, null, 3, null, 4, null, 5, null, 6)));
        assertEquals(0, MinimumDepthOfBinaryTree.minDepth(null));
        Random r = new Random(111);
        for (int t = 0; t < 500; t++) {
            TreeNode root = randomTree(r, r.nextInt(25));
            assertEquals(MinimumDepthOfBinaryTree.minDepthRecursive(root), MinimumDepthOfBinaryTree.minDepth(root));
        }
    }
}
