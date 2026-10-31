package SimplifyPath;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 71. Simplify Path — https://leetcode.com/problems/simplify-path/
 * Simplify an absolute Unix path: '.' is the current directory, '..' goes up, repeated slashes count as one,
 * any other name (including '...') is a directory. The result starts with '/' and has no trailing slash.
 * Input: "/home/" -> "/home" · "/home//foo/" -> "/home/foo" · "/home/user/Documents/../Pictures" -> "/home/user/Pictures"
 *        "/../" -> "/" · "/.../a/../b/c/../d/./" -> "/.../b/d"
 * Explanation: README.md
 */
public class SimplifyPath {
    public static void main(String[] args) {
        System.out.println(simplifyPath("/.../a/../b/c/../d/./"));      // /.../b/d
    }

    /* Split by '/', push names, pop on "..", ignore "" and ".". Join from bottom to top. Time O(n). */
    public static String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        for (String part : path.split("/")) {
            if (part.isEmpty() || part.equals(".")) continue;
            if (part.equals("..")) stack.pollLast();
            else stack.addLast(part);
        }
        return "/" + String.join("/", stack);
    }
}
