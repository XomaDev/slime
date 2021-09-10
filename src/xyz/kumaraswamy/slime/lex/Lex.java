package xyz.kumaraswamy.slime.lex;

import xyz.kumaraswamy.slime.Slime;

import java.util.LinkedList;

// a class to lex texts and form tokens
// this is the first step to create a node tree

public class Lex {

    private final String data;
    private final Slime slime;

    private final LinkedList<String> tokens = new LinkedList<>();

    // StringBuilders to append and store
    // the texts and numbers

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

            // keep track, if we are inside the
            // single quote or not

            if (ch == '\'') {
                inside = !inside;
                builder.append(ch);
            } else if (inside) {
                // simply append the char
                // if we are inside
                builder.append(ch);
            } else if (ch == ' ' || isBlock(ch)) {
                // a space is also used as a
                // delimiter here
                appendTokens();
                if (ch != ' ') {
                    tokens.add(ch + "");
                }
            } else if (isDigit(ch)) {
                // check if the character is a digit

                // an exception for negative and
                // floating point numbers

                if (previous.equals("-") && slime.isOperator(size == 0
                        ? "" : tokens.get(size - 1))) {
                    tokens.set(size, "-" + ch);
                } else if (isDigits(previous) && size != -1) {
                    // check if previous token was only digits
                    // if so, append that with the current char
                    String append = previous + ch;
                    if (append.startsWith(".")) {
                        // just add `0` in front of
                        // short decimals point values like .7
                        append = '0' + append;
                    }
                    tokens.set(size, append);
                } else {
                    tokens.add(ch + "");
                }
            } else {
                // do the same thing for characters
                // as did with the numbers

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
        // append the remaining values present
        // in the StringBuilders
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

    // check if a character is in range
    // of 0-9 or char is '.'

    public static boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9' || ch == '.';
    }

    // check if a characters is in range
    // of a-zA-Z

    private static boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    public static boolean isBlock(char ch) {
        return ch == '(' || ch == ')';
    }

    // append the builders to tokens
    // and reset(clear) them

    private void appendTokens() {
        append(builder);
        append(number);
    }

    // appends and clears the tokens

    private void append(StringBuilder builder) {
        final String build = builder.toString().trim();

        if (!build.isBlank()) {
            tokens.add(build);
        }
        builder.setLength(0);
    }
}
