package OddEvenLinkedList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OddEvenLinkedListTest {

    @Test
    void allLengths() {
        for (int n = 0; n <= 12; n++) {
            int[] values = IntStream.rangeClosed(1, n).toArray();
            List<Integer> expected = new ArrayList<>();
            for (int i = 0; i < n; i += 2) expected.add(values[i]);
            for (int i = 1; i < n; i += 2) expected.add(values[i]);
            assertEquals(expected, ListNode.toList(OddEvenLinkedList.oddEvenList(ListNode.of(values))));
            assertEquals(expected, ListNode.toList(OddEvenLinkedList.oddEvenListCollect(ListNode.of(values))));
        }
    }
}
