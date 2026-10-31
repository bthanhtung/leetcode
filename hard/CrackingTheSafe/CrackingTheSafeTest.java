package CrackingTheSafe;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrackingTheSafeTest {

    private static void check(String s, int n, int k) {
        int total = (int) Math.pow(k, n);
        assertEquals(total + n - 1, s.length(), "length");
        Set<String> windows = new HashSet<>();
        for (int i = 0; i + n <= s.length(); i++) {
            String w = s.substring(i, i + n);
            for (char c : w.toCharArray()) assertEquals(true, c - '0' < k);
            windows.add(w);
        }
        assertEquals(total, windows.size(), "every password appears");
    }

    @Test
    void allSmallCases() {
        for (int n = 1; n <= 4; n++) {
            for (int k = 1; k <= 10; k++) {
                if (Math.pow(k, n) > 4096) continue;
                check(CrackingTheSafe.crackSafe(n, k), n, k);
                check(CrackingTheSafe.crackSafeGreedy(n, k), n, k);
            }
        }
    }
}
