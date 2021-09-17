package xyz.kumaraswamy.slime.operators;

import static xyz.kumaraswamy.slime.Help.asNum;
import static xyz.kumaraswamy.slime.Help.assertNums;

/**
 * class for arithmetic operator '/'
 */

public class Divide extends Operator {

    /**
     * @return divides @parm first by @parm second
     * @throws Exception @parm first or @parm second is not a number
     */

    @Override
    public Object handle(Object first, Object second) throws Exception {
        assertNums(first, second);
        final double firstNum = asNum(first), secondNum = asNum(second);
        if (firstNum == 0.0 || secondNum == 0.0) {
            throw new ArithmeticException("[exception] / by zero");
        }
        return firstNum / secondNum;
    }
}
