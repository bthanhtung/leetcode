package LinkedListCycle;

import com.tungdadev.common.ListNode;

import java.util.HashSet;
import java.util.Set;

/*
 * 141. Linked List Cycle — https://leetcode.com/problems/linked-list-cycle/
 * Return true if the linked list has a cycle (some node can be reached again by following next). Follow up: O(1) memory.
 * Input: [3,2,0,-4] with the tail linked to index 1 -> true · [1] -> false
 * Explanation: README.md
 */
public class LinkedListCycle {
    public static void main(String[] args) {
        ListNode head = ListNode.of(3, 2, 0, -4);
        head.next.next.next.next = head.next;          // -4 -> 2
        System.out.println(hasCycle(head));             // true
        System.out.println(hasCycleSet(ListNode.of(1))); // false
    }

    /* Approach 1 - remember visited nodes; meeting one again means a cycle. Time O(n), space O(n). */
    public static boolean hasCycleSet(ListNode head) {
        Set<ListNode> seen = new HashSet<>();
        for (ListNode p = head; p != null; p = p.next) {
            if (!seen.add(p)) return true;
        }
        return false;
    }

    /* Approach 2 - slow moves 1, fast moves 2; inside a cycle fast gains one node per step and must catch slow. O(1) space. */
    public static boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
