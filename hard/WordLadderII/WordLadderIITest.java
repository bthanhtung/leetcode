package WordLadderII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordLadderIITest {

    private static Set<List<String>> brute(String begin, String end, List<String> words) {
        Set<List<String>> all = new HashSet<>();
        int[] best = {Integer.MAX_VALUE};
        List<String> path = new ArrayList<>(List.of(begin));
        dfs(begin, end, words, path, all, best);
        Set<List<String>> shortest = new HashSet<>();
        for (List<String> p : all) if (p.size() == best[0]) shortest.add(p);
        return shortest;
    }

    private static void dfs(String cur, String end, List<String> words, List<String> path, Set<List<String>> all, int[] best) {
        if (path.size() > best[0]) return;
        if (cur.equals(end)) { all.add(new ArrayList<>(path)); best[0] = Math.min(best[0], path.size()); return; }
        for (String w : words) {
            if (path.contains(w) || diff(cur, w) != 1) continue;
            path.add(w);
            dfs(w, end, words, path, all, best);
            path.remove(path.size() - 1);
        }
    }

    private static int diff(String a, String b) {
        int d = 0;
        for (int i = 0; i < a.length(); i++) if (a.charAt(i) != b.charAt(i)) d++;
        return d;
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals(Set.of(List.of("hit", "hot", "dot", "dog", "cog"), List.of("hit", "hot", "lot", "log", "cog")),
                new HashSet<>(WordLadderII.findLadders("hit", "cog", List.of("hot", "dot", "dog", "lot", "log", "cog"))));
        assertEquals(List.of(), WordLadderII.findLadders("hit", "cog", List.of("hot", "dot", "dog", "lot", "log")));
        Random r = new Random(126);
        for (int t = 0; t < 600; t++) {
            Set<String> set = new HashSet<>();
            for (int k = 2 + r.nextInt(8); k > 0; k--) set.add(rnd(r));
            List<String> words = new ArrayList<>(set);
            String begin = rnd(r), end = words.get(r.nextInt(words.size()));
            if (begin.equals(end)) continue;
            List<List<String>> got = WordLadderII.findLadders(begin, end, words);
            assertEquals(got.size(), new HashSet<>(got).size());
            assertEquals(brute(begin, end, words), new HashSet<>(got), begin + " -> " + end + " " + words);
        }
    }

    private static String rnd(Random r) {
        return "" + (char) ('a' + r.nextInt(3)) + (char) ('a' + r.nextInt(3)) + (char) ('a' + r.nextInt(2));
    }
}
