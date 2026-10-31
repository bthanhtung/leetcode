package MiddleOfTheLinkedList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertSame;

class MiddleOfTheLinkedListTest {

    @Test
    void allLengths() {
        for (int n = 1; n <= 20; n++) {
            ListNode head = ListNode.of(IntStream.rangeClosed(1, n).toArray());
            ListNode expected = head;
            for (int i = 0; i < n / 2; i++) expected = expected.next;
            assertSame(expected, MiddleOfTheLinkedList.middleNode(head));
            assertSame(expected, MiddleOfTheLinkedList.middleNodeCount(head));
            assertSame(expected, MiddleOfTheLinkedList.middleNodeArray(head));
        }
    }
}
