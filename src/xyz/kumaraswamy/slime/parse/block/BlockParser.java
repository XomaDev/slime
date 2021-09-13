package xyz.kumaraswamy.slime.parse.block;

import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.parse.Label;
import xyz.kumaraswamy.slime.parse.Parse;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * a class same as the @link Parse.class
 * but with just extra ability (functionality) to
 * parse methods as well.
 */

public class BlockParser {

    /**
     * Processes the string[] tokens provided by the
     * @link Lex.class
     * @param tokens Tokens that needs to be processed
     * @return Processed and parsed tokens[]
     * @throws Exception Something went wrong while processing
     */

    public Token[] parse(String[] tokens, Slime slime) throws Exception {
        int open = 0;
        final List<Integer> positions = new ArrayList<>(tokens.length);

        for (int i = 0; i < tokens.length; i++) {
            final String token = tokens[i];

            if (token.equals("(") && ++open == 1) {
                positions.add(i);
            } else if (token.equals(")")) {
                open--;
            }
        }
        if (open != 0) {
            throw new IllegalArgumentException();
        }
        final List<Integer> positionsR = new ArrayList<>(positions.size());
        final List<List<String>> blocks = new ArrayList<>();

        for (final int pos : positions) {
            final List<String> elements = new ArrayList<>();

            for (int i = pos; i < tokens.length; i++) {
                final String element = tokens[i];
                if (element.equals("(") && ++open == 1) {
                    continue;
                } else if (element.equals(")") && --open == 0) {
                    positionsR.add(i);
                    break;
                }
                elements.add(element);
            }
            if (open != 0) {
                throw new IllegalArgumentException();
            }
            blocks.add(elements);
        }
        final List<Object> tokensNext = new ArrayList<>();

        boolean isOpen = false;

        for (int i = 0; i < tokens.length; i++) {
            final String element = tokens[i];
            if (element.equals("(") && positions.contains(i)) {
                tokensNext.add(new Indicator());
                isOpen = true;
            } else if (element.equals(")") && positionsR.contains(i)) {
                isOpen = false;
            } else if (!isOpen) {
                tokensNext.add(element);
            }
        }
        final LinkedList<Token> tokens1 = new LinkedList<>();

        int blockElementGetPos = 0;
        Object previousElement = null;

        for (final Object element : tokensNext) {
            if (element instanceof Indicator) {
                final Token[] tokensElementz = parse(blocks.get(blockElementGetPos++).toArray(
                        new String[0]), slime);

                if (slime.isFunctionName(valueOf(previousElement))) {
                    tokens1.removeLast();
                    tokens1.add(new Token(new Block((String) previousElement,
                            tokensElementz, slime), Label.METHOD));
                } else {
                    tokens1.add(new Token("(", Label.OPERATOR));
                    tokens1.addAll(Arrays.asList(tokensElementz));
                    tokens1.add(new Token(")", Label.OPERATOR));
                }
            } else {
                tokens1.add(Parse.getToken((String) element, slime));
            }
            previousElement = element;
        }
        return tokens1.toArray(new Token[0]);
    }
}
