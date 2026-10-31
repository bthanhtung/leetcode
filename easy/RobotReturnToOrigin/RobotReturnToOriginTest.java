package RobotReturnToOrigin;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotReturnToOriginTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, RobotReturnToOrigin.judgeCircle("UD"));
        assertEquals(false, RobotReturnToOrigin.judgeCircle("LL"));
        assertEquals(true, RobotReturnToOrigin.judgeCircle(""));
        Random r = new Random(657);
        for (int t = 0; t < 1000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(8); k > 0; k--) sb.append("UDLR".charAt(r.nextInt(4)));
            assertEquals(RobotReturnToOrigin.judgeCircle(sb.toString()), RobotReturnToOrigin.judgeCircleCount(sb.toString()));
        }
    }
}
