package xyz.kumaraswamy.slime;

import java.text.DecimalFormat;

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
}
