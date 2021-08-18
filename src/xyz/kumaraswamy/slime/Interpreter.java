package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.lex.Lex;
import xyz.kumaraswamy.slime.node.Executor;
import xyz.kumaraswamy.slime.parse.Parser;

public class Interpreter {
    private final Executor executor;
    private final Lex lex = new Lex();

    public Interpreter(final Space space) {
        executor = new Executor(space);
    }

    public void exec(String text) throws Exception {
        lex.prepareNext();
        executor.execute(Parser.parse(lex.tokenize(text)));
    }
}
