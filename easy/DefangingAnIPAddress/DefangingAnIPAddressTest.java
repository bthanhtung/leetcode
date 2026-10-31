package DefangingAnIPAddress;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefangingAnIPAddressTest {

    @Test
    void examples() {
        for (String[] c : new String[][]{{"1.1.1.1", "1[.]1[.]1[.]1"}, {"255.100.50.0", "255[.]100[.]50[.]0"}}) {
            assertEquals(c[1], DefangingAnIPAddress.defangIPaddr(c[0]));
            assertEquals(c[1], DefangingAnIPAddress.defangIPaddrBuilder(c[0]));
        }
    }
}
