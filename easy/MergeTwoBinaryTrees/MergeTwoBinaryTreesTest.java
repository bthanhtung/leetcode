package MergeTwoBinaryTrees;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeTwoBinaryTreesTest {

    @Test
    void examples() {
        Integer[] a = {1, 3, 2, 5}, b = {2, 1, 3, null, 4, null, 7};
        List<Integer> want = java.util.Arrays.asList(3, 4, 5, 5, 4, null, 7);
        assertEquals(want, TreeNode.toList(MergeTwoBinaryTrees.mergeTrees(TreeNode.of(a), TreeNode.of(b))));
        assertEquals(want, TreeNode.toList(MergeTwoBinaryTrees.mergeTreesCopy(TreeNode.of(a), TreeNode.of(b))));
        assertEquals(want, TreeNode.toList(MergeTwoBinaryTrees.mergeTreesIterative(TreeNode.of(a), TreeNode.of(b))));
        assertEquals(List.of(2, 2), TreeNode.toList(MergeTwoBinaryTrees.mergeTrees(TreeNode.of(1), TreeNode.of(1, 2))));
        assertEquals(List.of(), TreeNode.toList(MergeTwoBinaryTrees.mergeTreesIterative(null, null)));
    }

    @Test
    void copyLeavesInputsUntouched() {
        TreeNode a = TreeNode.of(1, 3, 2, 5), b = TreeNode.of(2, 1, 3, null, 4, null, 7);
        MergeTwoBinaryTrees.mergeTreesCopy(a, b);
        assertEquals(List.of(1, 3, 2, 5), TreeNode.toList(a));
    }
}
