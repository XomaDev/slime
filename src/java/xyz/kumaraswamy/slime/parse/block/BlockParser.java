package xyz.kumaraswamy.slime.parse.block;

import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.ArrayList;
import java.util.List;

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
        final int[] positionsR = new int[positions.size()];
        final List<List<String>> blocks = new ArrayList<>();

        for (int j = 0; j < positions.size(); j++) {
            int pos = positions.get(j);

            final List<String> elements = new ArrayList<>();

            for (int i = pos; i < tokens.length; i++) {
                final String element = tokens[i];
                if (element.equals("(") && ++open == 1) {
                    continue;
                } else if (element.equals(")") && --open == 0) {
                    positionsR[j] = i;
                    break;
                }
                elements.add(element);
            }
            if (open != 0) {
                throw new IllegalArgumentException();
            }
            blocks.add(elements);
        }
        RecursiveParser recursiveParser = new RecursiveParser(slime, blocks, this);
        boolean isOpen = false;

        for (int i = 0; i < tokens.length; i++) {
            final String element = tokens[i];
            if (element.equals("(") && positions.contains(i)) {
                recursiveParser.put(new Indicator());
                isOpen = true;
            } else if (element.equals(")") && contains(positionsR, i)) {
                isOpen = false;
            } else if (!isOpen) {
                recursiveParser.put(element);
            }
        }
        return recursiveParser.getTokens();
    }

    private boolean contains(int[] positionsR, int n) {
        for (int pos : positionsR) {
            if (pos == n) {
                return true;
            }
        }
        return false;
    }
}
