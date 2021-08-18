package xyz.kumaraswamy.slime.lex;
import java.util.LinkedList;

import static xyz.kumaraswamy.slime.parse.Parser.isNumber;

public class Lex {
    // Quotes
    private final StringBuilder builder = new StringBuilder();

    // Numbers
    private final StringBuilder number = new StringBuilder();

    // Tokens
    private final LinkedList<String> modified = new LinkedList<>();

    public void prepareNext() {
        clear();
        modified.clear();
    }

    public LinkedList<String> tokenize(final String characters) {
        boolean inside = false;

        int pos = 0;
        for (char ch : characters.toCharArray()) {
            final String character = ch + "";

            final String previous = modified.size() == 0
                    ? "" : modified.getLast();
            final int size = modified.size() - 1;

            if (ch == '\'') {
                inside = !inside;
                builder.append(ch);
            } else if (inside) {
                builder.append(ch);
            } else if ((ch == ' ' && !previous.matches("[a-z-A-Z]+")) || isBlock(ch)) {
                append();
                if (!character.isBlank()) {
                    modified.add(character);
                }
            } else if (isDigit(ch)) {
                final int post = size - 1;
                final boolean isPreviousNumber = previous.matches("-[0-9]+");
                final boolean isFloat = previous.contains(".") && isNumber(previous);

                if (previous.equals("-") && post > 2
                        && modified.get(size - 1).matches("[+\\-/*]")) {
                    modified.set(size, '-' + character);
                }

                else if (isPreviousNumber && ch == '.') {
                    modified.set(size, previous + character);
                } else if (isFloat) {
                    modified.set(size, previous + character);
                }

                else if (previous.startsWith("-") && isPreviousNumber
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
                append();
            }
        }
        System.out.println(modified);
        return modified;
    }

    private void clear() {
        number.setLength(0);
        builder.setLength(0);
    }

    private boolean isDigit(char ch) {
        return 48 <= ch && ch <= 57 || ch == '.';
    }

    private boolean isBlock(char ch) {
        return ch == '(' || ch == ')';
    }

    private void append() {
        append(builder);
        append(number);
    }

    public void append(StringBuilder builder) {
        final String build = builder.toString().trim();
        if (build.isEmpty()) {
            return;
        }
        modified.add(build);
        builder.setLength(0);
    }
}
