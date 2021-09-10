package xyz.kumaraswamy.slime.parse;

/**
 * A class to store the value and
 * the token type, created in Parser class
 * to keep track of the value type
 */

public class Token {
    private final Object value;
    private final Label label;

    public Token(final Object value, final Label label) {
        this.value = value;
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "[" +
                "value=" + value +
                ", label=" + label +
                ']';
    }
}
