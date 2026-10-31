package DiameterOfBinaryTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiameterOfBinaryTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(3, DiameterOfBinaryTree.diameterOfBinaryTree(TreeNode.of(1, 2, 3, 4, 5)));
        assertEquals(1, DiameterOfBinaryTree.diameterOfBinaryTree(TreeNode.of(1, 2)));
        assertEquals(0, DiameterOfBinaryTree.diameterOfBinaryTree(TreeNode.of(1)));
        // longest path does not go through the root
        assertEquals(6, DiameterOfBinaryTree.diameterOfBinaryTree(TreeNode.of(1, 2, null, 3, 4, 5, null, null, 6, 7, null, null, 8)));
        Random r = new Random(543);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(30));
            assertEquals(DiameterOfBinaryTree.diameterOfBinaryTreeNaive(root), DiameterOfBinaryTree.diameterOfBinaryTree(root));
        }
    }
}
