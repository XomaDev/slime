package xyz.kumaraswamy.slime.lex;

import xyz.kumaraswamy.slime.Constants;

import java.util.ArrayList;
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
            final String charc = ch + "";
            final String previous = modified.size() == 0
                    ? "" : modified.getLast();
            final int size = modified.size() - 1;

            if (Constants.QUOTES.contains(charc)) {
                inside = !inside;
                builder.append(ch);
            } else if (inside) {
                builder.append(ch);
            } else if (charc.isBlank()) {
                append(builder, modified);
                append(number, modified);
            } else if (charc.matches("[0-9]")) {
                if (previous.equals("-") && modified.size() > 2
                        && modified.get(size - 1).matches("[+\\-/*]")) {
                    modified.set(size, '-' + charc);
                } else {
                    number.append(ch);
                }
            } else if (modified.size() != 0 && charc.matches("[a-zA-Z]")
                    && previous.matches("[a-z-A-Z]+")) {
                modified.set(size, previous + charc);
            } else {
                System.out.println("add: " + charc);
                modified.add(charc);
            }
            if (++pos == characters.length()) {
                append(number, modified);
            }
        }
        return modified;
    }

    private static void append(StringBuilder number, LinkedList<String> modified) {
        final String build = number.toString();

        if (!build.isEmpty()) {
            modified.add(build);
            number.setLength(0);
        }
    }
}
