package xyz.kumaraswamy.slime.node;

import xyz.kumaraswamy.slime.Symbol;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.ArrayList;

public class Creator {
    public static ArrayList<Node> create(ArrayList<Token> parse) {
        Token previous = null;

        for(Token token : parse) {
            if (token.getSymbol() == Symbol.ASSIGNMENT) {
                System.out.println(NodeCreator.createNode(parse.subList(2, parse.size())));
            }
            previous = token;
        }
        return null;
    }
}
