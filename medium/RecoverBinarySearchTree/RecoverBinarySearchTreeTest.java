package RecoverBinarySearchTree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecoverBinarySearchTreeTest {

    private static TreeNode insert(TreeNode root, int v) {
        if (root == null) return new TreeNode(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }

    private static void collect(TreeNode n, List<TreeNode> out) {
        if (n == null) return;
        collect(n.left, out);
        out.add(n);
        collect(n.right, out);
    }

    @Test
    void examplesAndRandom() {
        TreeNode a = TreeNode.of(1, 3, null, null, 2);
        RecoverBinarySearchTree.recoverTree(a);
        assertEquals(java.util.Arrays.asList(3, 1, null, null, 2), TreeNode.toList(a));
        Random r = new Random(99);
        for (int t = 0; t < 500; t++) {
            long seed = r.nextLong();
            List<Integer> expected = null;
            for (int approach = 0; approach < 3; approach++) {
                Random g = new Random(seed);
                TreeNode root = null;
                for (int i = 2 + g.nextInt(20); i > 0; i--) root = insert(root, g.nextInt(100));
                List<TreeNode> nodes = new ArrayList<>();
                collect(root, nodes);
                if (nodes.size() < 2) break;
                if (expected == null) expected = TreeNode.toList(root);
                int i = g.nextInt(nodes.size()), j = (i + 1 + g.nextInt(nodes.size() - 1)) % nodes.size();
                int tmp = nodes.get(i).val;
                nodes.get(i).val = nodes.get(j).val;
                nodes.get(j).val = tmp;
                if (approach == 0) RecoverBinarySearchTree.recoverTree(root);
                else if (approach == 1) RecoverBinarySearchTree.recoverTreeMorris(root);
                else RecoverBinarySearchTree.recoverTreeSort(root);
                assertEquals(expected, TreeNode.toList(root));
            }
        }
    }
}
