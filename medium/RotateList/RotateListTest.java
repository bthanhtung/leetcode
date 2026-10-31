package RotateList;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotateListTest {

    @Test
    void allLengthsAndShifts() {
        assertEquals(List.of(4, 5, 1, 2, 3), ListNode.toList(RotateList.rotateRight(ListNode.of(1, 2, 3, 4, 5), 2)));
        assertEquals(List.of(2, 0, 1), ListNode.toList(RotateList.rotateRight(ListNode.of(0, 1, 2), 4)));
        assertEquals(List.of(), ListNode.toList(RotateList.rotateRight(null, 3)));
        for (int len = 1; len <= 6; len++) {
            int[] v = new int[len];
            for (int i = 0; i < len; i++) v[i] = i;
            for (int k = 0; k <= 13; k++) {
                List<Integer> expected = new ArrayList<>();
                for (int i = 0; i < len; i++) expected.add(v[((i - k) % len + len) % len]);
                assertEquals(expected, ListNode.toList(RotateList.rotateRight(ListNode.of(v), k)));
            }
        }
        assertEquals(List.of(2, 1), ListNode.toList(RotateList.rotateRight(ListNode.of(1, 2), 2_000_000_001)));
    }
}
