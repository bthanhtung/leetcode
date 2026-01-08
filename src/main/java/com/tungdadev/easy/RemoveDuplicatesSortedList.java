package com.tungdadev.easy;

/**
 * @author TungDaDev
 * @since 09/01/2026 - 00:47
 */

/**
 * Link: https://leetcode.com/problems/remove-duplicates-from-sorted-list/description/
 */
public class RemoveDuplicatesSortedList {
    public static void main(String[] args) {

        System.out.println(deleteDuplicates(
                new RemoveDuplicatesSortedList().new ListNode(1,
                        new RemoveDuplicatesSortedList().new ListNode(1,
                                new RemoveDuplicatesSortedList().new ListNode(2,
                                        new RemoveDuplicatesSortedList().new ListNode(3,
                                                new RemoveDuplicatesSortedList().new ListNode(3)
                                        )
                                )
                        )
                )
        )); // [1,2,3]
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }


    // Definition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
