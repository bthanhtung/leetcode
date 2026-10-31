package LowestCommonAncestorOfABinaryTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertSame;

class LowestCommonAncestorOfABinaryTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    private static boolean path(TreeNode root, TreeNode target, List<TreeNode> out) {
        if (root == null) return false;
        out.add(root);
        if (root == target || path(root.left, target, out) || path(root.right, target, out)) return true;
        out.removeLast();
        return false;
    }

    private static void collect(TreeNode n, List<TreeNode> out) {
        if (n == null) return;
        out.add(n);
        collect(n.left, out);
        collect(n.right, out);
    }

    @Test
    void randomAgainstPathComparison() {
        Random r = new Random(236);
        for (int t = 0; t < 500; t++) {
            TreeNode root = randomTree(r, 2 + r.nextInt(25));
            List<TreeNode> nodes = new ArrayList<>();
            collect(root, nodes);
            TreeNode p = nodes.get(r.nextInt(nodes.size())), q = nodes.get(r.nextInt(nodes.size()));
            List<TreeNode> pp = new ArrayList<>(), qp = new ArrayList<>();
            path(root, p, pp);
            path(root, q, qp);
            TreeNode expected = root;
            for (int i = 0; i < Math.min(pp.size(), qp.size()) && pp.get(i) == qp.get(i); i++) expected = pp.get(i);
            assertSame(expected, LowestCommonAncestorOfABinaryTree.lowestCommonAncestor(root, p, q));
            assertSame(expected, LowestCommonAncestorOfABinaryTree.lowestCommonAncestorParents(root, p, q));
        }
    }
}
