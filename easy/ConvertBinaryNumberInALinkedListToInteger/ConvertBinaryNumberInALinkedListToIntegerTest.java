package ConvertBinaryNumberInALinkedListToInteger;

import com.tungdadev.common.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertBinaryNumberInALinkedListToIntegerTest {

    @Test
    void everyValueUpTo4096() {
        for (int v = 0; v < 4096; v++) {
            int[] bits = Integer.toBinaryString(v).chars().map(c -> c - '0').toArray();
            assertEquals(v, ConvertBinaryNumberInALinkedListToInteger.getDecimalValue(ListNode.of(bits)));
            assertEquals(v, ConvertBinaryNumberInALinkedListToInteger.getDecimalValueString(ListNode.of(bits)));
        }
    }
}
