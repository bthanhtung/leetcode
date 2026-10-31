package ReverseLinkedListII;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseLinkedListIITest {

    @Test
    void everyRange() {
        assertEquals(List.of(1, 4, 3, 2, 5), ListNode.toList(ReverseLinkedListII.reverseBetween(ListNode.of(1, 2, 3, 4, 5), 2, 4)));
        for (int len = 1; len <= 7; len++) {
            int[] values = new int[len];
            for (int i = 0; i < len; i++) values[i] = i + 1;
            for (int l = 1; l <= len; l++) {
                for (int r = l; r <= len; r++) {
                    List<Integer> expected = new ArrayList<>();
                    for (int v : values) expected.add(v);
                    Collections.reverse(expected.subList(l - 1, r));
                    assertEquals(expected, ListNode.toList(ReverseLinkedListII.reverseBetween(ListNode.of(values), l, r)));
                    assertEquals(expected, ListNode.toList(ReverseLinkedListII.reverseBetweenRecursive(ListNode.of(values), l, r)));
                }
            }
        }
    }
}
