package xyz.kumaraswamy.slime.parse;

import xyz.kumaraswamy.slime.Help;
import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.lex.Lex;

/**
 * A class the takes in the Tokens
 * provided by the lexer and marks values
 * accordingly to help while processing
 */

public class Parse {
    public static Token[] parse(final String[] tokens, Slime slime) throws Exception {
        final Token[] modified = new Token[tokens.length];

        int pos = 0;
        for (final String token : tokens) {
            modified[pos++] = getToken(token, slime);
        }
        return modified;
    }

    public static Token getToken(final String token, Slime slime) throws Exception {
        final Label label;
        Object value = token;

        if (Help.isValue(token) != null) {
            label = Label.STRING;
        } else if (Help.isBlock(token) || slime.isOperator(token)) {
            label = Label.OPERATOR;
            value = new Operator(token);
        } else if (Lex.alphabets(token)) {
            label = Label.SYMBOL;
        } else if (Lex.isDigits(token)) {
            label = Label.NUMBER;
            value = Double.parseDouble(token);
        } else if (token.equals(",")) {
            label = Label.COMMA;
        }  else {
            throw new Exception("Unexpected value '" + token + "'");
        }
        return new Token(value, label);
    }
}
