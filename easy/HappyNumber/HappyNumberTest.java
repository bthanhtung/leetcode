package HappyNumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HappyNumberTest {

    @Test
    void examples() {
        assertEquals(true, HappyNumber.isHappy(19));
        assertEquals(false, HappyNumber.isHappy(2));
        assertEquals(true, HappyNumber.isHappy(1));
        assertEquals(true, HappyNumber.isHappy(7));
    }

    @Test
    void allApproachesAgreeUpTo100000() {
        for (int n = 1; n <= 100_000; n++) {
            boolean expected = HappyNumber.isHappySet(n);
            assertEquals(expected, HappyNumber.isHappy(n), "floyd " + n);
            assertEquals(expected, HappyNumber.isHappyKnownCycle(n), "cycle " + n);
        }
        assertEquals(HappyNumber.isHappySet(Integer.MAX_VALUE), HappyNumber.isHappy(Integer.MAX_VALUE));
    }
}
