package xyz.kumaraswamy.slime.parse.block;

import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.parse.Label;
import xyz.kumaraswamy.slime.parse.Parse;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RecursiveParser {

    private final Slime slime;
    private final List<List<String>> blocks;
    private final BlockParser parser;
    private final LinkedList<Token> elements = new LinkedList<>();

    private Object previous = null;
    private int blockIndex = 0;

    public RecursiveParser(Slime slime, List<List<String>> blocks, BlockParser parser) {
        this.slime = slime;
        this.blocks = blocks;
        this.parser = parser;
    }

    public void put(final Object element) throws Exception {
        if (element instanceof String) {
            elements.add(Parse.getToken((String) element, slime));
        } else {
            processBlock();
        }
        previous = element;
    }

    public Token[] getTokens() {
        return elements.toArray(new Token[0]);
    }

    private void processBlock() throws Exception {
        final Token[] tokens = parser.parse(blocks.get(
                blockIndex++).toArray(new String[0]), slime);

        if (previous instanceof String &&
                slime.isFunctionName(previous.toString())) {
            // remove the last element because it's
            // just a function name
            elements.removeLast();
            elements.add(new Token(
                            new Block(previous.toString(), tokens, slime),
                            Label.METHOD
                    )
            );
            return;
        }

        elements.add(new Token("(", Label.OPERATOR));
        elements.addAll(Arrays.asList(tokens));
        elements.add(new Token(")", Label.OPERATOR));
    }
}
