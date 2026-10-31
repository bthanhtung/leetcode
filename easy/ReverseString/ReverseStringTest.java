package ReverseString;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ReverseStringTest {

    @Test
    void examples() {
        for (String word : new String[]{"hello", "Hannah", "a", "", "ab"}) {
            char[] expected = new StringBuilder(word).reverse().toString().toCharArray();
            char[] a = word.toCharArray(), b = word.toCharArray();
            ReverseString.reverseString(a);
            ReverseString.reverseStringRecursive(b);
            assertArrayEquals(expected, a);
            assertArrayEquals(expected, b);
        }
    }
}
