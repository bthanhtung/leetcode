package RestoreIPAddresses;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestoreIPAddressesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of("255.255.11.135", "255.255.111.35"), RestoreIPAddresses.restoreIpAddresses("25525511135"));
        assertEquals(List.of("0.0.0.0"), RestoreIPAddresses.restoreIpAddresses("0000"));
        assertEquals(List.of("1.0.10.23", "1.0.102.3", "10.1.0.23", "10.10.2.3", "101.0.2.3"), RestoreIPAddresses.restoreIpAddresses("101023"));
        assertEquals(List.of(), RestoreIPAddresses.restoreIpAddresses("1111111111111"));
        Random r = new Random(93);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(13); k > 0; k--) sb.append(r.nextInt(4) == 0 ? '0' : (char) ('0' + r.nextInt(10)));
            assertEquals(RestoreIPAddresses.restoreIpAddressesLoops(sb.toString()), RestoreIPAddresses.restoreIpAddresses(sb.toString()));
        }
    }
}
