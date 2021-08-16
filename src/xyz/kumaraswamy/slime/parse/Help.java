package xyz.kumaraswamy.slime.parse;

import static xyz.kumaraswamy.slime.Constants.QUOTE;

public class Help {
    public static Object isValue(String text) {
        if (text.length() < 2) {
            return null;
        }
        final int len = text.length() - 1;
        final char ch = text.charAt(0),
                ch1 = text.charAt(len);
        if (ch != ch1) {
            return null;
        }
        return QUOTE == ch
                ? text.substring(1, len)
                : null;
    }
}
