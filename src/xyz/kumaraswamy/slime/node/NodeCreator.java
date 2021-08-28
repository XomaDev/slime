package xyz.kumaraswamy.slime.node;

import xyz.kumaraswamy.slime.Constants;
import xyz.kumaraswamy.slime.Symbol;
import xyz.kumaraswamy.slime.parse.Operator;
import xyz.kumaraswamy.slime.parse.SimpleToken;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeCreator {
    public static Node createNode(List<Token> tokens) {
        final ArrayList<Integer> indexL = new ArrayList<>(),
                indexR = new ArrayList<>();

        final ArrayList<ArrayList<Token>> bracketNode = new ArrayList<>();
        final Token[] tokensArray = tokens.toArray(new Token[0]);

        int openBraces = 0, index = 1, i = 0;
        for (Token o: tokens) {
            final Object value = o.getValue();
            if (value == null) {
                continue;
            }
            if (value.equals(Constants.leftOperator)
                    && ++openBraces == 1) {
                indexL.add(index);
            } else if (value.equals(Constants.rightOperator)
                    && --openBraces == 0) {

                indexR.add(index);
                bracketNode.add(
                        new ArrayList<>(Arrays.asList(
                                Arrays.copyOfRange(
                                        tokensArray, indexL.get(i), indexR.get(i++) - 1)
                        ))
                );
            }
            index++;
        }
        final ArrayList<Token> tokensFormatted = new ArrayList<>();

        openBraces = 0;

        for (Token o: tokens) {
            final Object value = o.getValue();
            if (value == null) {
                continue;
            }
            if (value.equals(Constants.leftOperator) && ++openBraces == 1) {
                tokensFormatted.add(new SimpleToken(Symbol.NODE));
            } else if (value.equals(Constants.rightOperator)) {
                openBraces--;
            } else if (openBraces == 0 && !value.equals(Constants.rightOperator)) {
                tokensFormatted.add(o);
            }
        }
        tokens = tokensFormatted;
        System.out.println(tokensFormatted);
        if (tokens.size() != 0 && tokens.get(0).getSymbol() == Symbol.NODE) {
            // provide the least start values
            tokens.add(0, new Token(Symbol.STRING, "''"));
            tokens.add(1, new Token(Symbol.SYMBOL, new Operator("+")));
        }
        Object lastValObj = null;
        Operator lastEnum = null;

        int parenthesisI = 0;
        Node thisNode = null;

        int pos = 1;
        for (Token token : tokens) {
            Object value = token.getValue();

            if (value == null) {
                value = token.getSymbol();
            }

            if (value instanceof Operator) {
                lastEnum = (Operator) value;
            } else {
                final boolean isParenNode = token.getSymbol() == Symbol.NODE;
                if (isParenNode || lastEnum != null && lastValObj != null) {
                    thisNode = new Node(lastEnum).setLeft(
                            thisNode == null ?
                                    new SimpleNode(lastValObj) // left num
                                    : thisNode
                    ).setRight(
                            isParenNode
                                    ? createNode(bracketNode.get(parenthesisI++))
                                    : new SimpleNode(value)
                    );
                } else if (pos == tokens.size() && thisNode == null) {
                    thisNode = new SimpleNode(value);
                }
                lastValObj = value;
            }
            pos++;
        }
        return thisNode;
    }
}
