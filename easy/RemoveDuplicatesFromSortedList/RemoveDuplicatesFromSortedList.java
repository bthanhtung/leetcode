package RemoveDuplicatesFromSortedList;

import com.tungdadev.common.ListNode;

/*
 * 83. Remove Duplicates from Sorted List — https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 * Given the head of a sorted linked list, delete all duplicates such that each element appears only once. Return the list sorted.
 * Input: [1,1,2] -> [1,2] · [1,1,2,3,3] -> [1,2,3]
 * Explanation: README.md
 */
public class RemoveDuplicatesFromSortedList {
    public static void main(String[] args) {
        System.out.println(deleteDuplicates(ListNode.of(1, 1, 2, 3, 3)));            // [1, 2, 3]
        System.out.println(deleteDuplicatesRecursive(ListNode.of(1, 1, 2)));         // [1, 2]
    }

    /* Approach 1 - one pointer: while the next node repeats the current value, skip it. Time O(n), space O(1). */
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            if (current.next.val == current.val) {
                current.next = current.next.next;      // unlink the duplicate, stay on current
            } else {
                current = current.next;
            }
        }
        return head;
    }

    /* Approach 2 - recursive: clean the rest first, then drop head if it equals the new next. Time O(n), space O(n) stack. */
    public static ListNode deleteDuplicatesRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        head.next = deleteDuplicatesRecursive(head.next);
        return head.val == head.next.val ? head.next : head;
    }
}
