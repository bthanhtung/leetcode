package DefangingAnIPAddress;

/*
 * 1108. Defanging an IP Address — https://leetcode.com/problems/defanging-an-ip-address/
 * Given a valid IPv4 address, return its defanged version: every "." becomes "[.]".
 * Input: "1.1.1.1" -> "1[.]1[.]1[.]1" · "255.100.50.0" -> "255[.]100[.]50[.]0"
 * Explanation: README.md
 */
public class DefangingAnIPAddress {
    public static void main(String[] args) {
        System.out.println(defangIPaddr("1.1.1.1"));           // 1[.]1[.]1[.]1
        System.out.println(defangIPaddrBuilder("255.100.50.0")); // 255[.]100[.]50[.]0
    }

    /* Approach 1 - library replace. Time O(n), space O(n). */
    public static String defangIPaddr(String address) {
        return address.replace(".", "[.]");
    }

    /* Approach 2 - build the result character by character (what replace does inside). Time O(n), space O(n). */
    public static String defangIPaddrBuilder(String address) {
        StringBuilder sb = new StringBuilder(address.length() + 6);   // exactly 3 dots, each adds 2 characters
        for (char c : address.toCharArray()) {
            if (c == '.') {
                sb.append("[.]");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
