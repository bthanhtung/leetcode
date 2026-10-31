package MergeTwoSortedLists;

/*
 * 21. Merge Two Sorted Lists — https://leetcode.com/problems/merge-two-sorted-lists/
 * Merge two sorted linked lists into one sorted list made by splicing together the nodes of the two lists. Return its head.
 * Input: [1,2,4] + [1,3,4] -> [1,1,2,3,4,4] · [] + [] -> [] · [] + [0] -> [0]
 * Explanation: README.md
 */
public class MergeTwoSortedLists {
    public static void main(String[] args) {
        System.out.println(mergeTwoLists(ListNode.of(1, 2, 4), ListNode.of(1, 3, 4)));            // [1, 1, 2, 3, 4, 4]
        System.out.println(mergeTwoListsRecursive(ListNode.of(), ListNode.of(0)));                // [0]
    }

    /*
     * Approach 1 - iterative with a dummy head: repeatedly attach the smaller front node, then the leftover tail.
     * Time O(m + n), space O(1) - nodes are re-linked, not copied.
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        tail.next = list1 != null ? list1 : list2;
        return dummy.next;
    }

    /* Approach 2 - recursive: the smaller head stays in front, followed by the merge of everything else. Time O(m + n), space O(m + n) stack. */
    public static ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        }
        list2.next = mergeTwoListsRecursive(list1, list2.next);
        return list2;
    }
}
