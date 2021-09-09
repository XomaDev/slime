package xyz.kumaraswamy.slime.node;

public class Node {
    private final Object value;
    private Node left, right;

    public Node(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public boolean hasNextLeft() {
        return left != null;
    }

    public Node setLeft(Node left) {
        this.left = left;
        return this;
    }

    public Node setRight(Node right) {
        this.right = right;
        return this;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "[" + "value=" + value + ", left=" + left + ", right=" + right + ']';
    }
}