package PalindromeLinkedList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromeLinkedListTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, PalindromeLinkedList.isPalindrome(ListNode.of(1, 2, 2, 1)));
        assertEquals(false, PalindromeLinkedList.isPalindrome(ListNode.of(1, 2)));
        assertEquals(true, PalindromeLinkedList.isPalindrome(ListNode.of(1, 2, 1)));
        Random r = new Random(234);
        for (int t = 0; t < 2000; t++) {
            int[] half = r.ints(r.nextInt(5), 0, 3).toArray();
            int[] values = new int[r.nextBoolean() ? half.length * 2 : r.nextInt(8)];
            if (values.length == half.length * 2) {
                for (int i = 0; i < half.length; i++) values[i] = values[values.length - 1 - i] = half[i];
            } else {
                for (int i = 0; i < values.length; i++) values[i] = r.nextInt(3);
            }
            ListNode head = ListNode.of(values);
            List<Integer> before = ListNode.toList(head);
            boolean expected = PalindromeLinkedList.isPalindromeArray(head);
            assertEquals(expected, PalindromeLinkedList.isPalindromeRecursive(head));
            assertEquals(expected, PalindromeLinkedList.isPalindrome(head));
            assertEquals(before, ListNode.toList(head), "list must be restored");
        }
    }
}
