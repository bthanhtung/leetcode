package PartitionList;

/*
 * 86. Partition List — https://leetcode.com/problems/partition-list/
 * Rearrange the list so all nodes with value < x come before nodes with value >= x, keeping the original relative
 * order inside each part.
 * Input: [1,4,3,2,5,2], x = 3 -> [1,2,2,4,3,5] · [2,1], x = 2 -> [1,2]
 * Explanation: README.md
 */
public class PartitionList {
    public static void main(String[] args) {
        System.out.println(partition(ListNode.of(1, 4, 3, 2, 5, 2), 3));      // [1, 2, 2, 4, 3, 5]
        System.out.println(partitionValues(ListNode.of(2, 1), 2));            // [1, 2]
    }

    /* Approach 1 - build two lists (before / after) with dummy heads, then join them. Time O(n), space O(1). */
    public static ListNode partition(ListNode head, int x) {
        ListNode beforeHead = new ListNode(0), afterHead = new ListNode(0);
        ListNode before = beforeHead, after = afterHead;
        for (ListNode p = head; p != null; p = p.next) {
            if (p.val < x) {
                before.next = p;
                before = p;
            } else {
                after.next = p;
                after = p;
            }
        }
        after.next = null;                        // the last "after" node may still point into the before list
        before.next = afterHead.next;
        return beforeHead.next;
    }

    /* Approach 2 - two passes over the values, writing the small ones first, then the rest. Changes values, not nodes. */
    public static ListNode partitionValues(ListNode head, int x) {
        java.util.List<Integer> values = ListNode.toList(head);
        ListNode p = head;
        for (int pass = 0; pass < 2; pass++) {
            for (int v : values) {
                if ((v < x) == (pass == 0)) {
                    p.val = v;
                    p = p.next;
                }
            }
        }
        return head;
    }
}
