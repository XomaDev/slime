package xyz.kumaraswamy.slime.lex;

import xyz.kumaraswamy.slime.Slime;

import java.util.LinkedList;

public class Lex {
    private final String data;
    private final Slime slime;

    private final LinkedList<String> tokens = new LinkedList<>();

    private final StringBuilder builder = new StringBuilder();
    private final StringBuilder number = new StringBuilder();

    public Lex(final String text, Slime slime) {
        data = text;
        this.slime = slime;
    }

    public String[] lex() {
        boolean inside = false;

        int pos = 0;
        for (char ch : data.toCharArray()) {
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
                if (previous.equals("-") && slime.isOperator(size == 0
                        ? "" : tokens.get(size - 1))) {
                    tokens.set(size, "-" + ch);
                } else if (isDigits(previous) && size != -1) {
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
                if (pos > 0 && data.charAt(pos - 1) == ' ') {
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
