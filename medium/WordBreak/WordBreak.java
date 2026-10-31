package WordBreak;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/*
 * 139. Word Break — https://leetcode.com/problems/word-break/
 * Return true if s can be segmented into a sequence of one or more dictionary words (words may be reused).
 * Input: ("leetcode", ["leet","code"]) -> true · ("applepenapple", ["apple","pen"]) -> true
 *        ("catsandog", ["cats","dog","sand","and","cat"]) -> false
 * Explanation: README.md
 */
public class WordBreak {
    public static void main(String[] args) {
        System.out.println(wordBreak("leetcode", List.of("leet", "code")));                           // true
        System.out.println(wordBreakBfs("catsandog", List.of("cats", "dog", "sand", "and", "cat")));  // false
    }

    /*
     * Approach 1 - ok[i] = s[0..i) can be segmented. ok[i] is true if some ok[j] is true and s[j..i) is a word.
     * Only try j within the longest word length. Time O(n * L).
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int maxLen = 0;
        for (String w : wordDict) maxLen = Math.max(maxLen, w.length());
        boolean[] ok = new boolean[s.length() + 1];
        ok[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = Math.max(0, i - maxLen); j < i && !ok[i]; j++) {
                ok[i] = ok[j] && dict.contains(s.substring(j, i));
            }
        }
        return ok[s.length()];
    }

    /* Approach 2 - BFS: nodes are start positions; an edge j -> i when s[j..i) is a word. Reach s.length()? */
    public static boolean wordBreakBfs(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] seen = new boolean[s.length() + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        seen[0] = true;
        while (!queue.isEmpty()) {
            int start = queue.poll();
            if (start == s.length()) return true;
            for (int end = start + 1; end <= s.length(); end++) {
                if (!seen[end] && dict.contains(s.substring(start, end))) {
                    seen[end] = true;
                    queue.add(end);
                }
            }
        }
        return false;
    }
}
