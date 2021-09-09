package xyz.kumaraswamy.slime.parse;

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
