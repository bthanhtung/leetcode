package InvertBinaryTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvertBinaryTreeTest {

    @Test
    void examples() {
        assertEquals(List.of(4, 7, 2, 9, 6, 3, 1), TreeNode.toList(InvertBinaryTree.invertTree(TreeNode.of(4, 2, 7, 1, 3, 6, 9))));
        assertEquals(List.of(4, 7, 2, 9, 6, 3, 1), TreeNode.toList(InvertBinaryTree.invertTreeBfs(TreeNode.of(4, 2, 7, 1, 3, 6, 9))));
        assertEquals(java.util.Arrays.asList(1, null, 2), TreeNode.toList(InvertBinaryTree.invertTree(TreeNode.of(1, 2))));
        assertEquals(List.of(), TreeNode.toList(InvertBinaryTree.invertTreeBfs(null)));
    }

    @Test
    void invertingTwiceRestores() {
        TreeNode root = TreeNode.of(5, 3, 8, 1, 4, 7, 9, null, 2, null, null, 6);
        List<Integer> before = TreeNode.toList(root);
        InvertBinaryTree.invertTreeBfs(InvertBinaryTree.invertTree(root));
        assertEquals(before, TreeNode.toList(root));
    }
}
