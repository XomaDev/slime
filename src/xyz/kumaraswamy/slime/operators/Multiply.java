package xyz.kumaraswamy.slime.operators;

/**
 * class for arithmetic operator '*'
 */

public class Multiply extends Operator {

    /**
     * @return @parm first * @parm second
     * @throws Exception @parm first or @parm second is not a number
     */

    @Override
    public Object handle(Object first, Object second) throws Exception {
        assertNums(first, second);
        return asNum(first) * asNum(second);
    }
}
