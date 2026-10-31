package LinkedListCycleII;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertSame;

class LinkedListCycleIITest {

    @Test
    void randomShapes() {
        Random r = new Random(142);
        for (int t = 0; t < 2000; t++) {
            int n = r.nextInt(15);
            ListNode head = ListNode.of(IntStream.range(0, n).toArray());
            ListNode expected = null;
            int pos = n == 0 ? -1 : r.nextInt(n + 1) - 1;
            if (pos >= 0) {
                ListNode tail = head;
                while (tail.next != null) tail = tail.next;
                expected = head;
                for (int i = 0; i < pos; i++) expected = expected.next;
                tail.next = expected;
            }
            assertSame(expected, LinkedListCycleII.detectCycle(head));
            assertSame(expected, LinkedListCycleII.detectCycleSet(head));
        }
    }
}
