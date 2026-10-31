package SymmetricTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SymmetricTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(2), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    static TreeNode mirrorCopy(TreeNode n) {
        return n == null ? null : new TreeNode(n.val, mirrorCopy(n.right), mirrorCopy(n.left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, SymmetricTree.isSymmetric(TreeNode.of(1, 2, 2, 3, 4, 4, 3)));
        assertEquals(false, SymmetricTree.isSymmetric(TreeNode.of(1, 2, 2, null, 3, null, 3)));
        Random r = new Random(101);
        for (int t = 0; t < 1000; t++) {
            TreeNode half = randomTree(r, r.nextInt(5));
            TreeNode symmetric = new TreeNode(0, half, mirrorCopy(half));
            assertEquals(true, SymmetricTree.isSymmetric(symmetric));
            assertEquals(true, SymmetricTree.isSymmetricIterative(symmetric));
            TreeNode any = randomTree(r, r.nextInt(7));
            assertEquals(SymmetricTree.isSymmetric(any), SymmetricTree.isSymmetricIterative(any));
        }
    }
}
