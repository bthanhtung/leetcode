package LinkedListCycleII;

import com.tungdadev.common.ListNode;

import java.util.HashSet;
import java.util.Set;

/*
 * 142. Linked List Cycle II — https://leetcode.com/problems/linked-list-cycle-ii/
 * Return the node where the cycle begins, or null if the list has no cycle. Do not modify the list.
 * Follow up: O(1) memory.
 * Input: [3,2,0,-4], tail -> index 1 -> node 2 · [1,2], tail -> index 0 -> node 1 · [1] -> null
 * Explanation: README.md
 */
public class LinkedListCycleII {
    public static void main(String[] args) {
        ListNode head = ListNode.of(3, 2, 0, -4);
        head.next.next.next.next = head.next;
        System.out.println(detectCycle(head).val);          // 2
    }

    /* Approach 1 - the first node seen twice is the cycle's entrance. Time O(n), space O(n). */
    public static ListNode detectCycleSet(ListNode head) {
        Set<ListNode> seen = new HashSet<>();
        for (ListNode p = head; p != null; p = p.next) {
            if (!seen.add(p)) return p;
        }
        return null;
    }

    /*
     * Approach 2 - Floyd. Phase 1: slow and fast meet inside the cycle. Phase 2: a pointer from head and one from the
     * meeting point, both one step at a time, meet exactly at the entrance. Time O(n), space O(1).
     */
    public static ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode p = head;
                while (p != slow) {
                    p = p.next;
                    slow = slow.next;
                }
                return p;
            }
        }
        return null;
    }
}
