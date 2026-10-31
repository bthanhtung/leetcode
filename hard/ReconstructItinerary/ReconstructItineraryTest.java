package ReconstructItinerary;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReconstructItineraryTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of("JFK", "MUC", "LHR", "SFO", "SJC"),
                ReconstructItinerary.findItinerary(List.of(List.of("MUC", "LHR"), List.of("JFK", "MUC"), List.of("SFO", "SJC"), List.of("LHR", "SFO"))));
        assertEquals(List.of("JFK", "ATL", "JFK", "SFO", "ATL", "SFO"),
                ReconstructItinerary.findItinerary(List.of(List.of("JFK", "SFO"), List.of("JFK", "ATL"), List.of("SFO", "ATL"), List.of("ATL", "JFK"), List.of("ATL", "SFO"))));
        assertEquals(List.of("JFK", "NRT", "JFK", "KUL"),
                ReconstructItinerary.findItinerary(List.of(List.of("JFK", "KUL"), List.of("JFK", "NRT"), List.of("NRT", "JFK"))));
        String[] airports = {"JFK", "AAA", "BBB", "CCC"};
        Random r = new Random(332);
        for (int t = 0; t < 500; t++) {
            List<String> walk = new ArrayList<>(List.of("JFK"));                 // a random walk guarantees a valid itinerary
            for (int k = 1 + r.nextInt(8); k > 0; k--) walk.add(airports[r.nextInt(4)]);
            List<List<String>> tickets = new ArrayList<>();
            for (int i = 0; i + 1 < walk.size(); i++) tickets.add(List.of(walk.get(i), walk.get(i + 1)));
            Collections.shuffle(tickets, r);
            assertEquals(ReconstructItinerary.findItineraryBacktrack(tickets), ReconstructItinerary.findItinerary(tickets));
        }
    }
}
