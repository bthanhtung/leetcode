package RestoreIPAddresses;

import java.util.ArrayList;
import java.util.List;

/*
 * 93. Restore IP Addresses — https://leetcode.com/problems/restore-ip-addresses/
 * Return all valid IPv4 addresses formed by inserting three dots into a digit string. Each part is 0..255 without
 * leading zeros ("0" is fine, "01" is not).
 * Input: "25525511135" -> ["255.255.11.135","255.255.111.35"] · "0000" -> ["0.0.0.0"] · "101023" -> 5 addresses
 * Explanation: README.md
 */
public class RestoreIPAddresses {
    public static void main(String[] args) {
        System.out.println(restoreIpAddresses("25525511135"));       // [255.255.11.135, 255.255.111.35]
        System.out.println(restoreIpAddressesLoops("101023"));
    }

    private static boolean valid(String s, int from, int to) {
        int len = to - from;
        if (len < 1 || len > 3 || (len > 1 && s.charAt(from) == '0')) return false;
        return Integer.parseInt(s.substring(from, to)) <= 255;
    }

    /* Approach 1 - backtracking: choose the length (1..3) of each of the four parts; prune when the rest is too long. */
    public static List<String> restoreIpAddresses(String s) {
        List<String> out = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(String s, int start, List<String> parts, List<String> out) {
        int remainingParts = 4 - parts.size(), remainingChars = s.length() - start;
        if (remainingParts == 0) {
            if (remainingChars == 0) out.add(String.join(".", parts));
            return;
        }
        if (remainingChars < remainingParts || remainingChars > 3 * remainingParts) return;
        for (int len = 1; len <= 3 && start + len <= s.length(); len++) {
            if (!valid(s, start, start + len)) continue;
            parts.add(s.substring(start, start + len));
            backtrack(s, start + len, parts, out);
            parts.removeLast();
        }
    }

    /* Approach 2 - only three dots: try every (i, j, k) cut position directly. At most 27 combinations. */
    public static List<String> restoreIpAddressesLoops(String s) {
        List<String> out = new ArrayList<>();
        int n = s.length();
        for (int i = 1; i <= 3 && i < n; i++)
            for (int j = i + 1; j <= i + 3 && j < n; j++)
                for (int k = j + 1; k <= j + 3 && k < n; k++)
                    if (valid(s, 0, i) && valid(s, i, j) && valid(s, j, k) && valid(s, k, n))
                        out.add(s.substring(0, i) + "." + s.substring(i, j) + "." + s.substring(j, k) + "." + s.substring(k));
        return out;
    }
}
