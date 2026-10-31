package WordLadder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * 127. Word Ladder — https://leetcode.com/problems/word-ladder/
 * Transform beginWord into endWord changing one letter at a time; every intermediate word must be in wordList.
 * Return the number of words in the shortest sequence (including both ends), or 0.
 * Input: "hit" -> "cog", ["hot","dot","dog","lot","log","cog"] -> 5 (hit hot dot dog cog) · without "cog" -> 0
 * Explanation: README.md
 */
public class WordLadder {
    public static void main(String[] args) {
        List<String> words = List.of("hot", "dot", "dog", "lot", "log", "cog");
        System.out.println(ladderLength("hit", "cog", words));                 // 5
        System.out.println(ladderLengthBidirectional("hit", "cog", words));    // 5
    }

    /*
     * Approach 1 - BFS. Neighbours are found through wildcard patterns: "hot" belongs to "*ot", "h*t", "ho*"; all words
     * sharing a pattern differ in exactly that letter. Time O(N * L^2).
     */
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;
        Map<String, List<String>> buckets = new HashMap<>();
        for (String w : wordList) {
            for (int i = 0; i < w.length(); i++) {
                buckets.computeIfAbsent(w.substring(0, i) + '*' + w.substring(i + 1), k -> new ArrayList<>()).add(w);
            }
        }
        Set<String> seen = new HashSet<>(List.of(beginWord));
        Queue<String> queue = new ArrayDeque<>(List.of(beginWord));
        for (int length = 1; !queue.isEmpty(); length++) {
            for (int size = queue.size(); size > 0; size--) {
                String w = queue.poll();
                if (w.equals(endWord)) return length;
                for (int i = 0; i < w.length(); i++) {
                    String pattern = w.substring(0, i) + '*' + w.substring(i + 1);
                    for (String next : buckets.getOrDefault(pattern, List.of())) {
                        if (seen.add(next)) queue.add(next);
                    }
                    buckets.remove(pattern);                       // every word in it is now queued
                }
            }
        }
        return 0;
    }

    /* Approach 2 - bidirectional BFS: expand the smaller frontier by trying every letter at every position. */
    public static int ladderLengthBidirectional(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;
        Set<String> front = new HashSet<>(List.of(beginWord)), back = new HashSet<>(List.of(endWord));
        dict.remove(beginWord);
        dict.remove(endWord);
        for (int length = 2; !front.isEmpty(); length++) {
            if (front.size() > back.size()) {
                Set<String> t = front;
                front = back;
                back = t;
            }
            Set<String> next = new HashSet<>();
            for (String w : front) {
                char[] c = w.toCharArray();
                for (int i = 0; i < c.length; i++) {
                    char original = c[i];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (ch == original) continue;
                        c[i] = ch;
                        String candidate = new String(c);
                        if (back.contains(candidate)) return length;
                        if (dict.remove(candidate)) next.add(candidate);
                    }
                    c[i] = original;
                }
            }
            front = next;
        }
        return 0;
    }
}
