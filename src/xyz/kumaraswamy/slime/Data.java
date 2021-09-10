package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.operators.*;
import xyz.kumaraswamy.slime.parse.Label;
import xyz.kumaraswamy.slime.parse.Operator;
import xyz.kumaraswamy.slime.parse.Token;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Data {
    public static final Token emptyToken = new Token("''", Label.STRING);
    public static final Token plusToken = new Token(new Operator("+"), Label.OPERATOR);

    public static boolean isMethod(final String object, SlimeMethods methods) {
        for (Method method : methods.getClass().getMethods()) {
            if (object.equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    public static final HashMap<String, xyz.kumaraswamy.slime.operators.Operator> operators = new HashMap<>();

    static {
        final Object[][] operatorMaps = new Object[][] {
                new Object[] {"+", new Add()},
                new Object[] {"-", new Subtract()},
                new Object[] {"/", new Divide()},
                new Object[] {"*", new Multiply()},
                new Object[] {"^", new Power()}
        };

        for (Object[] operator : operatorMaps) {
            operators.put((String) operator[0],
                    (xyz.kumaraswamy.slime.operators.Operator) operator[1]);
        }
    }
}
