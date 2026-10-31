package MultiplyStrings;

/*
 * 43. Multiply Strings — https://leetcode.com/problems/multiply-strings/
 * Multiply two non-negative integers given as strings, without BigInteger or converting to numbers.
 * Input: ("2","3") -> "6" · ("123","456") -> "56088"
 * Explanation: README.md
 */
public class MultiplyStrings {
    public static void main(String[] args) {
        System.out.println(multiply("123", "456"));     // 56088
    }

    /*
     * digit i of num1 times digit j of num2 lands in positions i + j (carry) and i + j + 1 (units) of a result of
     * length m + n. Accumulate, propagate the carry right to left, strip leading zeros. Time O(m * n).
     */
    public static String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] pos = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int sum = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + pos[i + j + 1];
                pos[i + j + 1] = sum % 10;
                pos[i + j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int d : pos) if (sb.length() > 0 || d != 0) sb.append(d);
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
