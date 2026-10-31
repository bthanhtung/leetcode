package ConstructBinaryTreeFromPreorderAndInorderTraversal;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstructBinaryTreeFromPreorderAndInorderTraversalTest {

    private static int counter;

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(counter++, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    static void pre(TreeNode n, List<Integer> out) {
        if (n == null) return;
        out.add(n.val);
        pre(n.left, out);
        pre(n.right, out);
    }

    static void in(TreeNode n, List<Integer> out) {
        if (n == null) return;
        in(n.left, out);
        out.add(n.val);
        in(n.right, out);
    }

    @Test
    void examplesAndRandom() {
        assertEquals(java.util.Arrays.asList(3, 9, 20, null, null, 15, 7),
                TreeNode.toList(ConstructBinaryTreeFromPreorderAndInorderTraversal.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7})));
        Random r = new Random(105);
        for (int t = 0; t < 500; t++) {
            counter = 0;
            TreeNode tree = randomTree(r, r.nextInt(30));
            List<Integer> p = new ArrayList<>(), i = new ArrayList<>();
            pre(tree, p);
            in(tree, i);
            int[] preorder = p.stream().mapToInt(Integer::intValue).toArray(), inorder = i.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(TreeNode.toList(tree), TreeNode.toList(ConstructBinaryTreeFromPreorderAndInorderTraversal.buildTree(preorder, inorder)));
            assertEquals(TreeNode.toList(tree), TreeNode.toList(ConstructBinaryTreeFromPreorderAndInorderTraversal.buildTreeIterative(preorder, inorder)));
        }
    }
}
