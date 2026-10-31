package CountAndSay;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountAndSayTest {

    @Test
    void firstTerms() {
        String[] terms = {"1", "11", "21", "1211", "111221", "312211", "13112221", "1113213211"};
        for (int n = 1; n <= terms.length; n++) {
            assertEquals(terms[n - 1], CountAndSay.countAndSay(n));
            assertEquals(terms[n - 1], CountAndSay.countAndSayRecursive(n));
        }
        for (int n = 1; n <= 30; n++) assertEquals(CountAndSay.countAndSay(n), CountAndSay.countAndSayRecursive(n));
    }
}
