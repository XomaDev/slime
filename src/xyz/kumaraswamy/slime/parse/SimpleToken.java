package xyz.kumaraswamy.slime.parse;

/**
 * A class same as the Token.class
 * but only storing a label as the value
 */

public class SimpleToken extends Token {

    public SimpleToken(Label label) {
        super(null, label);
    }

    @Override
    public String toString() {
        return '[' + String.valueOf(super.getLabel()) + ']';
    }
}
