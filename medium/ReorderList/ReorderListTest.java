package ReorderList;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReorderListTest {

    @Test
    void allLengths() {
        for (int n = 0; n <= 15; n++) {
            int[] values = IntStream.rangeClosed(1, n).toArray();
            List<Integer> expected = new ArrayList<>();
            for (int i = 0, j = n - 1; i <= j; i++, j--) {
                expected.add(values[i]);
                if (i != j) expected.add(values[j]);
            }
            ListNode a = ListNode.of(values), b = ListNode.of(values);
            ReorderList.reorderList(a);
            ReorderList.reorderListArray(b);
            assertEquals(expected, ListNode.toList(a));
            assertEquals(expected, ListNode.toList(b));
        }
    }
}
