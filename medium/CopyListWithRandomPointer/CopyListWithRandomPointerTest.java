package CopyListWithRandomPointer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CopyListWithRandomPointerTest {

    private static List<List<Integer>> describe(CopyListWithRandomPointer.Node head, Map<CopyListWithRandomPointer.Node, Boolean> objects) {
        List<CopyListWithRandomPointer.Node> nodes = new ArrayList<>();
        for (CopyListWithRandomPointer.Node p = head; p != null; p = p.next) {
            nodes.add(p);
            objects.put(p, true);
        }
        List<List<Integer>> out = new ArrayList<>();
        for (CopyListWithRandomPointer.Node p : nodes) out.add(List.of(p.val, p.random == null ? -1 : nodes.indexOf(p.random)));
        return out;
    }

    @Test
    void random() {
        Random r = new Random(138);
        List<UnaryOperator<CopyListWithRandomPointer.Node>> approaches = List.of(CopyListWithRandomPointer::copyRandomList, CopyListWithRandomPointer::copyRandomListMap);
        for (int t = 0; t < 500; t++) {
            int n = r.nextInt(10);
            int[][] spec = new int[n][];
            for (int i = 0; i < n; i++) spec[i] = new int[]{r.nextInt(20), r.nextInt(n + 1) - 1};
            CopyListWithRandomPointer.Node head = CopyListWithRandomPointer.build(spec);
            Map<CopyListWithRandomPointer.Node, Boolean> originals = new IdentityHashMap<>();
            List<List<Integer>> expected = describe(head, originals);
            for (UnaryOperator<CopyListWithRandomPointer.Node> f : approaches) {
                Map<CopyListWithRandomPointer.Node, Boolean> copies = new IdentityHashMap<>();
                assertEquals(expected, describe(f.apply(head), copies));
                for (CopyListWithRandomPointer.Node c : copies.keySet()) assertFalse(originals.containsKey(c));
                assertEquals(expected, describe(head, new IdentityHashMap<>()), "original must be restored");
            }
        }
    }
}
