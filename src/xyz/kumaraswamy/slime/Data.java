package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.parse.Label;
import xyz.kumaraswamy.slime.parse.Operator;
import xyz.kumaraswamy.slime.parse.Token;

import java.lang.reflect.Method;

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
}
