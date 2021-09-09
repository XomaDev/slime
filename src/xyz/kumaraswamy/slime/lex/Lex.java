package xyz.kumaraswamy.slime.lex;

import xyz.kumaraswamy.slime.Extra;

import java.io.IOException;
import java.util.LinkedList;

public class Lex {
    private final Object data;

    private final LinkedList<String> tokens = new LinkedList<>();

    private final StringBuilder builder = new StringBuilder();
    private final StringBuilder number = new StringBuilder();

    public Lex(final String text) {
        data = text;
    }

    public String[] lex() throws IOException {
        final String text = new String(Extra.read(data));

        boolean inside = false;

        int pos = 0;
        for (char ch : text.toCharArray()) {
            final String previous = tokens.size() == 0
                    ? "" : tokens.getLast();
            final int size = tokens.size() - 1;

            if (ch == '\'') {
                inside = !inside;
                builder.append(ch);
            } else if (inside) {
                builder.append(ch);
            } else if (ch == ' ' || isBlock(ch)) {
                appendTokens();
                if (ch != ' ') {
                    tokens.add(ch + "");
                }
            } else if (isDigit(ch)) {
                if (previous.equals("-") && isOperator(size == 0
                        ? "" : tokens.get(size - 1))) {
                    tokens.set(size, "-" + ch);
                } else if (isDigits(previous)) {
                    String append = previous + ch;
                    if (append.startsWith(".")) {
                        append = '0' + append;
                    }
                    tokens.set(size, append);
                } else {
                    tokens.add(ch + "");
                }
            } else {
                appendTokens();
                if (pos > 0 && text.charAt(pos - 1) == ' ') {
                    tokens.add(ch + "");
                } else if (isLetter(ch) && alphabets(previous)) {
                    tokens.set(size, previous + ch);
                } else {
                    tokens.add(ch + "");
                }
            }
            pos++;
        }
        appendTokens();
        return tokens.toArray(new String[0]);
    }

    public static boolean isDigits(final String token) {
        if (token.length() == 1 && token.charAt(0) == '-') {
            return false;
        }
        boolean firstChar = false;
        for (char ch : token.toCharArray()) {
            if (!firstChar) {
                if (!(ch == '-') && !Lex.isDigit(ch)) {
                    return false;
                }
                firstChar = true;
            } else if (!isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOperator(String text) {
        if (text.length() != 1) {
            return false;
        }
        final char ch = text.charAt(0);
        return ch == '=' || isBlock(ch) || isOperator(ch);
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '/' || ch == '*';
    }

    public static boolean alphabets(final String text) {
        for (char ch : text.toCharArray()) {
            if (!isLetter(ch)) {
                return false;
            }
        }
        return !text.isBlank();
    }

    public static boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9' || ch == '.';
    }

    private static boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    public static boolean isBlock(char ch) {
        return ch == '(' || ch == ')';
    }

    private void appendTokens() {
        append(builder);
        append(number);
    }

    private void append(StringBuilder builder) {
        final String build = builder.toString().trim();

        if (!build.isBlank()) {
            tokens.add(build);
        }
        builder.setLength(0);
    }
}
