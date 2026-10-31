package ReorderList;

import java.util.ArrayList;
import java.util.List;

/*
 * 143. Reorder List — https://leetcode.com/problems/reorder-list/
 * Reorder L0 → L1 → … → Ln-1 → Ln into L0 → Ln → L1 → Ln-1 → L2 → … in place (nodes, not values).
 * Input: [1,2,3,4] -> [1,4,2,3] · [1,2,3,4,5] -> [1,5,2,4,3]
 * Explanation: README.md
 */
public class ReorderList {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 4, 5);
        reorderList(head);
        System.out.println(head);       // [1, 5, 2, 4, 3]
    }

    /* Approach 1 - copy node references into a list, relink from both ends. Time O(n), space O(n). */
    public static void reorderListArray(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        for (ListNode p = head; p != null; p = p.next) nodes.add(p);
        int i = 0, j = nodes.size() - 1;
        while (i < j) {
            nodes.get(i).next = nodes.get(j);
            i++;
            if (i == j) break;
            nodes.get(j).next = nodes.get(i);
            j--;
        }
        if (!nodes.isEmpty()) nodes.get(i).next = null;
    }

    /* Approach 2 - find the middle, reverse the second half, then weave the two halves. Time O(n), space O(1). */
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = reverse(slow.next);
        slow.next = null;                               // cut: first half ends at slow
        ListNode first = head;
        while (second != null) {
            ListNode n1 = first.next, n2 = second.next;
            first.next = second;
            second.next = n1;
            first = n1;
            second = n2;
        }
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
