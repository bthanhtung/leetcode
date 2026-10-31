package MergeTwoSortedLists;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeTwoSortedListsTest {

    @Test
    void examples() {
        assertEquals(List.of(1, 1, 2, 3, 4, 4), ListNode.toList(MergeTwoSortedLists.mergeTwoLists(ListNode.of(1, 2, 4), ListNode.of(1, 3, 4))));
        assertEquals(List.of(), ListNode.toList(MergeTwoSortedLists.mergeTwoLists(null, null)));
        assertEquals(List.of(0), ListNode.toList(MergeTwoSortedLists.mergeTwoListsRecursive(null, ListNode.of(0))));
    }

    @Test
    void randomSortedLists() {
        Random r = new Random(21);
        for (int t = 0; t < 500; t++) {
            int[] a = r.ints(r.nextInt(8), -10, 10).sorted().toArray();
            int[] b = r.ints(r.nextInt(8), -10, 10).sorted().toArray();
            List<Integer> expected = new ArrayList<>();
            for (int v : a) expected.add(v);
            for (int v : b) expected.add(v);
            Collections.sort(expected);
            assertEquals(expected, ListNode.toList(MergeTwoSortedLists.mergeTwoLists(ListNode.of(a), ListNode.of(b))));
            assertEquals(expected, ListNode.toList(MergeTwoSortedLists.mergeTwoListsRecursive(ListNode.of(a), ListNode.of(b))));
        }
    }
}
