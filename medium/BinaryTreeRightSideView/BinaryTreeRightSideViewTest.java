package BinaryTreeRightSideView;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeRightSideViewTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(1, 3, 4), BinaryTreeRightSideView.rightSideView(TreeNode.of(1, 2, 3, null, 5, null, 4)));
        assertEquals(List.of(1, 3, 4, 5), BinaryTreeRightSideView.rightSideView(TreeNode.of(1, 2, 3, 4, null, null, null, 5)));
        assertEquals(List.of(), BinaryTreeRightSideView.rightSideView(null));
        Random r = new Random(199);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(30));
            assertEquals(BinaryTreeRightSideView.rightSideView(root), BinaryTreeRightSideView.rightSideViewDfs(root));
        }
    }
}
