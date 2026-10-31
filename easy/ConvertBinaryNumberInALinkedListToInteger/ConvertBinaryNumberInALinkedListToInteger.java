package ConvertBinaryNumberInALinkedListToInteger;

/*
 * 1290. Convert Binary Number in a Linked List to Integer — https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/
 * A linked list holds the bits of a binary number, most significant bit first. Return its decimal value.
 * Input: [1,0,1] -> 5 · [0] -> 0
 * Explanation: README.md
 */
public class ConvertBinaryNumberInALinkedListToInteger {
    public static void main(String[] args) {
        System.out.println(getDecimalValue(ListNode.of(1, 0, 1)));              // 5
        System.out.println(getDecimalValueString(ListNode.of(1, 1, 1, 1)));     // 15
    }

    /* Approach 1 - Horner's rule: value = value * 2 + bit, written with a shift. Time O(n), space O(1). */
    public static int getDecimalValue(ListNode head) {
        int value = 0;
        for (ListNode p = head; p != null; p = p.next) {
            value = (value << 1) | p.val;
        }
        return value;
    }

    /* Approach 2 - build the bit string and parse it in base 2. Time O(n), space O(n). */
    public static int getDecimalValueString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        for (ListNode p = head; p != null; p = p.next) sb.append(p.val);
        return Integer.parseInt(sb.toString(), 2);
    }
}
