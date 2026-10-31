package RemoveLinkedListElements;

import com.tungdadev.common.ListNode;

/*
 * 203. Remove Linked List Elements — https://leetcode.com/problems/remove-linked-list-elements/
 * Remove all nodes whose value equals val and return the new head.
 * Input: [1,2,6,3,4,5,6], val = 6 -> [1,2,3,4,5] · [7,7,7,7], val = 7 -> []
 * Explanation: README.md
 */
public class RemoveLinkedListElements {
    public static void main(String[] args) {
        System.out.println(removeElements(ListNode.of(1, 2, 6, 3, 4, 5, 6), 6));       // [1, 2, 3, 4, 5]
        System.out.println(removeElementsRecursive(ListNode.of(7, 7, 7, 7), 7));      // null
    }

    /* Approach 1 - a dummy node before head so removing the head is not a special case. Time O(n), space O(1). */
    public static ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        while (prev.next != null) {
            if (prev.next.val == val) {
                prev.next = prev.next.next;
            } else {
                prev = prev.next;
            }
        }
        return dummy.next;
    }

    /* Approach 2 - clean the rest, then keep or skip head. Time O(n), space O(n) stack. */
    public static ListNode removeElementsRecursive(ListNode head, int val) {
        if (head == null) return null;
        head.next = removeElementsRecursive(head.next, val);
        return head.val == val ? head.next : head;
    }
}
