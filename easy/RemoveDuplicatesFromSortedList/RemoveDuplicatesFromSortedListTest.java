package RemoveDuplicatesFromSortedList;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveDuplicatesFromSortedListTest {

    @Test
    void examples() {
        assertEquals(List.of(1, 2), ListNode.toList(RemoveDuplicatesFromSortedList.deleteDuplicates(ListNode.of(1, 1, 2))));
        assertEquals(List.of(1, 2, 3), ListNode.toList(RemoveDuplicatesFromSortedList.deleteDuplicates(ListNode.of(1, 1, 2, 3, 3))));
        assertEquals(List.of(), ListNode.toList(RemoveDuplicatesFromSortedList.deleteDuplicates(null)));
        assertEquals(List.of(5), ListNode.toList(RemoveDuplicatesFromSortedList.deleteDuplicatesRecursive(ListNode.of(5, 5, 5))));
    }

    @Test
    void randomAgree() {
        Random r = new Random(83);
        for (int t = 0; t < 500; t++) {
            int[] a = r.ints(r.nextInt(12), 0, 5).sorted().toArray();
            List<Integer> expected = IntStream.of(a).distinct().boxed().collect(Collectors.toList());
            assertEquals(expected, ListNode.toList(RemoveDuplicatesFromSortedList.deleteDuplicates(ListNode.of(a))));
            assertEquals(expected, ListNode.toList(RemoveDuplicatesFromSortedList.deleteDuplicatesRecursive(ListNode.of(a))));
        }
    }
}
