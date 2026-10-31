package RemoveLinkedListElements;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveLinkedListElementsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(1, 2, 3, 4, 5), ListNode.toList(RemoveLinkedListElements.removeElements(ListNode.of(1, 2, 6, 3, 4, 5, 6), 6)));
        assertEquals(List.of(), ListNode.toList(RemoveLinkedListElements.removeElements(ListNode.of(7, 7, 7, 7), 7)));
        Random r = new Random(203);
        for (int t = 0; t < 1000; t++) {
            int[] values = r.ints(r.nextInt(10), 0, 3).toArray();
            int val = r.nextInt(3);
            List<Integer> expected = IntStream.of(values).filter(v -> v != val).boxed().collect(Collectors.toList());
            assertEquals(expected, ListNode.toList(RemoveLinkedListElements.removeElements(ListNode.of(values), val)));
            assertEquals(expected, ListNode.toList(RemoveLinkedListElements.removeElementsRecursive(ListNode.of(values), val)));
        }
    }
}
