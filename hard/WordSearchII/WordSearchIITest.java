package WordSearchII;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordSearchIITest {

    @Test
    void examplesAndRandom() {
        char[][] board = {"oaan".toCharArray(), "etae".toCharArray(), "ihkr".toCharArray(), "iflv".toCharArray()};
        assertEquals(Set.of("oath", "eat"), new HashSet<>(WordSearchII.findWords(board, new String[]{"oath", "pea", "eat", "rain"})));
        assertEquals(List.of(), WordSearchII.findWords(new char[][]{"ab".toCharArray(), "cd".toCharArray()}, new String[]{"abcb"}));
        Random r = new Random(212);
        for (int t = 0; t < 300; t++) {
            int m = 1 + r.nextInt(4), n = 1 + r.nextInt(4);
            char[][] b = new char[m][n];
            for (char[] row : b) for (int j = 0; j < n; j++) row[j] = (char) ('a' + r.nextInt(3));
            String[] words = new String[1 + r.nextInt(8)];
            for (int i = 0; i < words.length; i++) {
                StringBuilder w = new StringBuilder();
                for (int k = 1 + r.nextInt(5); k > 0; k--) w.append((char) ('a' + r.nextInt(3)));
                words[i] = w.toString();
            }
            List<String> a = WordSearchII.findWords(b, words), e = WordSearchII.findWordsEach(b, words);
            assertEquals(new HashSet<>(e), new HashSet<>(a));
            assertEquals(a.size(), new HashSet<>(a).size(), "no duplicates");
        }
    }
}
