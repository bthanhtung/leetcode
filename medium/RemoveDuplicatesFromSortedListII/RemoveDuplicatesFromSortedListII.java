package RemoveDuplicatesFromSortedListII;

/*
 * 82. Remove Duplicates from Sorted List II — https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
 * Sorted linked list: delete every node whose value appears more than once, keeping only distinct values.
 * Input: [1,2,3,3,4,4,5] -> [1,2,5] · [1,1,1,2,3] -> [2,3]
 * Explanation: README.md
 */
public class RemoveDuplicatesFromSortedListII {
    public static void main(String[] args) {
        System.out.println(ListNode.toList(deleteDuplicates(ListNode.of(1, 2, 3, 3, 4, 4, 5))));   // [1, 2, 5]
    }

    /* Approach 1 - prev is the last node kept; when a run of equal values starts, skip the whole run. O(n), O(1). */
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0, head), prev = dummy;
        while (prev.next != null) {
            ListNode cur = prev.next;
            if (cur.next != null && cur.next.val == cur.val) {
                int dup = cur.val;
                while (cur != null && cur.val == dup) cur = cur.next;
                prev.next = cur;                 // drop the run, prev stays
            } else {
                prev = cur;
            }
        }
        return dummy.next;
    }

    /* Approach 2 - recursion: a duplicated head is skipped with its run; a distinct head links to the rest. */
    public static ListNode deleteDuplicatesRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        if (head.val != head.next.val) {
            head.next = deleteDuplicatesRecursive(head.next);
            return head;
        }
        ListNode p = head;
        while (p != null && p.val == head.val) p = p.next;
        return deleteDuplicatesRecursive(p);
    }
}
