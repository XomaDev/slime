package xyz.kumaraswamy.slime.parse;

import xyz.kumaraswamy.slime.Constants;
import xyz.kumaraswamy.slime.Symbol;

import java.util.ArrayList;
import java.util.LinkedList;

public class Parser {
    public static ArrayList<Token> parse(final LinkedList<String> tokens) throws Exception {
        final ArrayList<Token> modified = new ArrayList<>();

        for (final String token : tokens) {
            final Object object = Help.isValue(token);
            Token parseToken = null;

            if (object != null) {
                parseToken = new Token(Symbol.STRING, token);
            } else if (token.matches("[a-zA-Z]+|[()]")) {
                parseToken = new Token(Symbol.SYMBOL, token);
            } else if (token.matches("[+\\-/*]")) {
                parseToken = new Token(Symbol.SYMBOL, new Operator(token));
            } else if (isNumber(token)) {
                parseToken = new Token(Symbol.NUMBER, Double.parseDouble(token));
            } else if (token.equals(Constants.ASSIGN)) {
                parseToken = new SimpleToken(Symbol.ASSIGNMENT);
            } else if (!token.isBlank()) {
                throw new ParseException("Invalid syntax '" + token + "'");
            }

            if (parseToken != null) {
                modified.add(parseToken);
            }
        }
        return modified;
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}

class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}