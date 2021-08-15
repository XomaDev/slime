package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.lex.Lex;
import xyz.kumaraswamy.slime.parse.Parser;

import java.util.LinkedList;

public class Interpreter {
    public void exec(String text) throws Exception {
        final LinkedList<String> tokens = Lex.tokenize(text);
        Parser.parse(tokens);
    }
}
