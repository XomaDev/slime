package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.lex.Lex;
import xyz.kumaraswamy.slime.node.Executor;
import xyz.kumaraswamy.slime.parse.Parser;

public class Interpreter {
    private final Executor executor;

    public Interpreter(final Space space) {
        executor = new Executor(space);
    }

    public void exec(String text) throws Exception {
        executor.execute(Parser.parse(Lex.tokenize(text)));
    }
}
