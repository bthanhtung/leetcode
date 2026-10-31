package SortList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 148. Sort List — https://leetcode.com/problems/sort-list/
 * Sort a linked list in ascending order. Follow up: O(n log n) time and O(1) memory.
 * Input: [4,2,1,3] -> [1,2,3,4] · [-1,5,3,4,0] -> [-1,0,3,4,5] · [] -> []
 * Explanation: README.md
 */
public class SortList {
    public static void main(String[] args) {
        System.out.println(sortList(ListNode.of(4, 2, 1, 3)));                 // [1, 2, 3, 4]
        System.out.println(sortListBottomUp(ListNode.of(-1, 5, 3, 4, 0)));     // [-1, 0, 3, 4, 5]
    }

    /* Approach 1 - copy values out, sort, write back. Time O(n log n), space O(n). */
    public static ListNode sortListCopy(ListNode head) {
        List<Integer> values = new ArrayList<>();
        for (ListNode p = head; p != null; p = p.next) values.add(p.val);
        Collections.sort(values);
        ListNode p = head;
        for (int v : values) {
            p.val = v;
            p = p.next;
        }
        return head;
    }

    /* Approach 2 - top-down merge sort: split at the middle (slow/fast), sort both halves, merge. Space O(log n) stack. */
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = slow.next;
        slow.next = null;
        return merge(sortList(head), sortList(second));
    }

    /*
     * Approach 3 - bottom-up merge sort: merge runs of size 1, 2, 4, ... in passes over the list. No recursion,
     * O(1) extra space. Time O(n log n).
     */
    public static ListNode sortListBottomUp(ListNode head) {
        int length = 0;
        for (ListNode p = head; p != null; p = p.next) length++;
        ListNode dummy = new ListNode(0, head);
        for (int size = 1; size < length; size *= 2) {
            ListNode tail = dummy, curr = dummy.next;
            while (curr != null) {
                ListNode left = curr;
                ListNode right = split(left, size);
                curr = split(right, size);
                tail.next = merge(left, right);
                while (tail.next != null) tail = tail.next;
            }
        }
        return dummy.next;
    }

    /** Cuts the list after n nodes and returns the head of the rest. */
    private static ListNode split(ListNode head, int n) {
        for (int i = 1; head != null && i < n; i++) head = head.next;
        if (head == null) return null;
        ListNode rest = head.next;
        head.next = null;
        return rest;
    }

    private static ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = a != null ? a : b;
        return dummy.next;
    }
}
