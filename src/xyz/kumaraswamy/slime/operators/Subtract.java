package xyz.kumaraswamy.slime.operators;


import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;
import static xyz.kumaraswamy.slime.Help.assertNums;

/**
 * class for the arithmetic operator '-'
 */

public class Subtract extends Operator {

    /**
     *
     * @return the value of first and second number
     * @throws Exception @parm first or @parm second is not a number
     */

    @Override
    public Object handle(Object first, Object second) throws Exception {
        assertNums(first, second);

        return parseDouble(valueOf(first))
                - parseDouble(valueOf(second));
    }
}
