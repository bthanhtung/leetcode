package LongestCommonPrefix;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestCommonPrefixTest {

    private static void all(String expected, String... strs) {
        assertEquals(expected, LongestCommonPrefix.lcpVertical(strs));
        assertEquals(expected, LongestCommonPrefix.lcpHorizontal(strs));
        assertEquals(expected, LongestCommonPrefix.lcpSorted(strs));
    }

    @Test
    void examples() {
        all("fl", "flower", "flow", "flight");
        all("", "dog", "racecar", "car");
        all("a", "a");
        all("", "", "abc");
        all("ab", "ab", "abc", "abcd");      // a whole word can be the prefix
    }

    @Test
    void randomAgree() {
        Random r = new Random(5);
        for (int t = 0; t < 500; t++) {
            String[] strs = new String[1 + r.nextInt(5)];
            for (int i = 0; i < strs.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int k = r.nextInt(6); k > 0; k--) {
                    sb.append((char) ('a' + r.nextInt(2)));
                }
                strs[i] = sb.toString();
            }
            String v = LongestCommonPrefix.lcpVertical(strs);
            assertEquals(v, LongestCommonPrefix.lcpHorizontal(strs));
            assertEquals(v, LongestCommonPrefix.lcpSorted(strs));
        }
    }
}
