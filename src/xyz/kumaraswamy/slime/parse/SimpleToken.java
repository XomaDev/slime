package xyz.kumaraswamy.slime.parse;

public class SimpleToken extends Token{

    public SimpleToken(Label label) {
        super(null, label);
    }

    @Override
    public String toString() {
        return '[' + String.valueOf(super.getLabel()) + ']';
    }
}
