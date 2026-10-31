package NumberOfAtoms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfAtomsTest {

    @Test
    void examples() {
        String[][] cases = {
                {"H2O", "H2O"}, {"Mg(OH)2", "H2MgO2"}, {"K4(ON(SO3)2)2", "K4N2O14S4"},
                {"Be32", "Be32"}, {"(NB3)33", "B99N33"}, {"(H)", "H"}, {"((HHe28Be26He)9)34", "Be7956H306He8874"}};
        for (String[] c : cases) {
            assertEquals(c[1], NumberOfAtoms.countOfAtoms(c[0]), c[0]);
            assertEquals(c[1], NumberOfAtoms.countOfAtomsRecursive(c[0]), c[0]);
        }
    }
}
