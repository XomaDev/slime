package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.lex.Lex;
import xyz.kumaraswamy.slime.node.Creator;
import xyz.kumaraswamy.slime.parse.Parser;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.ArrayList;
import java.util.LinkedList;

public class Interpreter {
    public void exec(String text) throws Exception {
        final LinkedList<String> tokens = Lex.tokenize(text);
        final ArrayList<Token> parse = Parser.parse(tokens);
        Creator.create(parse);
    }
}
