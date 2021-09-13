package xyz.kumaraswamy.slime.parse;

import java.util.Arrays;

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

    private String valueAsString() {
        return value instanceof Object[]
                ? Arrays.toString((Object[]) value)
                : value + "";
    }

    @Override
    public String toString() {
        return "[" +
                "value=" + valueAsString() +
                ", label=" + label +
                ']';
    }
}
