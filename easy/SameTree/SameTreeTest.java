package SameTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SameTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(3), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, SameTree.isSameTree(TreeNode.of(1, 2, 3), TreeNode.of(1, 2, 3)));
        assertEquals(false, SameTree.isSameTree(TreeNode.of(1, 2), TreeNode.of(1, null, 2)));
        assertEquals(false, SameTree.isSameTree(TreeNode.of(1, 2, 1), TreeNode.of(1, 1, 2)));
        assertEquals(true, SameTree.isSameTree(null, null));
        Random r = new Random(100);
        for (int t = 0; t < 2000; t++) {
            TreeNode a = randomTree(r, r.nextInt(5)), b = randomTree(r, r.nextInt(5));
            boolean expected = TreeNode.toList(a).equals(TreeNode.toList(b));
            assertEquals(expected, SameTree.isSameTree(a, b));
            assertEquals(expected, SameTree.isSameTreeIterative(a, b));
        }
    }
}
