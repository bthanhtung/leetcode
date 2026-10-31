package FindTheDifference;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindTheDifferenceTest {

    @Test
    void random() {
        assertEquals('e', FindTheDifference.findTheDifference("abcd", "abcde"));
        Random r = new Random(389);
        for (int t = 0; t < 1000; t++) {
            StringBuilder s = new StringBuilder();
            for (int k = r.nextInt(10); k > 0; k--) s.append((char) ('a' + r.nextInt(26)));
            char extra = (char) ('a' + r.nextInt(26));
            List<Character> chars = new ArrayList<>();
            for (char c : s.toString().toCharArray()) chars.add(c);
            chars.add(extra);
            Collections.shuffle(chars, r);
            StringBuilder tt = new StringBuilder();
            chars.forEach(tt::append);
            assertEquals(extra, FindTheDifference.findTheDifference(s.toString(), tt.toString()));
            assertEquals(extra, FindTheDifference.findTheDifferenceXor(s.toString(), tt.toString()));
            assertEquals(extra, FindTheDifference.findTheDifferenceCount(s.toString(), tt.toString()));
        }
    }
}
