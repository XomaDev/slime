package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.parse.Token;

public class Constants {
    public static final String QUOTES = "'\"";
    public static final String ASSIGN = "=";

    public static final String leftOperator = "(";
    public static final String rightOperator = ")";

    public static final Token add = new Token(Symbol.SYMBOL, "+");
}
