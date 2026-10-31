package FizzBuzz;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FizzBuzzTest {

    @Test
    void examples() {
        assertEquals(List.of("1", "2", "Fizz", "4", "Buzz"), FizzBuzz.fizzBuzz(5));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15).get(14));
        assertEquals(FizzBuzz.fizzBuzz(1000), FizzBuzz.fizzBuzzConcat(1000));
        assertEquals(FizzBuzz.fizzBuzz(1000), FizzBuzz.fizzBuzzCounters(1000));
    }
}
