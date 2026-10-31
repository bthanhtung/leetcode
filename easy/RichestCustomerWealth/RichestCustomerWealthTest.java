package RichestCustomerWealth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RichestCustomerWealthTest {

    @Test
    void examples() {
        int[][][] inputs = {{{1, 2, 3}, {3, 2, 1}}, {{1, 5}, {7, 3}, {3, 5}}, {{2, 8, 7}, {7, 1, 3}, {1, 9, 5}}};
        int[] expected = {6, 10, 17};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expected[i], RichestCustomerWealth.maximumWealth(inputs[i]));
            assertEquals(expected[i], RichestCustomerWealth.maximumWealthStream(inputs[i]));
        }
    }
}
