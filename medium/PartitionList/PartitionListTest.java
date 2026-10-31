package PartitionList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartitionListTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(1, 2, 2, 4, 3, 5), ListNode.toList(PartitionList.partition(ListNode.of(1, 4, 3, 2, 5, 2), 3)));
        assertEquals(List.of(1, 2), ListNode.toList(PartitionList.partition(ListNode.of(2, 1), 2)));
        Random r = new Random(86);
        for (int t = 0; t < 1000; t++) {
            int[] values = r.ints(r.nextInt(12), 0, 6).toArray();
            int x = r.nextInt(7);
            List<Integer> expected = new ArrayList<>();
            for (int v : values) if (v < x) expected.add(v);
            for (int v : values) if (v >= x) expected.add(v);
            assertEquals(expected, ListNode.toList(PartitionList.partition(ListNode.of(values), x)));
            assertEquals(expected, ListNode.toList(PartitionList.partitionValues(ListNode.of(values), x)));
        }
    }
}
