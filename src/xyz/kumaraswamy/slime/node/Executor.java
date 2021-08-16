package xyz.kumaraswamy.slime.node;

import xyz.kumaraswamy.slime.Space;
import xyz.kumaraswamy.slime.Symbol;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.ArrayList;

public class Executor {

    private final Space space;

    public Executor(Space space) {
        this.space = space;
    }

    public void execute(ArrayList<Token> parse) throws Exception {
        final NodeReader reader =  new NodeReader(space);

        Token previous = null;
        for(final Token token : parse) {
            if (token.getSymbol() == Symbol.ASSIGNMENT && previous != null) {
                space.add(previous.getValue().toString(), cutExec(2, parse, reader));
            } else if (token.getValue().equals("print")) {
                System.out.println(cutExec(1, parse, reader));
            }
            previous = token;
        }
    }

    private static Object cutExec(int from, ArrayList<Token> tokens, NodeReader reader) throws Exception {
        return reader.parse(NodeCreator.createNode(tokens.subList(from, tokens.size())));
    }
}
