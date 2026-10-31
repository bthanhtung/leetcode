package ReverseNodesInKGroup;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseNodesInKGroupTest {

    @Test
    void allLengthsAndK() {
        for (int n = 0; n <= 12; n++) {
            for (int k = 1; k <= 5; k++) {
                int[] values = IntStream.rangeClosed(1, n).toArray();
                List<Integer> expected = new ArrayList<>();
                for (int start = 0; start < n; start += k) {
                    if (start + k <= n) for (int i = start + k - 1; i >= start; i--) expected.add(values[i]);
                    else for (int i = start; i < n; i++) expected.add(values[i]);
                }
                assertEquals(expected, ListNode.toList(ReverseNodesInKGroup.reverseKGroup(ListNode.of(values), k)));
                assertEquals(expected, ListNode.toList(ReverseNodesInKGroup.reverseKGroupRecursive(ListNode.of(values), k)));
            }
        }
    }
}
