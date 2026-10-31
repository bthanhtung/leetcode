package IntersectionOfTwoLinkedLists;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertSame;

class IntersectionOfTwoLinkedListsTest {

    private static ListNode prepend(int count, ListNode tail, Random r) {
        for (int i = 0; i < count; i++) tail = new ListNode(r.nextInt(10), tail);
        return tail;
    }

    @Test
    void randomShapes() {
        Random r = new Random(160);
        for (int t = 0; t < 1000; t++) {
            ListNode common = r.nextInt(4) == 0 ? null : prepend(1 + r.nextInt(5), null, r);
            ListNode a = prepend(r.nextInt(6), common, r), b = prepend(r.nextInt(6), common, r);
            ListNode expected = (a == null || b == null) ? null : common;
            assertSame(expected, IntersectionOfTwoLinkedLists.getIntersectionNode(a, b));
            assertSame(expected, IntersectionOfTwoLinkedLists.getIntersectionNodeSet(a, b));
            assertSame(expected, IntersectionOfTwoLinkedLists.getIntersectionNodeLength(a, b));
        }
    }
}
