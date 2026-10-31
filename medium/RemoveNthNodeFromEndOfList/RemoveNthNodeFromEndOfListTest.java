package RemoveNthNodeFromEndOfList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveNthNodeFromEndOfListTest {

    @Test
    void everyPosition() {
        assertEquals(List.of(1, 2, 3, 5), ListNode.toList(RemoveNthNodeFromEndOfList.removeNthFromEnd(ListNode.of(1, 2, 3, 4, 5), 2)));
        assertEquals(List.of(), ListNode.toList(RemoveNthNodeFromEndOfList.removeNthFromEnd(ListNode.of(1), 1)));
        for (int len = 1; len <= 8; len++) {
            int[] values = new int[len];
            for (int i = 0; i < len; i++) values[i] = i;
            for (int n = 1; n <= len; n++) {
                List<Integer> expected = new ArrayList<>();
                for (int v : values) if (v != len - n) expected.add(v);
                assertEquals(expected, ListNode.toList(RemoveNthNodeFromEndOfList.removeNthFromEnd(ListNode.of(values), n)));
                assertEquals(expected, ListNode.toList(RemoveNthNodeFromEndOfList.removeNthFromEndTwoPass(ListNode.of(values), n)));
            }
        }
    }
}
