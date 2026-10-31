package BinarySearchTreeIterator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinarySearchTreeIteratorTest {

    private static TreeNode insert(TreeNode root, int v) {
        if (root == null) return new TreeNode(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }

    @Test
    void exampleAndRandom() {
        BinarySearchTreeIterator.BSTIterator it = new BinarySearchTreeIterator.BSTIterator(TreeNode.of(7, 3, 15, null, null, 9, 20));
        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) seen.add(it.next());
        assertEquals(List.of(3, 7, 9, 15, 20), seen);
        Random r = new Random(173);
        for (int t = 0; t < 300; t++) {
            TreeNode root = null;
            for (int i = 1 + r.nextInt(30); i > 0; i--) root = insert(root, r.nextInt(100));
            BinarySearchTreeIterator.BSTIterator a = new BinarySearchTreeIterator.BSTIterator(root);
            BinarySearchTreeIterator.BSTIteratorList b = new BinarySearchTreeIterator.BSTIteratorList(root);
            while (b.hasNext()) {
                assertEquals(true, a.hasNext());
                assertEquals(b.next(), a.next());
            }
            assertEquals(false, a.hasNext());
        }
    }
}
