package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.lex.Lex;
import xyz.kumaraswamy.slime.node.Node;
import xyz.kumaraswamy.slime.node.NodeCreator;
import xyz.kumaraswamy.slime.parse.Parse;
import xyz.kumaraswamy.slime.parse.Token;

import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class Slime {

    private final Space space;
    private final Processor processor;
    private SlimeMethods methods = null;

    public Slime(Space space) {
        this.space = space;
        processor = new Processor(space);
    }

    public Slime(Space space, SlimeMethods methods) {
        this(space);
        this.methods = methods;
    }

    public void exec(final String text) throws Exception {
        if (methods == null) {
            methods = new SlimeMethods();
        }
        final String[] lexed = new Lex(text).lex();

        if (lexed.length == 0) {
            return;
        }

        final Token[] tokens = Parse.parse(lexed);
        final Object[] results = (Object[]) NodeCreator.createNode(
                tokens, true, methods);
        final Object result = processor.process((Node) results[0]);
        if (result == null || results[1] == null) {
            return;
        }
        final String[] names = (String[]) results[1];
        final int len = names.length;

        if (len == 2 && names[1].equals("=")) {
            final String name = names[0];

            if (Lex.alphabets(name)) {
                space.add(name, result);
                return;
            }
        } else if (len == 1) {
            invoke(names[0], result);
            return;
        }
        throw new Exception("Invalid syntax provided");
    }

    private void invoke(final String name, final Object result) throws Exception {
        final Method method = methods.getClass().getMethod(name, Object.class);
        method.invoke(methods, result);
    }
}
