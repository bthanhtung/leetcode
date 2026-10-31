package AddTwoNumbers;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddTwoNumbersTest {

    private static ListNode fromNumber(BigInteger n) {
        String s = new StringBuilder(n.toString()).reverse().toString();
        return ListNode.of(s.chars().map(c -> c - '0').toArray());
    }

    @Test
    void examples() {
        assertEquals(List.of(7, 0, 8), ListNode.toList(AddTwoNumbers.addTwoNumbers(ListNode.of(2, 4, 3), ListNode.of(5, 6, 4))));
        assertEquals(List.of(0), ListNode.toList(AddTwoNumbers.addTwoNumbers(ListNode.of(0), ListNode.of(0))));
        assertEquals(List.of(8, 9, 9, 9, 0, 0, 0, 1),
                ListNode.toList(AddTwoNumbers.addTwoNumbersRecursive(ListNode.of(9, 9, 9, 9, 9, 9, 9), ListNode.of(9, 9, 9, 9))));
    }

    @Test
    void randomBigNumbers() {
        Random r = new Random(2);
        for (int t = 0; t < 500; t++) {
            BigInteger a = new BigInteger(1 + r.nextInt(200), r), b = new BigInteger(1 + r.nextInt(200), r);
            List<Integer> expected = ListNode.toList(fromNumber(a.add(b)));
            assertEquals(expected, ListNode.toList(AddTwoNumbers.addTwoNumbers(fromNumber(a), fromNumber(b))));
            assertEquals(expected, ListNode.toList(AddTwoNumbers.addTwoNumbersRecursive(fromNumber(a), fromNumber(b))));
        }
    }
}
