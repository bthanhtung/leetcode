package NAryTreePreorderTraversal;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NAryTreePreorderTraversalTest {

    @Test
    void examples() {
        NAryTreePreorderTraversal.Node root = new NAryTreePreorderTraversal.Node(1, new NAryTreePreorderTraversal.Node(3, new NAryTreePreorderTraversal.Node(5), new NAryTreePreorderTraversal.Node(6)), new NAryTreePreorderTraversal.Node(2), new NAryTreePreorderTraversal.Node(4));
        assertEquals(List.of(1, 3, 5, 6, 2, 4), NAryTreePreorderTraversal.preorder(root));
        assertEquals(List.of(1, 3, 5, 6, 2, 4), NAryTreePreorderTraversal.preorderIterative(root));
        assertEquals(List.of(), NAryTreePreorderTraversal.preorderIterative(null));
        NAryTreePreorderTraversal.Node deep = new NAryTreePreorderTraversal.Node(1, new NAryTreePreorderTraversal.Node(2), new NAryTreePreorderTraversal.Node(3, new NAryTreePreorderTraversal.Node(6), new NAryTreePreorderTraversal.Node(7, new NAryTreePreorderTraversal.Node(11, new NAryTreePreorderTraversal.Node(14)))),
                new NAryTreePreorderTraversal.Node(4, new NAryTreePreorderTraversal.Node(8, new NAryTreePreorderTraversal.Node(12))), new NAryTreePreorderTraversal.Node(5, new NAryTreePreorderTraversal.Node(9, new NAryTreePreorderTraversal.Node(13)), new NAryTreePreorderTraversal.Node(10)));
        assertEquals(List.of(1, 2, 3, 6, 7, 11, 14, 4, 8, 12, 5, 9, 13, 10), NAryTreePreorderTraversal.preorderIterative(deep));
        assertEquals(NAryTreePreorderTraversal.preorder(deep), NAryTreePreorderTraversal.preorderIterative(deep));
    }
}
