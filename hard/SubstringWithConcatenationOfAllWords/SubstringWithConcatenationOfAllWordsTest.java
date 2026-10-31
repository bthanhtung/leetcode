package SubstringWithConcatenationOfAllWords;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringWithConcatenationOfAllWordsTest {

    private static List<Integer> brute(String s, String[] words) {
        List<Integer> out = new ArrayList<>();
        int L = words[0].length(), total = L * words.length;
        String[] sortedWords = words.clone();
        Arrays.sort(sortedWords);
        for (int i = 0; i + total <= s.length(); i++) {
            String[] parts = new String[words.length];
            for (int k = 0; k < words.length; k++) parts[k] = s.substring(i + k * L, i + (k + 1) * L);
            Arrays.sort(parts);
            if (Arrays.equals(parts, sortedWords)) out.add(i);
        }
        return out;
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals(List.of(0, 9), SubstringWithConcatenationOfAllWords.findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}));
        assertEquals(List.of(), SubstringWithConcatenationOfAllWords.findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}));
        assertEquals(List.of(6, 9, 12), SubstringWithConcatenationOfAllWords.findSubstring("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}));
        assertEquals(List.of(0, 1, 2), SubstringWithConcatenationOfAllWords.findSubstring("aaaaaa", new String[]{"aa", "aa"}));
        Random r = new Random(30);
        for (int t = 0; t < 3000; t++) {
            int L = 1 + r.nextInt(3);
            String[] words = new String[1 + r.nextInt(3)];
            for (int i = 0; i < words.length; i++) words[i] = rnd(r, L);
            String s = rnd(r, r.nextInt(16));
            assertEquals(brute(s, words), SubstringWithConcatenationOfAllWords.findSubstring(s, words), s + " " + Arrays.toString(words));
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('a' + r.nextInt(2)));
        return sb.toString();
    }
}
