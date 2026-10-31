package PopulatingNextRightPointersInEachNode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PopulatingNextRightPointersInEachNodeTest {

    private static String serialize(PopulatingNextRightPointersInEachNode.Node root) {
        List<String> out = new ArrayList<>();
        for (PopulatingNextRightPointersInEachNode.Node leftmost = root; leftmost != null; leftmost = leftmost.left) {
            for (PopulatingNextRightPointersInEachNode.Node n = leftmost; n != null; n = n.next) out.add(String.valueOf(n.val));
            out.add("#");
        }
        return String.join(",", out);
    }

    @Test
    void allDepths() {
        assertEquals("1,#,2,3,#,4,5,6,7,#", serialize(PopulatingNextRightPointersInEachNode.connect(PopulatingNextRightPointersInEachNode.perfect(3, new int[]{1}))));
        List<UnaryOperator<PopulatingNextRightPointersInEachNode.Node>> approaches = List.of(PopulatingNextRightPointersInEachNode::connect, PopulatingNextRightPointersInEachNode::connectBfs, PopulatingNextRightPointersInEachNode::connectRecursive);
        for (int depth = 1; depth <= 8; depth++) {
            String expected = null;
            for (UnaryOperator<PopulatingNextRightPointersInEachNode.Node> f : approaches) {
                String got = serialize(f.apply(PopulatingNextRightPointersInEachNode.perfect(depth, new int[]{1})));
                if (expected == null) expected = got;
                assertEquals(expected, got);
            }
            assertEquals((1 << depth) - 1 + depth, expected.split(",").length);
        }
    }
}
