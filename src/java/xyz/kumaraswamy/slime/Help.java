package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.lex.Lex;

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;

public class Help {

    /**
     * Checks if the @parm text is a string value
     * values are quoted by `'` at the beginning and the end
     */

    public static Object isValue(final String text) {
        if (text.length() < 2) {
            return null;
        }
        final int len = text.length() - 1;
        final char ch = text.charAt(0),
                ch1 = text.charAt(len);
        if (ch != ch1) {
            return null;
        }
        return '\'' == ch
                ? text.substring(1, len)
                : null;
    }

    /**
     * Formats the Double to a decimal or
     * no decimal point values
     */

    public static String format(Object num) {
        if (num instanceof Double) {
            return new DecimalFormat("###.#").format(num);
        }
        return valueOf(num);
    }

    /**
     * Check if @parm token is a bracket (block)
     */

    public static boolean isBlock(final String token) {
        if (token.length() != 1) {
            return false;
        }
        return Lex.isBlock(token.charAt(0));
    }

    /**
     * @param object The object to be converted
     *               into double
     * @return returns the object as double
     */

    public static double asNum(final Object object) {
        return Double.parseDouble(String.valueOf(object));
    }

    /**
     * Assets that value are numbers
     * @throws Exception Raises an exception
     * if values are not numbers
     */

    public static void assertNums(final Object first,
                           final Object second) throws Exception {
        if (areNums(first, second)) {
            return;
        }
        throw new Exception("Cannot perform this operation, invalid type of arguments provided!");
    }

    /**
     * Checks if two numbers can be used as numbers
     * this is used to auto cast objects
     */

    public static boolean areNums(Object first, Object second) {
        if (first instanceof Double
                && second instanceof Double) {
            return true;
        }
        try {
            // try parsing the two objects to number
            parseDouble(valueOf(first));
            parseDouble(valueOf(second));
        } catch (NumberFormatException ignored) {
            // just ignore the exception
            return false;
        }
        return true;
    }
}
