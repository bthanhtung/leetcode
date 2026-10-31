package OddEvenLinkedList;

import com.tungdadev.common.ListNode;

import java.util.ArrayList;
import java.util.List;

/*
 * 328. Odd Even Linked List — https://leetcode.com/problems/odd-even-linked-list/
 * Group the nodes at odd positions (1st, 3rd, ...) followed by those at even positions, keeping relative order.
 * O(1) extra space and O(n) time.
 * Input: [1,2,3,4,5] -> [1,3,5,2,4] · [2,1,3,5,6,4,7] -> [2,3,6,7,1,5,4]
 * Explanation: README.md
 */
public class OddEvenLinkedList {
    public static void main(String[] args) {
        System.out.println(oddEvenList(ListNode.of(1, 2, 3, 4, 5)));              // [1, 3, 5, 2, 4]
        System.out.println(oddEvenListCollect(ListNode.of(2, 1, 3, 5, 6, 4, 7))); // [2, 3, 6, 7, 1, 5, 4]
    }

    /* Approach 1 - collect node references, relink odd positions then even ones. O(n) extra space. */
    public static ListNode oddEvenListCollect(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        for (ListNode p = head; p != null; p = p.next) nodes.add(p);
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int start = 0; start < 2; start++) {
            for (int i = start; i < nodes.size(); i += 2) {
                tail.next = nodes.get(i);
                tail = tail.next;
            }
        }
        tail.next = null;
        return dummy.next;
    }

    /* Approach 2 - grow an odd chain and an even chain at the same time, then attach evens after odds. O(1) space. */
    public static ListNode oddEvenList(ListNode head) {
        if (head == null) return null;
        ListNode odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
