package AccountsMerge;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountsMergeTest {

    @Test
    void exampleAndRandom() {
        List<List<String>> accounts = List.of(
                List.of("John", "johnsmith@mail.com", "john_newyork@mail.com"),
                List.of("John", "johnsmith@mail.com", "john00@mail.com"),
                List.of("Mary", "mary@mail.com"),
                List.of("John", "johnnybravo@mail.com"));
        Set<List<String>> expected = Set.of(
                List.of("John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"),
                List.of("Mary", "mary@mail.com"),
                List.of("John", "johnnybravo@mail.com"));
        assertEquals(expected, new HashSet<>(AccountsMerge.accountsMerge(accounts)));
        assertEquals(expected, new HashSet<>(AccountsMerge.accountsMergeDfs(accounts)));
        Random r = new Random(721);
        for (int t = 0; t < 300; t++) {
            List<List<String>> input = new ArrayList<>();
            int people = 1 + r.nextInt(4);
            for (int a = 0; a < 1 + r.nextInt(8); a++) {
                int person = r.nextInt(people);
                List<String> acc = new ArrayList<>();
                acc.add("P" + person);
                for (int e = 0; e < 1 + r.nextInt(3); e++) acc.add(person + "_" + r.nextInt(5) + "@m");
                input.add(acc);
            }
            assertEquals(new HashSet<>(AccountsMerge.accountsMergeDfs(input)), new HashSet<>(AccountsMerge.accountsMerge(input)));
        }
    }
}
