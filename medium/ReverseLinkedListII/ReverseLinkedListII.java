package ReverseLinkedListII;

import com.tungdadev.common.ListNode;

/*
 * 92. Reverse Linked List II — https://leetcode.com/problems/reverse-linked-list-ii/
 * Reverse the nodes from position left to position right (1-indexed) and return the list. Follow up: one pass.
 * Input: [1,2,3,4,5], left = 2, right = 4 -> [1,4,3,2,5] · [5], 1, 1 -> [5]
 * Explanation: README.md
 */
public class ReverseLinkedListII {
    public static void main(String[] args) {
        System.out.println(ListNode.toList(reverseBetween(ListNode.of(1, 2, 3, 4, 5), 2, 4)));    // [1, 4, 3, 2, 5]
    }

    /*
     * Approach 1 - stop at the node before the range (pre). Then repeatedly take the node after `start` and move it
     * to the front of the range (right after pre). right - left moves, one pass, O(1) space.
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head), pre = dummy;
        for (int i = 1; i < left; i++) pre = pre.next;
        ListNode start = pre.next;                    // becomes the tail of the reversed part
        for (int i = 0; i < right - left; i++) {
            ListNode move = start.next;
            start.next = move.next;
            move.next = pre.next;
            pre.next = move;
        }
        return dummy.next;
    }

    /* Approach 2 - recursion: shift left down to 1, then reverse the first k nodes recursively. */
    public static ListNode reverseBetweenRecursive(ListNode head, int left, int right) {
        if (left == 1) return reverseFirst(head, right);
        head.next = reverseBetweenRecursive(head.next, left - 1, right - 1);
        return head;
    }

    private static ListNode successor;

    private static ListNode reverseFirst(ListNode head, int k) {
        if (k == 1) {
            successor = head.next;
            return head;
        }
        ListNode newHead = reverseFirst(head.next, k - 1);
        head.next.next = head;
        head.next = successor;
        return newHead;
    }
}
