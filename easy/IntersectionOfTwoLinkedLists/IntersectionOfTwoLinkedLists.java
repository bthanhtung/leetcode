package IntersectionOfTwoLinkedLists;

import java.util.HashSet;
import java.util.Set;

/*
 * 160. Intersection of Two Linked Lists — https://leetcode.com/problems/intersection-of-two-linked-lists/
 * Return the node at which two singly linked lists intersect (same node object), or null. Lists have no cycles and
 * must keep their structure. Follow up: O(m + n) time, O(1) memory.
 * Input: A = 4->1->8->4->5, B = 5->6->1->8->4->5 sharing 8->4->5 -> node 8
 * Explanation: README.md
 */
public class IntersectionOfTwoLinkedLists {
    public static void main(String[] args) {
        ListNode common = ListNode.of(8, 4, 5);
        ListNode a = new ListNode(4, new ListNode(1, common));
        ListNode b = new ListNode(5, new ListNode(6, new ListNode(1, common)));
        System.out.println(getIntersectionNode(a, b).val);          // 8
    }

    /* Approach 1 - remember every node of A, walk B until a remembered node. Time O(m + n), space O(m). */
    public static ListNode getIntersectionNodeSet(ListNode headA, ListNode headB) {
        Set<ListNode> seen = new HashSet<>();
        for (ListNode p = headA; p != null; p = p.next) seen.add(p);
        for (ListNode p = headB; p != null; p = p.next) {
            if (seen.contains(p)) return p;
        }
        return null;
    }

    /* Approach 2 - skip the longer list's extra head start, then walk both together. Time O(m + n), space O(1). */
    public static ListNode getIntersectionNodeLength(ListNode headA, ListNode headB) {
        int lenA = length(headA), lenB = length(headB);
        while (lenA > lenB) { headA = headA.next; lenA--; }
        while (lenB > lenA) { headB = headB.next; lenB--; }
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }

    private static int length(ListNode p) {
        int n = 0;
        for (; p != null; p = p.next) n++;
        return n;
    }

    /*
     * Approach 3 - two pointers; when one reaches the end it restarts at the other list's head. Both then walk
     * a + c + b steps before meeting at the intersection (or both reach null together). Time O(m + n), space O(1).
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p = headA, q = headB;
        while (p != q) {
            p = p == null ? headB : p.next;
            q = q == null ? headA : q.next;
        }
        return p;
    }
}
