package ClimbingStairs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClimbingStairsTest {

    @Test
    void examples() {
        assertEquals(1, ClimbingStairs.climbStairs(1));
        assertEquals(2, ClimbingStairs.climbStairs(2));
        assertEquals(3, ClimbingStairs.climbStairs(3));
        assertEquals(1836311903, ClimbingStairs.climbStairs(45));
    }

    @Test
    void allApproachesAgree() {
        for (int n = 1; n <= 45; n++) {
            int expected = ClimbingStairs.climbStairs(n);
            assertEquals(expected, ClimbingStairs.climbStairsMemo(n));
            assertEquals(expected, ClimbingStairs.climbStairsTable(n));
            if (n <= 25) {
                assertEquals(expected, ClimbingStairs.climbStairsRecursive(n));
            }
        }
    }
}
