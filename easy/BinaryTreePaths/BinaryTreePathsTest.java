package BinaryTreePaths;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreePathsTest {

    @Test
    void examples() {
        TreeNode root = TreeNode.of(1, 2, 3, null, 5);
        assertEquals(List.of("1->2->5", "1->3"), BinaryTreePaths.binaryTreePaths(root));
        assertEquals(List.of("1->2->5", "1->3"), BinaryTreePaths.binaryTreePathsBacktrack(root));
        assertEquals(Set.of("1->2->5", "1->3"), new HashSet<>(BinaryTreePaths.binaryTreePathsBfs(root)));
        assertEquals(List.of("1"), BinaryTreePaths.binaryTreePaths(TreeNode.of(1)));
        TreeNode neg = TreeNode.of(-10, 20, -30, 4, null, null, 50);
        assertEquals(List.of("-10->20->4", "-10->-30->50"), BinaryTreePaths.binaryTreePathsBacktrack(neg));
    }
}
