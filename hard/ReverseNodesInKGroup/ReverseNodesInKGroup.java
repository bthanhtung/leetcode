package ReverseNodesInKGroup;

/*
 * 25. Reverse Nodes in k-Group — https://leetcode.com/problems/reverse-nodes-in-k-group/
 * Reverse the nodes of a linked list k at a time; a final group with fewer than k nodes stays as it is.
 * Only change links, not values. Follow up: O(1) extra memory.
 * Input: [1,2,3,4,5], k = 2 -> [2,1,4,3,5] · k = 3 -> [3,2,1,4,5]
 * Explanation: README.md
 */
public class ReverseNodesInKGroup {
    public static void main(String[] args) {
        System.out.println(reverseKGroup(ListNode.of(1, 2, 3, 4, 5), 2));            // [2, 1, 4, 3, 5]
        System.out.println(reverseKGroupRecursive(ListNode.of(1, 2, 3, 4, 5), 3));   // [3, 2, 1, 4, 5]
    }

    /*
     * Approach 1 - iterative: groupPrev is the node before the current group. Check that k nodes exist, reverse them
     * in place, reconnect, and move groupPrev to the group's new tail. Time O(n), space O(1).
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head), groupPrev = dummy;
        while (true) {
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) kth = kth.next;
            if (kth == null) break;                                // fewer than k nodes left
            ListNode groupNext = kth.next;
            ListNode prev = groupNext, curr = groupPrev.next;      // reverse the group, pointing its first node to groupNext
            while (curr != groupNext) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            ListNode oldFirst = groupPrev.next;
            groupPrev.next = kth;                                  // kth is the new first node
            groupPrev = oldFirst;                                  // the old first node is now the group's tail
        }
        return dummy.next;
    }

    /* Approach 2 - recursive: reverse the first k nodes, attach the recursively processed rest. Space O(n / k). */
    public static ListNode reverseKGroupRecursive(ListNode head, int k) {
        ListNode node = head;
        for (int i = 0; i < k; i++) {
            if (node == null) return head;
            node = node.next;
        }
        ListNode prev = reverseKGroupRecursive(node, k), curr = head;
        for (int i = 0; i < k; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
