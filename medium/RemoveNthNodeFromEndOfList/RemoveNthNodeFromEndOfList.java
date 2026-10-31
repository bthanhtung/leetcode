package RemoveNthNodeFromEndOfList;

import com.tungdadev.common.ListNode;

/*
 * 19. Remove Nth Node From End of List — https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * Remove the n-th node from the end of the list and return its head. Follow up: one pass.
 * Input: [1,2,3,4,5], n = 2 -> [1,2,3,5] · [1], n = 1 -> [] · [1,2], n = 1 -> [1]
 * Explanation: README.md
 */
public class RemoveNthNodeFromEndOfList {
    public static void main(String[] args) {
        System.out.println(ListNode.toList(removeNthFromEnd(ListNode.of(1, 2, 3, 4, 5), 2)));    // [1, 2, 3, 5]
    }

    /*
     * Approach 1 - one pass: move fast n steps ahead, then move both until fast is the last node; slow then stands
     * just before the node to delete. The dummy head handles deleting the first node. O(L), O(1).
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head), fast = dummy, slow = dummy;
        for (int i = 0; i < n; i++) fast = fast.next;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    /* Approach 2 - two passes: count L, then stop before index L - n. */
    public static ListNode removeNthFromEndTwoPass(ListNode head, int n) {
        int length = 0;
        for (ListNode p = head; p != null; p = p.next) length++;
        ListNode dummy = new ListNode(0, head), prev = dummy;
        for (int i = 0; i < length - n; i++) prev = prev.next;
        prev.next = prev.next.next;
        return dummy.next;
    }
}
