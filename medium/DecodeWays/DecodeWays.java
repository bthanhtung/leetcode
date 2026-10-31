package DecodeWays;

/*
 * 91. Decode Ways — https://leetcode.com/problems/decode-ways/
 * Letters map to "1".."26". Count the ways to decode a digit string (a part like "06" is invalid).
 * Input: "12" -> 2 ("AB", "L") · "226" -> 3 · "06" -> 0
 * Explanation: README.md
 */
public class DecodeWays {
    public static void main(String[] args) {
        System.out.println(numDecodings("226"));     // 3
    }

    /*
     * ways(i) = ways of decoding s[0..i). The last piece is one digit (valid if not '0') or two digits (10..26):
     * ways(i) = [s[i-1] != '0'] * ways(i-1) + [10 <= s[i-2..i) <= 26] * ways(i-2). Two variables. O(n), O(1).
     */
    public static int numDecodings(String s) {
        int prev2 = 1, prev1 = s.charAt(0) == '0' ? 0 : 1;      // ways("") and ways(s[0..1))
        for (int i = 2; i <= s.length(); i++) {
            int cur = 0;
            if (s.charAt(i - 1) != '0') cur += prev1;
            int two = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            if (two >= 10 && two <= 26) cur += prev2;
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }
}
