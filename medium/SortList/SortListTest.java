package SortList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortListTest {

    @Test
    void random() {
        assertEquals(List.of(1, 2, 3, 4), ListNode.toList(SortList.sortList(ListNode.of(4, 2, 1, 3))));
        assertEquals(List.of(), ListNode.toList(SortList.sortList(null)));
        Random r = new Random(148);
        for (int t = 0; t < 1000; t++) {
            int[] values = r.ints(r.nextInt(30), -20, 20).toArray();
            List<Integer> expected = IntStream.of(values).sorted().boxed().toList();
            assertEquals(expected, ListNode.toList(SortList.sortList(ListNode.of(values))));
            assertEquals(expected, ListNode.toList(SortList.sortListBottomUp(ListNode.of(values))));
            assertEquals(expected, ListNode.toList(SortList.sortListCopy(ListNode.of(values))));
        }
    }
}
