package ReverseLinkedList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseLinkedListTest {

    @Test
    void allLengths() {
        for (int n = 0; n <= 12; n++) {
            int[] values = IntStream.rangeClosed(1, n).toArray();
            List<Integer> expected = new ArrayList<>(IntStream.of(values).boxed().toList());
            Collections.reverse(expected);
            assertEquals(expected, ListNode.toList(ReverseLinkedList.reverseList(ListNode.of(values))));
            assertEquals(expected, ListNode.toList(ReverseLinkedList.reverseListRecursive(ListNode.of(values))));
            assertEquals(expected, ListNode.toList(ReverseLinkedList.reverseListStack(ListNode.of(values))));
        }
    }
}
