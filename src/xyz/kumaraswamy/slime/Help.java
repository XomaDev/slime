package xyz.kumaraswamy.slime;

public class Help {
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
}
