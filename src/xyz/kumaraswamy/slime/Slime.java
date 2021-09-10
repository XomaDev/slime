package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.operators.Operator;
import xyz.kumaraswamy.slime.lex.Lex;
import xyz.kumaraswamy.slime.node.Node;
import xyz.kumaraswamy.slime.node.NodeCreator;
import xyz.kumaraswamy.slime.parse.Parse;
import xyz.kumaraswamy.slime.parse.Token;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("unused")
public class Slime {

    private final Space space;
    private Processor processor = null;

    private SlimeMethods methods = null;

    private HashMap<String, Operator> operators = Data.operators;

    public Slime(final Space space) {
        this.space = space;
    }

    public Slime(final Space space, final SlimeMethods methods) {
        this(space);
        this.methods = methods;
    }

    public void setOperators(final HashMap<String, Operator> operators) {
        this.operators = operators;
    }

    public HashMap<String, Operator> getOperators() {
        return operators;
    }

    public void setOperator(final String symbol, final Operator operator) {
        operators.put(symbol, operator);
    }

    public void removeOperator(final String symbol) {
        operators.remove(symbol);
    }

    public boolean isOperator(final String check) {
        return check.equals("=") || operators.containsKey(check);
    }

    public void exec(final String text) throws Exception {
        final String[] lexed = new Lex(text, this).lex();

        if (lexed.length == 0) {
            return;
        }

        final Token[] tokens = Parse.parse(lexed, this);

        if (methods == null) {
            methods = new SlimeMethods();
        }

        final Object[] results = (Object[]) NodeCreator.createNode(
                tokens, true, methods);

        if (processor == null) {
            processor = new Processor(space, operators);
        }

        final Object result = processor.process((Node) results[0]);

        if (result == null || results[1] == null) {
            return;
        }

        final String[] names = (String[]) results[1];
        final int len = names.length;

        if (len == 2 && names[1].equals("=") && Lex.alphabets(names[0])) {
            final String name = names[0];

            if (Lex.alphabets(names[0])) {
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
