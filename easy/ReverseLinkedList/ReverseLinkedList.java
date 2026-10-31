package ReverseLinkedList;

import com.tungdadev.common.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 206. Reverse Linked List — https://leetcode.com/problems/reverse-linked-list/
 * Given the head of a singly linked list, reverse the list and return the new head.
 * Input: [1,2,3,4,5] -> [5,4,3,2,1] · [1,2] -> [2,1] · [] -> []
 * Explanation: README.md
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        System.out.println(reverseList(ListNode.of(1, 2, 3, 4, 5)));          // [5, 4, 3, 2, 1]
        System.out.println(reverseListRecursive(ListNode.of(1, 2)));          // [2, 1]
    }

    /* Approach 1 - walk once, turning each next pointer backwards. Time O(n), space O(1). */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next;   // 1. remember the rest
            curr.next = prev;            // 2. turn the arrow around
            prev = curr;                 // 3. step forward
            curr = next;
        }
        return prev;
    }

    /* Approach 2 - reverse the rest, then hang head after its old successor. Time O(n), space O(n) stack. */
    public static ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;           // the old successor now points back to head
        head.next = null;
        return newHead;
    }

    /* Approach 3 - push nodes on a stack, pop them to relink. Time O(n), space O(n). */
    public static ListNode reverseListStack(ListNode head) {
        Deque<ListNode> stack = new ArrayDeque<>();
        for (ListNode p = head; p != null; p = p.next) stack.push(p);
        ListNode dummy = new ListNode(0), tail = dummy;
        while (!stack.isEmpty()) {
            tail.next = stack.pop();
            tail = tail.next;
        }
        tail.next = null;
        return dummy.next;
    }
}
