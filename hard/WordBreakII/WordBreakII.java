package WordBreakII;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 140. Word Break II — https://leetcode.com/problems/word-break-ii/
 * Insert spaces into s to make sentences where every word is in the dictionary. Return all such sentences, any order.
 * Input: "catsanddog", ["cat","cats","and","sand","dog"] -> ["cats and dog","cat sand dog"]
 *        "catsandog", ["cats","dog","sand","and","cat"] -> []
 * Explanation: README.md
 */
public class WordBreakII {
    public static void main(String[] args) {
        System.out.println(wordBreak("catsanddog", List.of("cat", "cats", "and", "sand", "dog")));
        System.out.println(wordBreakDp("pineapplepenapple", List.of("apple", "pen", "applepen", "pine", "pineapple")));
    }

    /* Approach 1 - sentences(start) = for each dictionary word at start, word + each sentence of the rest. Memoized. */
    public static List<String> wordBreak(String s, List<String> wordDict) {
        return sentences(s, 0, new HashSet<>(wordDict), new HashMap<>());
    }

    private static List<String> sentences(String s, int start, Set<String> dict, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) return memo.get(start);
        List<String> out = new ArrayList<>();
        if (start == s.length()) {
            out.add("");
        } else {
            for (int end = start + 1; end <= s.length(); end++) {
                String word = s.substring(start, end);
                if (!dict.contains(word)) continue;
                for (String rest : sentences(s, end, dict, memo)) out.add(rest.isEmpty() ? word : word + " " + rest);
            }
        }
        memo.put(start, out);
        return out;
    }

    /*
     * Approach 2 - first a cheap boolean DP (problem 139): can the suffix from i be broken at all? Then backtrack only
     * into breakable suffixes, so no work is wasted on dead ends.
     */
    public static List<String> wordBreakDp(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] ok = new boolean[n + 1];
        ok[n] = true;
        for (int i = n - 1; i >= 0; i--)
            for (int j = i + 1; j <= n && !ok[i]; j++) ok[i] = ok[j] && dict.contains(s.substring(i, j));
        List<String> out = new ArrayList<>();
        if (ok[0]) build(s, 0, dict, ok, new ArrayList<>(), out);
        return out;
    }

    private static void build(String s, int i, Set<String> dict, boolean[] ok, List<String> path, List<String> out) {
        if (i == s.length()) {
            out.add(String.join(" ", path));
            return;
        }
        for (int j = i + 1; j <= s.length(); j++) {
            String word = s.substring(i, j);
            if (!ok[j] || !dict.contains(word)) continue;
            path.add(word);
            build(s, j, dict, ok, path, out);
            path.removeLast();
        }
    }
}
