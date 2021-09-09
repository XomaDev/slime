package xyz.kumaraswamy.slime.node;

public class SimpleNode extends Node {
    private final Object number;

    public SimpleNode(Object value) {
        super(value);
        number = value;
    }

    @Override
    public String toString() {
        return "[" + "val=" + number + ']';
    }
}