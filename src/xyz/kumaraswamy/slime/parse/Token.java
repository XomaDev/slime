package xyz.kumaraswamy.slime.parse;

import xyz.kumaraswamy.slime.Symbol;

public class Token {
    private final Symbol symbol;
    private final Object value;

    public Token(Symbol symbol, Object value) {
        this.symbol = symbol;
        this.value = value;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + "symbol=" + symbol + ", value='" + value + '\'' + ']';
    }
}
