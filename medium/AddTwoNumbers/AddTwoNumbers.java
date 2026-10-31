package AddTwoNumbers;

import com.tungdadev.common.ListNode;

/*
 * 2. Add Two Numbers — https://leetcode.com/problems/add-two-numbers/
 * Two non-negative integers are stored as linked lists with digits in reverse order (ones digit first). Return their sum
 * as a linked list in the same format.
 * Input: [2,4,3] + [5,6,4] -> [7,0,8] (342 + 465 = 807) · [9,9,9,9,9,9,9] + [9,9,9,9] -> [8,9,9,9,0,0,0,1]
 * Explanation: README.md
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        System.out.println(addTwoNumbers(ListNode.of(2, 4, 3), ListNode.of(5, 6, 4)));          // [7, 0, 8]
        System.out.println(addTwoNumbersRecursive(ListNode.of(9, 9), ListNode.of(1)));          // [0, 0, 1]
    }

    /* Approach 1 - school addition with a carry, one digit per node. Time O(max(m, n)), space O(1) besides the result. */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0), tail = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            tail.next = new ListNode(sum % 10);
            tail = tail.next;
            carry = sum / 10;
        }
        return dummy.next;
    }

    /* Approach 2 - recursive: this digit, then the sum of the rest with the carry. Time O(max(m, n)), space O(max(m, n)) stack. */
    public static ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2) {
        return add(l1, l2, 0);
    }

    private static ListNode add(ListNode a, ListNode b, int carry) {
        if (a == null && b == null && carry == 0) {
            return null;
        }
        int sum = carry + (a != null ? a.val : 0) + (b != null ? b.val : 0);
        ListNode node = new ListNode(sum % 10);
        node.next = add(a != null ? a.next : null, b != null ? b.next : null, sum / 10);
        return node;
    }
}
