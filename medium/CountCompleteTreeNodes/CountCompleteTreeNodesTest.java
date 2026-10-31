package CountCompleteTreeNodes;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountCompleteTreeNodesTest {

    @Test
    void everySizeUpTo300() {
        assertEquals(0, CountCompleteTreeNodes.countNodes(null));
        for (int n = 1; n <= 300; n++) {
            TreeNode root = TreeNode.of(IntStream.rangeClosed(1, n).boxed().toArray(Integer[]::new));   // complete by construction
            assertEquals(n, CountCompleteTreeNodes.countNodesNaive(root));
            assertEquals(n, CountCompleteTreeNodes.countNodes(root));
            assertEquals(n, CountCompleteTreeNodes.countNodesBinarySearch(root));
        }
    }
}
