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

public class NodeCreator {
    public static Object createNode(Token[] tokens, boolean separators, SlimeMethods methods) throws Exception {
        final ArrayList<Integer> indexL = new ArrayList<>(),
                indexR = new ArrayList<>();
        final ArrayList<ArrayList<Token>> blocks = new ArrayList<>();

        int openBraces = 0, index = 1, i = 0;
        for (final Token o : tokens) {
            final Object value = o.getValue().toString();

            if (value != null) {
                if (value.equals("(") && ++openBraces == 1) {
                    indexL.add(index);
                }
                else if (value.equals(")") && --openBraces == 0) {
                    indexR.add(index);
                    blocks.add(new ArrayList<>(Arrays.asList(Arrays.copyOfRange(
                            tokens, indexL.get(i), indexR.get(i++) - 1))));
                }
                index++;
            }
        }
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
        if (openBraces != 0) {
            throw new Exception("Missing one or more right or left parenthesis");
        }
        if (tokensFormatted.size() != 0 && tokensFormatted.get(0).getLabel() == Label.NODE) {
            // provide the least start values
            tokensFormatted.add(0, Data.emptyToken);
            tokensFormatted.add(1, Data.plusToken);
        }
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
                                    ? (Node) createNode(blocks.get(blockIndex++).toArray(
                                            new Token[0]), false, methods)
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

    private static String[] generate(ArrayList<Token> formatted) {
        final String[] tokens = new String[formatted.size()];

        for (int i = 0; i < formatted.size(); i++) {
            tokens[i] = valueOf(formatted.get(i).getValue());
        }
        formatted.clear();
        return tokens;
    }
}