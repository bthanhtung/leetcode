package RangeSumOfBST;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeSumOfBSTTest {

    private static TreeNode insert(TreeNode root, int v) {
        if (root == null) return new TreeNode(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }

    @Test
    void examples() {
        assertEquals(32, RangeSumOfBST.rangeSumBST(TreeNode.of(10, 5, 15, 3, 7, null, 18), 7, 15));
        assertEquals(23, RangeSumOfBST.rangeSumBST(TreeNode.of(10, 5, 15, 3, 7, 13, 18, 1, null, 6), 6, 10));
    }

    @Test
    void randomBsts() {
        Random r = new Random(938);
        for (int t = 0; t < 500; t++) {
            TreeNode root = null;
            for (int k = 1 + r.nextInt(30); k > 0; k--) root = insert(root, r.nextInt(100));
            int low = r.nextInt(100), high = low + r.nextInt(50);
            int expected = RangeSumOfBST.rangeSumAllNodes(root, low, high);
            assertEquals(expected, RangeSumOfBST.rangeSumBST(root, low, high));
            assertEquals(expected, RangeSumOfBST.rangeSumBSTIterative(root, low, high));
        }
    }
}
