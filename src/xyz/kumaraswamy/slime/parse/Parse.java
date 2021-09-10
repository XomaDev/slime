package xyz.kumaraswamy.slime.parse;

import xyz.kumaraswamy.slime.Help;
import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.lex.Lex;

public class Parse {

    public static Token[] parse(final String[] tokens, Slime slime) throws Exception {
        final Token[] modified = new Token[tokens.length];

        int pos = 0;
        for (final String token : tokens) {
            final Label label;
            Object value = token;

            if (Help.isValue(token) != null) {
                label = Label.STRING;
            } else if (isBlock(token) || slime.isOperator(token)) {
                label = Label.OPERATOR;
                value = new Operator(token);
            } else if (Lex.alphabets(token)) {
                label = Label.SYMBOL;
            } else if (Lex.isDigits(token)) {
                label = Label.NUMBER;
                value = Double.parseDouble(token);
            }  else {
                throw new Exception("Unexpected value '" + token + "'");
            }
            modified[pos++] = new Token(value, label);
        }
        return modified;
    }

    private static boolean isBlock(final String token) {
        if (token.length() != 1) {
            return false;
        }
        return Lex.isBlock(token.charAt(0));
    }
}
