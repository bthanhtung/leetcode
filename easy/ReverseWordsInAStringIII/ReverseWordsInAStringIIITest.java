package ReverseWordsInAStringIII;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseWordsInAStringIIITest {

    @Test
    void examples() {
        String[][] cases = {{"Let's take LeetCode contest", "s'teL ekat edoCteeL tsetnoc"}, {"Mr Ding", "rM gniD"}, {"a", "a"}};
        for (String[] c : cases) {
            assertEquals(c[1], ReverseWordsInAStringIII.reverseWords(c[0]));
            assertEquals(c[1], ReverseWordsInAStringIII.reverseWordsSplit(c[0]));
        }
    }
}
