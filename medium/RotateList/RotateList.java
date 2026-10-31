package RotateList;

/*
 * 61. Rotate List — https://leetcode.com/problems/rotate-list/
 * Rotate the linked list to the right by k places.
 * Input: [1,2,3,4,5], k = 2 -> [4,5,1,2,3] · [0,1,2], k = 4 -> [2,0,1]
 * Explanation: README.md
 */
public class RotateList {
    public static void main(String[] args) {
        System.out.println(ListNode.toList(rotateRight(ListNode.of(1, 2, 3, 4, 5), 2)));     // [4, 5, 1, 2, 3]
    }

    /*
     * Find the length and the tail, link tail -> head to form a ring, walk length - k % length steps from the tail
     * to the new tail, and cut there. O(n), O(1).
     */
    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) return head;
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }
        tail.next = head;                                    // ring
        for (int steps = length - k % length; steps > 0; steps--) tail = tail.next;
        ListNode newHead = tail.next;
        tail.next = null;
        return newHead;
    }
}
