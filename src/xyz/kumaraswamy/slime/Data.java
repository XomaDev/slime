package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.parse.Label;
import xyz.kumaraswamy.slime.parse.Operator;
import xyz.kumaraswamy.slime.parse.Token;

import xyz.kumaraswamy.slime.operators.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Data {

    // An empty token used by NodeCreator class to
    // provide the least start value for blocks (brackets)

    public static final Token emptyToken = new Token("''", Label.STRING);
    public static final Token plusToken = new Token(new Operator("+"), Label.OPERATOR);

    /**
     * Checks if a string is a method existing in SlimeMethods.class
     */

    public static boolean isMethod(final String object, SlimeMethods methods) {
        for (Method method : methods.getClass().getMethods()) {
            if (object.equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    // Initialize the Map with the default operators

    public static final HashMap<String, xyz.kumaraswamy.slime.operators.Operator> operators = new HashMap<>();

    static {
        final Object[][] operatorMaps = new Object[][] {
                new Object[] {"+", new Add()},
                new Object[] {"-", new Subtract()},
                new Object[] {"/", new Divide()},
                new Object[] {"*", new Multiply()},

                new Object[] {"^", new Power()},

                new Object[] {"is", new Is()},
                new Object[] {"or", new Or()}
        };

        for (Object[] operator : operatorMaps) {
            // put them to the Map
            operators.put((String) operator[0],
                    (xyz.kumaraswamy.slime.operators.Operator) operator[1]);
        }
    }
}
