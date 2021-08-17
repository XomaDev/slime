package xyz.kumaraswamy.slime.lex;

import xyz.kumaraswamy.slime.Constants;

import java.util.LinkedList;

public class Lex {
    public static LinkedList<String> tokenize(final String characters) {
        final LinkedList<String> modified = new LinkedList<>();

        // Quotes
        final StringBuilder builder = new StringBuilder();
        boolean inside = false;

        // Numbers
        final StringBuilder number = new StringBuilder();

        int pos = 0;
        for (char ch : characters.toCharArray()) {
            final String character = ch + "";

            final String previous = modified.size() == 0
                    ? "" : modified.getLast();
            final int size = modified.size() - 1;

            if (ch == Constants.QUOTE) {
                inside = !inside;
                builder.append(ch);
            } else if (inside) {
                builder.append(ch);
            } else if ((ch == ' ' && !previous.matches("[a-z-A-Z]+")) || character.matches("[()]")) {
                append(builder, number, modified);
                if (!character.isBlank()) {
                    modified.add(character);
                }
            } else if (ch == ';') {
              builder.setLength(0);
              number.setLength(0);
            } else if (character.matches("[0-9]")) {
                if (previous.equals("-") && (size - 1) > 2
                        && modified.get(size - 1).matches("[+\\-/*]")) {
                    modified.set(size, '-' + character);
                } else if ((previous.startsWith("-") && previous.matches("-[0-9]+"))
                        || previous.matches("[0-9]+")) {
                    modified.set(size, previous + character);
                } else {
                    modified.add(character);
                }
            } else if (modified.size() != 0 && character.matches("[a-zA-Z]")
                    && previous.matches("[a-z-A-Z]+")) {
                modified.set(size, previous + character);
            } else {
                modified.add(character);
            }
            if (++pos == characters.length()) {
                append(builder, number, modified);
            }
        }
        return modified;
    }

    private static void append(StringBuilder builder, StringBuilder number, LinkedList<String> modified) {
        append(builder, modified);
        append(number, modified);
    }

    private static void append(StringBuilder builder, LinkedList<String> modified) {
        final String build = builder.toString();
        if (build.isEmpty()) {
            return;
        }
        modified.add(build);
        builder.setLength(0);
    }
}
