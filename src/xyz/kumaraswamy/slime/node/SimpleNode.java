package xyz.kumaraswamy.slime.node;

/**
 * A simple node used by NodeCreator
 * to represent values that no more has any
 * left or the right node
 */

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