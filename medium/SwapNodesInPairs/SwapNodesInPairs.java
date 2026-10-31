package SwapNodesInPairs;

/*
 * 24. Swap Nodes in Pairs — https://leetcode.com/problems/swap-nodes-in-pairs/
 * Swap every two adjacent nodes of a linked list and return its head. Change nodes, not values.
 * Input: [1,2,3,4] -> [2,1,4,3] · [1,2,3] -> [2,1,3] · [] -> []
 * Explanation: README.md
 */
public class SwapNodesInPairs {
    public static void main(String[] args) {
        System.out.println(swapPairs(ListNode.of(1, 2, 3, 4)));             // [2, 1, 4, 3]
        System.out.println(swapPairsRecursive(ListNode.of(1, 2, 3)));       // [2, 1, 3]
    }

    /* Approach 1 - prev -> a -> b -> rest becomes prev -> b -> a -> rest; move prev to a. Time O(n), space O(1). */
    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, head), prev = dummy;
        while (prev.next != null && prev.next.next != null) {
            ListNode a = prev.next, b = a.next;
            a.next = b.next;
            b.next = a;
            prev.next = b;
            prev = a;
        }
        return dummy.next;
    }

    /* Approach 2 - swap the first two, attach the swapped rest. Time O(n), space O(n) stack. */
    public static ListNode swapPairsRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode second = head.next;
        head.next = swapPairsRecursive(second.next);
        second.next = head;
        return second;
    }
}
