package xyz.kumaraswamy.slime.node;

import xyz.kumaraswamy.slime.Data;
import xyz.kumaraswamy.slime.SlimeMethods;
import xyz.kumaraswamy.slime.parse.Label;
import java.util.ArrayList;
import java.util.Arrays;

import xyz.kumaraswamy.slime.parse.Operator;
import xyz.kumaraswamy.slime.parse.SimpleToken;
import xyz.kumaraswamy.slime.parse.Token;

import static java.lang.String.valueOf;
import static xyz.kumaraswamy.slime.Data.isMethod;

/**
 * Takes in the tokens and creates a node tree
 * that will be Processed by the Processor.class
 */

public class NodeCreator {

    /**
     * Creates a node tree and returns the initializers
     * (values before expression)
     * @param separators To return just a node or
     *                   with to initialize
     * @param methods To check if a statement is a
     *                value method call
     * @return Node tree (with initializers)
     */

    public static Object createNode(Token[] tokens, boolean separators, SlimeMethods methods) throws Exception {
        // marks the indexes for the sub expressions (blocks)

        final ArrayList<Integer> indexL = new ArrayList<>(),
                indexR = new ArrayList<>();

        // store the sub expressions (blocks)
        // the program will itself recall to create a
        // sub node tree

        final ArrayList<Token[]> blocks = new ArrayList<>();

        int openBraces = 0, index = 1, i = 0;
        for (final Token o : tokens) {
            final Object value = o.getValue().toString();

            if (value != null) {
                if (value.equals("(") && ++openBraces == 1) {
                    indexL.add(index);
                } else if (value.equals(")") && --openBraces == 0) {
                    indexR.add(index);
                    blocks.add(((Arrays.copyOfRange(
                            tokens, indexL.get(i), indexR.get(i++) - 1))));
                }
                index++;
            }
        }

        // a process to format the tokens accordingly
        // removing the sub (expressions) and replacing it with
        // a SimpleToken with a label Node to indicate that a sub node

        final ArrayList<Token> tokensFormatted = new ArrayList<>();
        String[] operationSeparator = null;

        openBraces = 0;

        Token previousToken = null;

        for (final Token token : tokens) {
            final Object value = token.getValue();
            if (value == null) {
                continue;
            }
            final String tokenValue = value.toString();

            // check if the token's value is a method call

            if (previousToken != null && previousToken.getLabel() == Label.SYMBOL
                    && token.getLabel() != Label.OPERATOR) {
                if (!isMethod(valueOf(previousToken.getValue()), methods)) {
                    throw new Exception("Cannot find symbol '" + previousToken.getValue() + "'");
                }
            }
            if (tokenValue.equals("=") || isMethod(tokenValue, methods)) {
                tokensFormatted.add(token);
                operationSeparator = generate(tokensFormatted);
            } else if (tokenValue.equals("(") && ++openBraces == 1) {
                tokensFormatted.add(new SimpleToken(Label.NODE));
            } else if (tokenValue.equals(")")) {
                openBraces--;
            } else if (openBraces == 0) {
                tokensFormatted.add(token);
            }
            previousToken = token;
        }

        // check if all the braces are closed
        // and are not missing

        if (openBraces != 0) {
            throw new Exception("Missing one or more right or left parenthesis");
        }
        if (tokensFormatted.size() != 0 && tokensFormatted.get(0).getLabel() == Label.NODE) {
            // provide the least start values
            tokensFormatted.add(0, Data.emptyToken);
            tokensFormatted.add(1, Data.plusToken);
        }

        // creates the node tree with the formatted tokens
        // and indexes of the sub nodes (blocks)

        Operator lastEnum = null;
        Object lastValObj = null;

        int blockIndex = 0, pos = 1;
        Node headNode = null;

        for (final Token token : tokensFormatted) {
            Object value = token.getValue();

            if (value == null) {
                value = token.getLabel();
            }
            if (value instanceof Operator) {
                lastEnum = (Operator) value;
            } else {
                final boolean isBlock = token.getLabel() == Label.NODE;
                if (isBlock || lastEnum != null && lastValObj != null) {
                    headNode = new Node(lastEnum).setLeft(
                            headNode == null ?
                                    new SimpleNode(lastValObj)
                                    : headNode
                    ).setRight(
                            isBlock
                                    ? (Node) createNode(blocks.get(blockIndex++), false, methods)
                                    : new SimpleNode(value)
                    );
                } else if (pos == tokensFormatted.size() && headNode == null) {
                    headNode = new SimpleNode(value);
                }
                lastValObj = value;
            }
            pos++;
        }
        return separators ? new Object[] {
                headNode, operationSeparator } : headNode;
    }

    /**
     * @return the values for all the tokens
     */

    private static String[] generate(ArrayList<Token> formatted) {
        final String[] tokens = new String[formatted.size()];

        for (int i = 0; i < formatted.size(); i++) {
            tokens[i] = valueOf(formatted.get(i).getValue());
        }
        formatted.clear();
        return tokens;
    }
}