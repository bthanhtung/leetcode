package RemoveDuplicatesFromSortedListII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveDuplicatesFromSortedListIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(1, 2, 5), ListNode.toList(RemoveDuplicatesFromSortedListII.deleteDuplicates(ListNode.of(1, 2, 3, 3, 4, 4, 5))));
        assertEquals(List.of(2, 3), ListNode.toList(RemoveDuplicatesFromSortedListII.deleteDuplicates(ListNode.of(1, 1, 1, 2, 3))));
        Random r = new Random(82);
        for (int t = 0; t < 2000; t++) {
            int[] a = r.ints(r.nextInt(10), 0, 5).sorted().toArray();
            List<Integer> expected = new ArrayList<>();
            for (int x : a) if (Arrays.stream(a).filter(y -> y == x).count() == 1) expected.add(x);
            assertEquals(expected, ListNode.toList(RemoveDuplicatesFromSortedListII.deleteDuplicates(ListNode.of(a))));
            assertEquals(expected, ListNode.toList(RemoveDuplicatesFromSortedListII.deleteDuplicatesRecursive(ListNode.of(a))));
        }
    }
}
