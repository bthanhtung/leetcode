package AddStrings;

/*
 * 415. Add Strings — https://leetcode.com/problems/add-strings/
 * Add two non-negative integers given as strings without converting them to numbers. Return the sum as a string.
 * Input: ("11","123") -> "134" · ("456","77") -> "533" · ("0","0") -> "0"
 * Explanation: README.md
 */
public class AddStrings {
    public static void main(String[] args) {
        System.out.println(addStrings("456", "77"));     // 533
    }

    /* Walk both strings from the end with a carry, like school addition. Time O(max(m, n)). */
    public static String addStrings(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            sb.append(sum % 10);
            carry = sum / 10;
        }
        return sb.reverse().toString();
    }
}
