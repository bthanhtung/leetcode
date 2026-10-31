package WordLadderII;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 126. Word Ladder II — https://leetcode.com/problems/word-ladder-ii/
 * Return all shortest transformation sequences from beginWord to endWord: change one letter at a time, each
 * intermediate word must be in wordList.
 * Input: begin "hit", end "cog", list [hot,dot,dog,lot,log,cog] -> [[hit,hot,dot,dog,cog],[hit,hot,lot,log,cog]]
 *        begin "hit", end "cog", list [hot,dot,dog,lot,log] -> []
 * Explanation: README.md
 */
public class WordLadderII {
    public static void main(String[] args) {
        System.out.println(findLadders("hit", "cog", List.of("hot", "dot", "dog", "lot", "log", "cog")));
    }

    /*
     * BFS layer by layer. For each word reached in the next layer remember every parent in the current layer (a word
     * is removed from the dictionary only after its whole layer is processed, so several parents are kept). Stop
     * after the layer that reaches endWord, then build paths backwards from endWord with DFS over parents.
     */
    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        List<List<String>> result = new ArrayList<>();
        if (!dict.contains(endWord)) return result;
        Map<String, List<String>> parents = new HashMap<>();
        Set<String> layer = new HashSet<>(List.of(beginWord));
        dict.remove(beginWord);
        boolean found = false;
        while (!layer.isEmpty() && !found) {
            Set<String> next = new HashSet<>();
            for (String word : layer) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[i] = c;
                        String candidate = new String(chars);
                        if (dict.contains(candidate)) {
                            next.add(candidate);
                            parents.computeIfAbsent(candidate, k -> new ArrayList<>()).add(word);
                            if (candidate.equals(endWord)) found = true;
                        }
                    }
                    chars[i] = original;
                }
            }
            dict.removeAll(next);
            layer = next;
        }
        if (found) backtrack(endWord, beginWord, parents, new ArrayDeque<>(List.of(endWord)), result);
        return result;
    }

    private static void backtrack(String word, String begin, Map<String, List<String>> parents,
                                  ArrayDeque<String> path, List<List<String>> result) {
        if (word.equals(begin)) {
            List<String> p = new ArrayList<>(path);
            Collections.reverse(p);
            result.add(p);
            return;
        }
        for (String parent : parents.getOrDefault(word, List.of())) {
            path.addLast(parent);
            backtrack(parent, begin, parents, path, result);
            path.removeLast();
        }
    }
}
