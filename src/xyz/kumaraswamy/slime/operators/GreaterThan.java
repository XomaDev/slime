package xyz.kumaraswamy.slime.operators;

import static xyz.kumaraswamy.slime.Help.asNum;
import static xyz.kumaraswamy.slime.Help.assertNums;

/**
 * class for the operator '<'
 * to check if the second number is
 * greater than the first number
 */

public class GreaterThan extends Operator {

    /**
     * @return @parm first < @parm second
     * @throws Exception The first or the second parm is not a valid number
     */

    @Override
    public Object handle(Object first, Object second) throws Exception {
        assertNums(first, second);
        return asNum(first) > asNum(second);
    }
}
