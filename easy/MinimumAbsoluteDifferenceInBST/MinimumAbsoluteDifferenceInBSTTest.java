package MinimumAbsoluteDifferenceInBST;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumAbsoluteDifferenceInBSTTest {

    private static TreeNode insert(TreeNode root, int v) {
        if (root == null) return new TreeNode(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(1, MinimumAbsoluteDifferenceInBST.getMinimumDifference(TreeNode.of(4, 2, 6, 1, 3)));
        assertEquals(1, MinimumAbsoluteDifferenceInBST.getMinimumDifference(TreeNode.of(1, 0, 48, null, null, 12, 49)));
        Random r = new Random(530);
        for (int t = 0; t < 500; t++) {
            TreeNode root = new TreeNode(r.nextInt(1000));
            for (int k = 1 + r.nextInt(20); k > 0; k--) insert(root, r.nextInt(1000));
            if (root.left == null && root.right == null) continue;
            assertEquals(MinimumAbsoluteDifferenceInBST.getMinimumDifferenceList(root), MinimumAbsoluteDifferenceInBST.getMinimumDifference(root));
        }
    }
}
