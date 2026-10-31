package ReconstructItinerary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * 332. Reconstruct Itinerary — https://leetcode.com/problems/reconstruct-itinerary/
 * Use every ticket [from, to] exactly once to build an itinerary starting at "JFK". If several exist, return the
 * lexicographically smallest. A valid itinerary is guaranteed.
 * Input: [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]] -> [JFK, MUC, LHR, SFO, SJC]
 * Explanation: README.md
 */
public class ReconstructItinerary {
    public static void main(String[] args) {
        List<List<String>> t = List.of(List.of("JFK", "SFO"), List.of("JFK", "ATL"), List.of("SFO", "ATL"), List.of("ATL", "JFK"), List.of("ATL", "SFO"));
        System.out.println(findItinerary(t));                 // [JFK, ATL, JFK, SFO, ATL, SFO]
        System.out.println(findItineraryBacktrack(t));
    }

    /*
     * Approach 1 - Hierholzer: from each airport always take the smallest unused ticket; when stuck, the airport goes to
     * the front of the answer (post-order). Dead ends are therefore placed last. Time O(E log E).
     */
    public static List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> t : tickets) graph.computeIfAbsent(t.get(0), k -> new PriorityQueue<>()).add(t.get(1));
        LinkedList<String> route = new LinkedList<>();
        visit("JFK", graph, route);
        return route;
    }

    private static void visit(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> route) {
        PriorityQueue<String> next = graph.get(airport);
        while (next != null && !next.isEmpty()) visit(next.poll(), graph, route);
        route.addFirst(airport);
    }

    /* Approach 2 - backtracking: try destinations in lexical order; the first complete route is the answer. */
    public static List<String> findItineraryBacktrack(List<List<String>> tickets) {
        Map<String, List<String>> graph = new HashMap<>();
        for (List<String> t : tickets) graph.computeIfAbsent(t.get(0), k -> new ArrayList<>()).add(t.get(1));
        for (List<String> dests : graph.values()) Collections.sort(dests);
        List<String> route = new ArrayList<>(List.of("JFK"));
        backtrack(graph, route, tickets.size() + 1);
        return route;
    }

    private static boolean backtrack(Map<String, List<String>> graph, List<String> route, int length) {
        if (route.size() == length) return true;
        List<String> dests = graph.get(route.getLast());
        if (dests == null) return false;
        for (int i = 0; i < dests.size(); i++) {
            if (i > 0 && dests.get(i).equals(dests.get(i - 1))) continue;       // same ticket twice is the same choice
            String d = dests.remove(i);
            route.add(d);
            if (backtrack(graph, route, length)) return true;
            route.removeLast();
            dests.add(i, d);
        }
        return false;
    }
}
