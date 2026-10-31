package PartitionLabels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 763. Partition Labels — https://leetcode.com/problems/partition-labels/
 * Partition s into as many parts as possible so that each letter appears in at most one part. Return the part sizes.
 * Input: "ababcbacadefegdehijhklij" -> [9,7,8] ("ababcbaca","defegde","hijhklij") · "eccbbbbdec" -> [10]
 * Explanation: README.md
 */
public class PartitionLabels {
    public static void main(String[] args) {
        System.out.println(partitionLabels("ababcbacadefegdehijhklij"));          // [9, 7, 8]
        System.out.println(partitionLabelsIntervals("eccbbbbdec"));               // [10]
    }

    /*
     * Approach 1 - remember each letter's last index. Walk the string extending the current part's end to the last
     * index of every letter seen; when i reaches that end, cut. Time O(n), space O(1).
     */
    public static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
        List<Integer> sizes = new ArrayList<>();
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                sizes.add(end - start + 1);
                start = i + 1;
            }
        }
        return sizes;
    }

    /* Approach 2 - each letter spans [first, last]; merge overlapping spans (problem 56) and report their lengths. */
    public static List<Integer> partitionLabelsIntervals(String s) {
        int[] first = new int[26], last = new int[26];
        Arrays.fill(first, -1);
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == -1) first[c] = i;
            last[c] = i;
        }
        List<int[]> spans = new ArrayList<>();
        for (int c = 0; c < 26; c++) if (first[c] != -1) spans.add(new int[]{first[c], last[c]});
        spans.sort((a, b) -> a[0] - b[0]);
        List<Integer> sizes = new ArrayList<>();
        int[] cur = spans.getFirst();
        for (int[] span : spans) {
            if (span[0] > cur[1]) {
                sizes.add(cur[1] - cur[0] + 1);
                cur = span.clone();
            } else {
                cur[1] = Math.max(cur[1], span[1]);
            }
        }
        sizes.add(cur[1] - cur[0] + 1);
        return sizes;
    }
}
