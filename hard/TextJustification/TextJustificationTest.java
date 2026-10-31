package TextJustification;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextJustificationTest {

    @Test
    void examples() {
        assertEquals(List.of("This    is    an", "example  of text", "justification.  "),
                TextJustification.fullJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16));
        assertEquals(List.of("What   must   be", "acknowledgment  ", "shall be        "),
                TextJustification.fullJustify(new String[]{"What", "must", "be", "acknowledgment", "shall", "be"}, 16));
        assertEquals(List.of("Science  is  what we", "understand      well", "enough to explain to", "a  computer.  Art is",
                        "everything  else  we", "do                  "),
                TextJustification.fullJustify(new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain",
                        "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"}, 20));
    }

    @Test
    void randomInvariants() {
        Random r = new Random(68);
        for (int t = 0; t < 500; t++) {
            int width = 5 + r.nextInt(15);
            String[] words = new String[1 + r.nextInt(12)];
            for (int i = 0; i < words.length; i++) words[i] = "w".repeat(1 + r.nextInt(width)).replace('w', (char) ('a' + i % 26));
            List<String> lines = TextJustification.fullJustify(words, width);
            StringBuilder joined = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                assertEquals(width, line.length());
                assertTrue(!line.startsWith(" "));
                joined.append(line.trim().replaceAll(" +", " ")).append(' ');
            }
            assertEquals(String.join(" ", words), joined.toString().trim(), "words in order, none lost");
        }
    }
}
