package NumberOfSegmentsInAString;

/*
 * 434. Number of Segments in a String — https://leetcode.com/problems/number-of-segments-in-a-string/
 * Count the segments (maximal runs of non-space characters) in s.
 * Input: "Hello, my name is John" -> 5 · "Hello" -> 1 · "   " -> 0
 * Explanation: README.md
 */
public class NumberOfSegmentsInAString {
    public static void main(String[] args) {
        System.out.println(countSegments("Hello, my name is John"));      // 5
        System.out.println(countSegmentsSplit("   "));                    // 0
    }

    /* Approach 1 - a segment starts at a non-space character that is first or follows a space. O(n), O(1). */
    public static int countSegments(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ' && (i == 0 || s.charAt(i - 1) == ' ')) count++;
        }
        return count;
    }

    /* Approach 2 - trim and split on runs of spaces. */
    public static int countSegmentsSplit(String s) {
        String t = s.trim();
        return t.isEmpty() ? 0 : t.split(" +").length;
    }
}
