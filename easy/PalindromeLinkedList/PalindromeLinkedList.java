package PalindromeLinkedList;

import com.tungdadev.common.ListNode;

import java.util.ArrayList;
import java.util.List;

/*
 * 234. Palindrome Linked List — https://leetcode.com/problems/palindrome-linked-list/
 * Return true if a singly linked list reads the same forward and backward. Follow up: O(n) time and O(1) space.
 * Input: [1,2,2,1] -> true · [1,2] -> false
 * Explanation: README.md
 */
public class PalindromeLinkedList {
    public static void main(String[] args) {
        System.out.println(isPalindrome(ListNode.of(1, 2, 2, 1)));         // true
        System.out.println(isPalindromeArray(ListNode.of(1, 2)));          // false
    }

    /* Approach 1 - copy values into a list, two pointers from both ends. Time O(n), space O(n). */
    public static boolean isPalindromeArray(ListNode head) {
        List<Integer> values = new ArrayList<>();
        for (ListNode p = head; p != null; p = p.next) values.add(p.val);
        for (int i = 0, j = values.size() - 1; i < j; i++, j--) {
            if (!values.get(i).equals(values.get(j))) return false;
        }
        return true;
    }

    /* Approach 2 - recursion reaches the tail first; compare it with a front pointer that moves forward as calls return. */
    public static boolean isPalindromeRecursive(ListNode head) {
        ListNode[] front = {head};
        return check(head, front);
    }

    private static boolean check(ListNode node, ListNode[] front) {
        if (node == null) return true;
        if (!check(node.next, front)) return false;
        boolean same = node.val == front[0].val;
        front[0] = front[0].next;
        return same;
    }

    /*
     * Approach 3 - find the middle (slow/fast), reverse the second half, compare the halves, reverse back to restore
     * the list. Time O(n), space O(1).
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {     // slow stops at the end of the first half
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode secondHead = reverse(slow.next);
        boolean result = true;
        for (ListNode p = head, q = secondHead; q != null; p = p.next, q = q.next) {
            if (p.val != q.val) {
                result = false;
                break;
            }
        }
        slow.next = reverse(secondHead);                            // leave the input as we found it
        return result;
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
