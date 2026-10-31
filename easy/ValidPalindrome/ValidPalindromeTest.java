package ValidPalindrome;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidPalindromeTest {

    private static void all(boolean expected, String s) {
        assertEquals(expected, ValidPalindrome.isPalindrome(s), s);
        assertEquals(expected, ValidPalindrome.isPalindromeClean(s), s);
    }

    @Test
    void examples() {
        all(true, "A man, a plan, a canal: Panama");
        all(false, "race a car");
        all(true, " ");
        all(false, "0P");          // digits count, and '0' != 'p'
        all(true, ".,");
    }

    @Test
    void randomAgree() {
        Random r = new Random(125);
        String alphabet = "aAbB1 ,.";
        for (int t = 0; t < 3000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(8); k > 0; k--) sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
            all(ValidPalindrome.isPalindromeClean(sb.toString()), sb.toString());
        }
    }
}
