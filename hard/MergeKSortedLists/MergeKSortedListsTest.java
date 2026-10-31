package MergeKSortedLists;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeKSortedListsTest {

    private static ListNode[] build(int[][] arrays) {
        ListNode[] lists = new ListNode[arrays.length];
        for (int i = 0; i < arrays.length; i++) lists[i] = ListNode.of(arrays[i]);
        return lists;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(1, 1, 2, 3, 4, 4, 5, 6), ListNode.toList(MergeKSortedLists.mergeKLists(build(new int[][]{{1, 4, 5}, {1, 3, 4}, {2, 6}}))));
        assertEquals(List.of(), ListNode.toList(MergeKSortedLists.mergeKLists(new ListNode[0])));
        assertEquals(List.of(), ListNode.toList(MergeKSortedLists.mergeKListsDivide(new ListNode[]{null})));
        Random r = new Random(23);
        for (int t = 0; t < 500; t++) {
            int[][] arrays = new int[r.nextInt(8)][];
            for (int i = 0; i < arrays.length; i++) arrays[i] = r.ints(r.nextInt(6), -10, 10).sorted().toArray();
            List<Integer> expected = ListNode.toList(MergeKSortedLists.mergeKListsSort(build(arrays)));
            assertEquals(expected, ListNode.toList(MergeKSortedLists.mergeKLists(build(arrays))));
            assertEquals(expected, ListNode.toList(MergeKSortedLists.mergeKListsDivide(build(arrays))));
        }
    }
}
