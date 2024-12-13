package org.util;

import javafx.scene.Node;
import javafx.scene.Parent;

public class RootFinder {
    public static Parent findRoot(Node currentNode) {
        Parent parent = currentNode.getParent();
        while (parent != null && parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent;
    }
}
