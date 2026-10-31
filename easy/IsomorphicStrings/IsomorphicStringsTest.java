package IsomorphicStrings;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsomorphicStringsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, IsomorphicStrings.isIsomorphic("egg", "add"));
        assertEquals(false, IsomorphicStrings.isIsomorphic("foo", "bar"));
        assertEquals(true, IsomorphicStrings.isIsomorphic("paper", "title"));
        assertEquals(false, IsomorphicStrings.isIsomorphic("badc", "baba"));
        Random r = new Random(205);
        for (int t = 0; t < 5000; t++) {
            int n = r.nextInt(7);
            StringBuilder a = new StringBuilder(), b = new StringBuilder();
            for (int i = 0; i < n; i++) {
                a.append((char) ('a' + r.nextInt(3)));
                b.append((char) ('x' + r.nextInt(3)));
            }
            assertEquals(IsomorphicStrings.isIsomorphicMaps(a.toString(), b.toString()), IsomorphicStrings.isIsomorphic(a.toString(), b.toString()));
        }
    }
}
