package SerializeAndDeserializeBinaryTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializeAndDeserializeBinaryTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(2001) - 1000, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void roundTrip() {
        SerializeAndDeserializeBinaryTree.Codec a = new SerializeAndDeserializeBinaryTree.Codec();
        SerializeAndDeserializeBinaryTree.CodecBfs b = new SerializeAndDeserializeBinaryTree.CodecBfs();
        assertEquals(java.util.Arrays.asList(1, 2, 3, null, null, 4, 5), TreeNode.toList(a.deserialize(a.serialize(TreeNode.of(1, 2, 3, null, null, 4, 5)))));
        assertEquals(java.util.List.of(), TreeNode.toList(a.deserialize(a.serialize(null))));
        Random r = new Random(297);
        for (int t = 0; t < 500; t++) {
            TreeNode root = randomTree(r, r.nextInt(40));
            assertEquals(TreeNode.toList(root), TreeNode.toList(a.deserialize(a.serialize(root))));
            assertEquals(TreeNode.toList(root), TreeNode.toList(b.deserialize(b.serialize(root))));
        }
    }
}
