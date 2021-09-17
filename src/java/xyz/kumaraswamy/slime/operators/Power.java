package xyz.kumaraswamy.slime.operators;

// class for the power operator '^'

import static xyz.kumaraswamy.slime.Help.asNum;
import static xyz.kumaraswamy.slime.Help.assertNums;

public class Power extends Operator {

    /**
     * @return power of two numbers
     * @throws Exception @parm first or @parm second is not a number
     */

    @Override
    public Object handle(Object first, Object second) throws Exception {
        assertNums(first, second);
        return Math.pow(asNum(first), asNum(second));
    }
}
