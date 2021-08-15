package xyz.kumaraswamy.slime.parse;

import xyz.kumaraswamy.slime.Symbol;

public class SimpleToken extends Token {
    public SimpleToken(Symbol symbol) {
        super(symbol, null);
    }

    @Override
    public String toString() {
        return super.getSymbol().name();
    }
}
