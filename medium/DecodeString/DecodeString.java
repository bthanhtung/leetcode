package DecodeString;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 394. Decode String — https://leetcode.com/problems/decode-string/
 * Decode k[encoded] meaning encoded repeated k times; encodings can nest.
 * Input: "3[a]2[bc]" -> "aaabcbc" · "3[a2[c]]" -> "accaccacc" · "2[abc]3[cd]ef" -> "abcabccdcdcdef"
 * Explanation: README.md
 */
public class DecodeString {
    public static void main(String[] args) {
        System.out.println(decodeString("3[a2[c]]"));               // accaccacc
        System.out.println(decodeStringRecursive("2[abc]3[cd]ef")); // abcabccdcdcdef
    }

    /* Approach 1 - on '[' push (count, text so far) and start fresh; on ']' pop and append the repeated part. O(output). */
    public static String decodeString(String s) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> outer = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        int k = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0');                    // numbers can have several digits
            } else if (c == '[') {
                counts.push(k);
                outer.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                String inner = current.toString();
                current = outer.pop();
                current.append(inner.repeat(counts.pop()));
            } else {
                current.append(c);
            }
        }
        return current.toString();
    }

    /* Approach 2 - recursive descent: parse letters and k[...] groups until ']' or the end. */
    public static String decodeStringRecursive(String s) {
        return parse(s, new int[]{0});
    }

    private static String parse(String s, int[] pos) {
        StringBuilder sb = new StringBuilder();
        while (pos[0] < s.length() && s.charAt(pos[0]) != ']') {
            char c = s.charAt(pos[0]);
            if (Character.isDigit(c)) {
                int k = 0;
                while (Character.isDigit(s.charAt(pos[0]))) k = k * 10 + (s.charAt(pos[0]++) - '0');
                pos[0]++;                                   // skip '['
                String inner = parse(s, pos);
                pos[0]++;                                   // skip ']'
                sb.append(inner.repeat(k));
            } else {
                sb.append(c);
                pos[0]++;
            }
        }
        return sb.toString();
    }
}
