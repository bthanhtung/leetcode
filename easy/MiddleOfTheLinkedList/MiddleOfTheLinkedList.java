package MiddleOfTheLinkedList;

import java.util.ArrayList;
import java.util.List;

/*
 * 876. Middle of the Linked List — https://leetcode.com/problems/middle-of-the-linked-list/
 * Return the middle node of a singly linked list; with two middle nodes, return the second one.
 * Input: [1,2,3,4,5] -> [3,4,5] · [1,2,3,4,5,6] -> [4,5,6]
 * Explanation: README.md
 */
public class MiddleOfTheLinkedList {
    public static void main(String[] args) {
        System.out.println(middleNode(ListNode.of(1, 2, 3, 4, 5)));          // [3, 4, 5]
        System.out.println(middleNodeCount(ListNode.of(1, 2, 3, 4, 5, 6)));  // [4, 5, 6]
    }

    /* Approach 1 - count the nodes, then walk n / 2 steps. Two passes. Time O(n), space O(1). */
    public static ListNode middleNodeCount(ListNode head) {
        int n = 0;
        for (ListNode p = head; p != null; p = p.next) n++;
        ListNode p = head;
        for (int i = 0; i < n / 2; i++) p = p.next;
        return p;
    }

    /* Approach 2 - copy node references into a list, return the middle one. Time O(n), space O(n). */
    public static ListNode middleNodeArray(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        for (ListNode p = head; p != null; p = p.next) nodes.add(p);
        return nodes.get(nodes.size() / 2);
    }

    /* Approach 3 - slow moves 1, fast moves 2: when fast reaches the end, slow is in the middle. One pass, O(1) space. */
    public static ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
