package SortList;

import java.util.ArrayList;
import java.util.List;

/**
 * Singly linked list node as used by LeetCode, plus helpers to build and read lists in tests.
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /** of(1, 2, 3) -> 1 -> 2 -> 3; of() -> null. */
    public static ListNode of(int... values) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int v : values) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    /** 1 -> 2 -> 3 -> [1, 2, 3] (stops after 10^5 nodes so a cycle can't hang a test). */
    public static List<Integer> toList(ListNode head) {
        List<Integer> out = new ArrayList<>();
        for (ListNode n = head; n != null && out.size() < 100_000; n = n.next) {
            out.add(n.val);
        }
        return out;
    }

    @Override
    public String toString() {
        return toList(this).toString();
    }
}
