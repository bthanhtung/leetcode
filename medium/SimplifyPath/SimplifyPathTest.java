package SimplifyPath;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimplifyPathTest {

    @Test
    void examples() {
        assertEquals("/home", SimplifyPath.simplifyPath("/home/"));
        assertEquals("/home/foo", SimplifyPath.simplifyPath("/home//foo/"));
        assertEquals("/home/user/Pictures", SimplifyPath.simplifyPath("/home/user/Documents/../Pictures"));
        assertEquals("/", SimplifyPath.simplifyPath("/../"));
        assertEquals("/.../b/d", SimplifyPath.simplifyPath("/.../a/../b/c/../d/./"));
        assertEquals("/c", SimplifyPath.simplifyPath("/a/./b/../../c/"));
        assertEquals("/..a/b", SimplifyPath.simplifyPath("/..a/b/."));
    }
}
