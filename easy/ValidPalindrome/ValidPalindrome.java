package ValidPalindrome;

/*
 * 125. Valid Palindrome — https://leetcode.com/problems/valid-palindrome/
 * After lower-casing and removing all non-alphanumeric characters, does the string read the same forward and backward?
 * Input: "A man, a plan, a canal: Panama" -> true · "race a car" -> false · " " -> true
 * Explanation: README.md
 */
public class ValidPalindrome {
    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));     // true
        System.out.println(isPalindromeClean("race a car"));                    // false
        System.out.println(isPalindrome(" "));                                  // true
    }

    /* Approach 1 - build the cleaned string, compare with its reverse. Time O(n), space O(n). */
    public static boolean isPalindromeClean(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        String cleaned = sb.toString();
        return cleaned.contentEquals(sb.reverse());
    }

    /* Approach 2 - two pointers from both ends, skipping non-alphanumerics on the fly. Time O(n), space O(1). */
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (!Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            } else if (!Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            } else {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }
}
