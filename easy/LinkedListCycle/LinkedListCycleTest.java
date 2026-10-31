package LinkedListCycle;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListCycleTest {

    @Test
    void randomShapes() {
        Random r = new Random(141);
        for (int t = 0; t < 1000; t++) {
            int n = r.nextInt(12);
            ListNode head = ListNode.of(IntStream.range(0, n).toArray());
            int pos = n == 0 ? -1 : r.nextInt(n + 1) - 1;          // -1 means no cycle
            if (pos >= 0) {
                ListNode tail = head, target = head;
                while (tail.next != null) tail = tail.next;
                for (int i = 0; i < pos; i++) target = target.next;
                tail.next = target;
            }
            assertEquals(pos >= 0, LinkedListCycle.hasCycle(head));
            assertEquals(pos >= 0, LinkedListCycle.hasCycleSet(head));
        }
    }
}
