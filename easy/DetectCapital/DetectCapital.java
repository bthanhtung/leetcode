package DetectCapital;

/*
 * 520. Detect Capital — https://leetcode.com/problems/detect-capital/
 * Capital usage is right if all letters are capitals ("USA"), none are ("leetcode"), or only the first is ("Google").
 * Input: "USA" -> true · "FlaG" -> false
 * Explanation: README.md
 */
public class DetectCapital {
    public static void main(String[] args) {
        System.out.println(detectCapitalUse("USA"));         // true
        System.out.println(detectCapitalUseRegex("FlaG"));   // false
    }

    /* Approach 1 - count capitals: 0, all, or exactly 1 at position 0. Time O(n). */
    public static boolean detectCapitalUse(String word) {
        int capitals = 0;
        for (char c : word.toCharArray()) {
            if (Character.isUpperCase(c)) capitals++;
        }
        return capitals == 0 || capitals == word.length() || (capitals == 1 && Character.isUpperCase(word.charAt(0)));
    }

    /* Approach 2 - one regular expression: all caps, or an optional capital followed by lowercase. */
    public static boolean detectCapitalUseRegex(String word) {
        return word.matches("[A-Z]*|[A-Z]?[a-z]*");
    }
}
