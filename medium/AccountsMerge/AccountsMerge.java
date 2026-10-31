package AccountsMerge;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*
 * 721. Accounts Merge — https://leetcode.com/problems/accounts-merge/
 * accounts[i] = [name, email1, email2, ...]. Two accounts belong to the same person if they share an email.
 * Merge them and return [name, sorted emails...] per person, in any order.
 * Input: [["John","a@m","b@m"],["John","a@m","c@m"],["Mary","m@m"],["John","j@m"]]
 *     -> [["John","a@m","b@m","c@m"],["Mary","m@m"],["John","j@m"]]
 * Explanation: README.md
 */
public class AccountsMerge {
    public static void main(String[] args) {
        List<List<String>> accounts = List.of(
                List.of("John", "johnsmith@mail.com", "john_newyork@mail.com"),
                List.of("John", "johnsmith@mail.com", "john00@mail.com"),
                List.of("Mary", "mary@mail.com"),
                List.of("John", "johnnybravo@mail.com"));
        System.out.println(accountsMerge(accounts));
    }

    /* Approach 1 - union-find over emails: union every email of an account with its first email. Time ~O(E log E). */
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, String> parent = new HashMap<>(), owner = new HashMap<>();
        for (List<String> acc : accounts) {
            for (int i = 1; i < acc.size(); i++) {
                parent.putIfAbsent(acc.get(i), acc.get(i));
                owner.put(acc.get(i), acc.getFirst());
                union(parent, acc.get(1), acc.get(i));
            }
        }
        Map<String, TreeSet<String>> groups = new HashMap<>();
        for (String email : parent.keySet()) {
            groups.computeIfAbsent(find(parent, email), k -> new TreeSet<>()).add(email);
        }
        List<List<String>> out = new ArrayList<>();
        for (TreeSet<String> emails : groups.values()) {
            List<String> row = new ArrayList<>();
            row.add(owner.get(emails.first()));
            row.addAll(emails);
            out.add(row);
        }
        return out;
    }

    private static String find(Map<String, String> parent, String x) {
        String root = x;
        while (!parent.get(root).equals(root)) root = parent.get(root);
        while (!parent.get(x).equals(root)) {             // path compression
            String next = parent.get(x);
            parent.put(x, root);
            x = next;
        }
        return root;
    }

    private static void union(Map<String, String> parent, String a, String b) {
        String ra = find(parent, a), rb = find(parent, b);
        if (!ra.equals(rb)) parent.put(rb, ra);
    }

    /* Approach 2 - graph: connect each account's first email to its other emails; every connected component is a person. */
    public static List<List<String>> accountsMergeDfs(List<List<String>> accounts) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, String> owner = new HashMap<>();
        for (List<String> acc : accounts) {
            String first = acc.get(1);
            for (int i = 1; i < acc.size(); i++) {
                String e = acc.get(i);
                graph.computeIfAbsent(e, k -> new ArrayList<>());
                graph.get(first).add(e);
                graph.get(e).add(first);
                owner.put(e, acc.getFirst());
            }
        }
        Set<String> seen = new HashSet<>();
        List<List<String>> out = new ArrayList<>();
        for (String start : graph.keySet()) {
            if (!seen.add(start)) continue;
            List<String> emails = new ArrayList<>();
            Deque<String> stack = new ArrayDeque<>();
            stack.push(start);
            while (!stack.isEmpty()) {
                String e = stack.pop();
                emails.add(e);
                for (String next : graph.get(e)) if (seen.add(next)) stack.push(next);
            }
            Collections.sort(emails);
            List<String> row = new ArrayList<>();
            row.add(owner.get(start));
            row.addAll(emails);
            out.add(row);
        }
        return out;
    }
}
