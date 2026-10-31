package SearchInABinarySearchTree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class SearchInABinarySearchTreeTest {

    @Test
    void examples() {
        TreeNode root = TreeNode.of(4, 2, 7, 1, 3);
        assertEquals(List.of(2, 1, 3), TreeNode.toList(SearchInABinarySearchTree.searchBST(root, 2)));
        assertNull(SearchInABinarySearchTree.searchBST(root, 5));
        for (int v = 0; v <= 8; v++) {
            assertSame(SearchInABinarySearchTree.searchBST(root, v), SearchInABinarySearchTree.searchBSTRecursive(root, v));
        }
    }
}
